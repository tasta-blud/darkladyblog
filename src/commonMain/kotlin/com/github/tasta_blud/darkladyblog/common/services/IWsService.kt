package com.github.tasta_blud.darkladyblog.common.services

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

interface IWsService {
    suspend fun wservice(input: ReceiveChannel<Int>, output: SendChannel<String>) {}
    suspend fun wservice(handler: suspend (SendChannel<Int>, ReceiveChannel<String>) -> Unit) {}
}
