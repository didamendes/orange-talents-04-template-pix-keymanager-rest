package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.ConsultaChaveResponse

class DetalheResponse(response: ConsultaChaveResponse) {
    val identificador = response.identificador
    val idPix = response.idPix
    val tipo = response.chave.tipo
    val chave = response.chave.chave

    var conta = ContaAssociada(
        response.chave.conta.tipo,
        response.chave.conta.instituicao,
        response.chave.conta.nomeDoTitular,
        response.chave.conta.cpfDoTitular,
        response.chave.conta.agencia,
        response.chave.conta.numeroDaConta
    )

}
