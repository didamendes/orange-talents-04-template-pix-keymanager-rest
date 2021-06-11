package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import javax.annotation.Nullable
import javax.inject.Inject

@Validated
@Controller("/keymanager")
class PesquisarKeyManagerController(
    @Inject val clientConsulta: KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub,
    @Inject val clientLstar: KeyManagerListarGrpcServiceGrpc.KeyManagerListarGrpcServiceBlockingStub
) {

    @Get
    fun consultaPorIdPixAndIdentificador(
        @Nullable @QueryValue idPix: Long,
        @Nullable @QueryValue identificador: String
    ): HttpResponse<Any> {
        val consultaChaveRequest =
            ConsultaChaveRequest.newBuilder().setIdPix(
                ConsultaChaveRequest.FiltroProPixId.newBuilder().setIdPix(idPix).setIdentificador(identificador).build()
            ).build()


        val response = clientConsulta.consultar(consultaChaveRequest)
        return HttpResponse.ok(DetalheResponse(response))
    }

    @Get(uri = "/consulta")
    fun consultaPorChave(@Nullable @QueryValue chave: String): HttpResponse<Any> {
        val consultaChaveRequest = ConsultaChaveRequest.newBuilder().setChave(chave).build()

        val response = clientConsulta.consultar(consultaChaveRequest)
        return HttpResponse.ok(DetalheResponse(response))
    }

    @Get(uri = "/{identificador}")
    fun listar(@PathVariable identificador: String): HttpResponse<Any> {
        val listaChaveRequest = ListaChaveRequest.newBuilder().setIndentificador(identificador).build()
        val response: ListaChaveResponse = clientLstar.listar(listaChaveRequest)

        return HttpResponse.ok(ListaChaves(response))
    }

}