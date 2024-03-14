package com.github.tasta_blud.darkladyblog.client.components.flags

import io.kvision.core.Container
import io.kvision.html.I

class Icon(name: String, type: String = "bi") : I(className = "$type $type-$name")

fun Container.icon(name: String, type: String = "bi"): Icon = Icon(name, type).also { add(it) }
