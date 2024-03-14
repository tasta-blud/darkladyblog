package com.github.tasta_blud.darkladyblog.client.util

import io.kvision.routing.Routing
import io.kvision.routing.RoutingManager

val routing: Routing = RoutingManager.getRouter().unsafeCast<Routing>()
