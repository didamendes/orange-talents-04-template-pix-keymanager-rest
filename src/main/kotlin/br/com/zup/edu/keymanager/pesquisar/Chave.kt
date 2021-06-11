package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta

class Chave(
    val idPix: Long,
    val tipoChave: TipoChave?,
    val valorChave: String?,
    val tipoConta: TipoConta?
)
