package com.github.tasta_blud.darkladyblog.client.controllers

import com.github.tasta_blud.darkladyblog.client.controllers.AppController.AppScope
import com.github.tasta_blud.darkladyblog.common.services.IWsService
import io.kvision.remote.getService
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

object WsController {
    private val ws: IWsService = getService<IWsService>()

    init {
        AppScope.launch {
            while (true) {
                ws.wservice { output: SendChannel<Int>, input: ReceiveChannel<String> ->
                    coroutineScope {
                        launch {
                            while (true) {
                                val i = Random.nextInt()
                                output.send(i)
                                delay(1000)
                            }
                        }
                        launch {
                            for (str in input) {
                                println(str)
                            }
                        }
                    }
                }
                delay(5000)
            }
        }
    }
}
