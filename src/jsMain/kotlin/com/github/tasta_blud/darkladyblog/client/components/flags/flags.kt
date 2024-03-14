package com.github.tasta_blud.darkladyblog.client.components.flags

import io.kvision.core.Container
import io.kvision.html.I

class Flag(country: String) : I(className = "flag:$country")

fun Container.flag(country: String): Flag = Flag(country).also { add(it) }
