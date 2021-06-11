package br.com.zup.edu.keymanager.remover

import br.com.zup.edu.KeyManagerRemoverGrpcServiceGrpc
import br.com.zup.edu.RemoverChaveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject

@Validated
@Controller("/keymanager")
class RemoverKeyManagerController(@Inject val client: KeyManagerRemoverGrpcServiceGrpc.KeyManagerRemoverGrpcServiceBlockingStub) {

    @Delete("/key/{idPix}/identificador/{identificador}")
    fun remover(@PathVariable idPix: Long, @PathVariable identificador: UUID): HttpResponse<Any> {
        val removerChaveRequest =
            RemoverChaveRequest.newBuilder().setIdPix(idPix).setIdentificador(identificador.toString()).build()

        client.remover(removerChaveRequest)

        return HttpResponse.noContent()
    }

}