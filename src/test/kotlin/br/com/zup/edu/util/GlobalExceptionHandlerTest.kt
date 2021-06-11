package br.com.zup.edu.util

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class GlobalExceptionHandlerTest {

    val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve lancar 404 para StatusRuntimeException not found`() {

        val mensagem = "Não foi encontrado com os dados informado"
        val statusRuntimeException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, statusRuntimeException)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
    }

    @Test
    internal fun `deve lancar 400 para StatusRuntimeException Invalid Argument`() {
        val mensagem = "Dados preenchidos invalidos"
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, statusRuntimeException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
    }

    @Test
    internal fun `deve lancar 422 para StatusRuntimeException Already Exists`() {
        val mensagem = "Dados informados ja existem"
        val statusRuntimeException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, statusRuntimeException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
    }

    @Test
    internal fun `deve lancar 500 para StatusRuntimeException que nao foram esperadas`() {
        val mensagem = "Erro inesperado ao cadastrar chave pix"
        val statusRuntimeException = StatusRuntimeException(Status.INTERNAL.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, statusRuntimeException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
    }
}