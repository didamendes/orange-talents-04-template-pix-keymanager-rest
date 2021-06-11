package br.com.zup.edu.keymanager.pesquisar

import br.com.zup.edu.ListaChaveResponse

class ListaChaves(response: ListaChaveResponse) {
    val identificador = response.identificador

    val chaves = response.chavesList.map { chave -> Chave(
        chave.idPix,
        chave.tipoChave,
        chave.valorChave,
        chave.tipoConta
    ) }

}
