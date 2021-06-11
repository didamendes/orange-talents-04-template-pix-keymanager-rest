package br.com.zup.edu.util

import br.com.zup.edu.KeyManagerConsultaGrpcServiceGrpc
import br.com.zup.edu.KeyManagerListarGrpcServiceGrpc
import br.com.zup.edu.KeyManagerNovoGrpcServiceGrpc
import br.com.zup.edu.KeyManagerRemoverGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
open class GrpcClientFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun keyManagerNovoClientStub() = KeyManagerNovoGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun keyManagerDeletarClientStub() = KeyManagerRemoverGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun keyManagerConsultaClientStub() = KeyManagerConsultaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun keyManagerListarClientStub() = KeyManagerListarGrpcServiceGrpc.newBlockingStub(channel)
}