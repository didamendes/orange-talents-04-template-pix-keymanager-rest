package br.com.zup.edu.keymanager.classes

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import br.com.caelum.stella.validation.CPFValidator
import br.com.zup.edu.NovaChaveRequest
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoChave.NULLO
import br.com.zup.edu.TipoConta
import br.com.zup.edu.util.ValidPixKey
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NovaChaveRequest(
    @field:NotBlank val identificador: String?,
    @field:NotNull val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77) val valorChave: String?,
    @field:NotNull val tipoConta: TipoContaRequest?
) {
    fun toNovaChaveRequest(): NovaChaveRequest {
        return NovaChaveRequest.newBuilder().setIdentificador(identificador)
            .setTipoChave(tipoChave?.atributoGrpc ?: NULLO).setValorChave(valorChave ?: "")
            .setTipoConta(tipoConta?.atributoGrpc ?: TipoConta.NULO).build()
    }

}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {
    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }
            return CPFValidator(false)
                .invalidMessagesFor(chave)
                .isEmpty()
        }
    },
    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank()) {
                return false
            }

            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    CHAVE(TipoChave.CHAVE) {
        override fun valida(chave: String?) = chave.isNullOrBlank() // não deve ser preenchida
    };
    abstract fun valida(chave: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
