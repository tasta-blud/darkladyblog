package com.github.tasta_blud.darkladyblog.common.services

import io.ktor.server.websocket.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class WsService(val wsSession: WebSocketServerSession) : IWsService {

    override suspend fun wservice(input: ReceiveChannel<Int>, output: SendChannel<String>) {
        for (i in input) {
            output.send("I'v got: $i")
        }
    }
}
