package br.com.zup.edu.keymanager.cadastrar

import br.com.zup.edu.KeyManagerNovoGrpcServiceGrpc
import br.com.zup.edu.NovaChaveResponse
import br.com.zup.edu.keymanager.classes.NovaChaveRequest
import br.com.zup.edu.keymanager.classes.TipoChaveRequest
import br.com.zup.edu.keymanager.classes.TipoContaRequest
import br.com.zup.edu.util.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CadastrarKeyManagerControllerTest {

    @field:Inject
    lateinit var client: KeyManagerNovoGrpcServiceGrpc.KeyManagerNovoGrpcServiceBlockingStub

    @field:Inject
    @field:Client(value = "/")
    lateinit var clientHttp: HttpClient

    @Test
    internal fun `deve cadastrar uma chave pix`() {
        val idPix = 1L
        val identificador = UUID.randomUUID().toString()

        val novaChaveResponse = NovaChaveResponse.newBuilder().setIdPix(idPix).build()

        Mockito.`when`(client.cadastrar(Mockito.any())).thenReturn(novaChaveResponse)

        val novaChaveRequest = NovaChaveRequest(
            identificador,
            TipoChaveRequest.EMAIL,
            "didamendes@hotmail.com",
            TipoContaRequest.CONTA_CORRENTE
        )

        val request = HttpRequest.POST("/keymanager", novaChaveRequest)

        val response = clientHttp.toBlocking().exchange(request, NovaChaveRequest::class.java)

        assertEquals(HttpStatus.OK, response.status)

    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class GrpcFactory {
        @Singleton
        fun stub() = Mockito.mock(KeyManagerNovoGrpcServiceGrpc.KeyManagerNovoGrpcServiceBlockingStub::class.java)
    }

}