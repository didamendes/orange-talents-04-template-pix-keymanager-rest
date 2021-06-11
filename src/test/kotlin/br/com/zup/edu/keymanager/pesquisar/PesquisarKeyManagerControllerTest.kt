package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.*
import br.com.zup.edu.util.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class PesquisarKeyManagerControllerTest {

    @field:Inject
    lateinit var clientConsulta: KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub

    @field:Inject
    lateinit var clientListar: KeyManagerListarGrpcServiceGrpc.KeyManagerListarGrpcServiceBlockingStub

    @field:Inject
    @field:Client(value = "/")
    lateinit var clientHttp: HttpClient

    @Test
    internal fun `deve buscar a chave pix pelo idPix e identificador informado`() {
        val idPix = 1L
        val identificador = UUID.randomUUID().toString()

        val consultaChaveResponseMock = consultaChaveResponseMock()

        Mockito.`when`(
            clientConsulta.consultar(
                ConsultaChaveRequest.newBuilder().setIdPix(
                    ConsultaChaveRequest.FiltroProPixId.newBuilder().setIdPix(idPix)
                        .setIdentificador(identificador).build()
                ).build()
            )
        ).thenReturn(consultaChaveResponseMock)

        val request = HttpRequest.GET<Any>("/keymanager?idPix=${idPix}&identificador=${identificador}")
        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status())
        Assertions.assertNotNull(response.body())
    }

    @Test
    internal fun `deve buscar a chave pix pela chave informado`() {
        val chave = "83128212520"

        val consultaChaveResponseMock = consultaChaveResponseMock()

        Mockito.`when`(clientConsulta.consultar(ConsultaChaveRequest.newBuilder().setChave(chave).build()))
            .thenReturn(consultaChaveResponseMock)

        val request = HttpRequest.GET<Any>("/keymanager/consulta?chave=${chave}")
        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status())
        Assertions.assertNotNull(response.body())
    }

    @Test
    internal fun `deve listar as chaves PIX do cliente informado`() {
        val identificador = UUID.randomUUID().toString()

        val listaChaveResponseMock = listaChaveResponseMock()

        Mockito.`when`(clientListar.listar(ListaChaveRequest.newBuilder().setIndentificador(identificador).build()))
            .thenReturn(listaChaveResponseMock)

        val request = HttpRequest.GET<Any>("/keymanager/${identificador}")
        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status())
        Assertions.assertNotNull(response.body())
    }

    fun consultaChaveResponseMock(): ConsultaChaveResponse {
        val contaInfo = ConsultaChaveResponse.ChavePix.ContaInfo.newBuilder().setTipo(TipoConta.CONTA_CORRENTE)
            .setInstituicao("60701190").setNomeDoTitular("Diogo Mendes").setCpfDoTitular("030303030301")
            .setAgencia("0001").setNumeroDaConta("123456").build()

        val chavePix = ConsultaChaveResponse.ChavePix.newBuilder().setTipo(TipoChave.CPF).setChave("33059192057")
            .setConta(contaInfo).build()

        return ConsultaChaveResponse.newBuilder().setIdPix("1").setIdentificador("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
            .setChave(chavePix).build()
    }

    fun listaChaveResponseMock(): ListaChaveResponse {
        val chaveOne = ListaChaveResponse.chave.newBuilder().setIdPix(1).setTipoChave(TipoChave.CHAVE)
            .setValorChave("ab23a331-842a-4727-891b-790ba6fe6502").setTipoConta(TipoConta.CONTA_CORRENTE).build()

        val chaveTwo = ListaChaveResponse.chave.newBuilder().setIdPix(2).setTipoChave(TipoChave.CHAVE)
            .setValorChave("ab23a331-842a-4727-891b-790ba6fe6503").setTipoConta(TipoConta.CONTA_CORRENTE).build()

        return ListaChaveResponse.newBuilder().setIdentificador("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
            .addChaves(chaveOne).addChaves(chaveTwo).build()
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class GrpcFactory {

        @Singleton
        fun stub() =
            Mockito.mock(KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub::class.java)

        @Singleton
        fun listarStub() =
            Mockito.mock(KeyManagerListarGrpcServiceGrpc.KeyManagerListarGrpcServiceBlockingStub::class.java)
    }

}