package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.TipoConta

class ContaAssociada(
    val tipo: TipoConta?,
    val instituicao: String?,
    val nomeDoTitular: String?,
    val cpfDoTitular: String?,
    val agencia: String?,
    val numeroDaConta: String?
)
