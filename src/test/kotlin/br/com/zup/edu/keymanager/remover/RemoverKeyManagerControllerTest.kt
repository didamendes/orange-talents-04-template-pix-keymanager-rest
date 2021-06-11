package br.com.zup.edu.keymanager.remover


import br.com.zup.edu.KeyManagerRemoverGrpcServiceGrpc
import br.com.zup.edu.RemoverChaveResponse
import br.com.zup.edu.util.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoverKeyManagerControllerTest {

    @field:Inject
    lateinit var client: KeyManagerRemoverGrpcServiceGrpc.KeyManagerRemoverGrpcServiceBlockingStub

    @field:Inject
    @field:Client(value = "/")
    lateinit var clientHttp: HttpClient

    @Test
    internal fun `deve remover uma chave pix`() {
        val idPix = 1L
        val identificador = UUID.randomUUID().toString()

        val novaChaveResponse = RemoverChaveResponse.newBuilder().setIdPix(idPix).build()
        Mockito.`when`(client.remover(Mockito.any())).thenReturn(novaChaveResponse)

        val request = HttpRequest.DELETE<Any>("keymanager/key/${idPix}/identificador/${identificador}")
        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.NO_CONTENT, response.status)
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class GrpcFactory {
        @Singleton
        fun stub() = Mockito.mock(KeyManagerRemoverGrpcServiceGrpc.KeyManagerRemoverGrpcServiceBlockingStub::class.java)
    }

}

