package br.com.zup.edu.keymanager.cadastrar


import br.com.zup.edu.KeyManagerNovoGrpcServiceGrpc
import br.com.zup.edu.keymanager.classes.NovaChaveRequest
import br.com.zup.edu.keymanager.classes.NovaChaveResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/keymanager")
class CadastrarKeyManagerController(@Inject val client: KeyManagerNovoGrpcServiceGrpc.KeyManagerNovoGrpcServiceBlockingStub) {

    @Post
    fun cadastrar(@Valid @Body request: NovaChaveRequest): HttpResponse<Any> {

        val novaChaveRequest = request.toNovaChaveRequest()

        val response = client.cadastrar(novaChaveRequest)

        return HttpResponse.ok(NovaChaveResponse(response.idPix))
    }

}