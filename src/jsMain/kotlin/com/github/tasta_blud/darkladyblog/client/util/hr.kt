package com.github.tasta_blud.darkladyblog.client.util

import io.kvision.core.Container
import io.kvision.html.CustomTag
import io.kvision.html.customTag

fun Container.hr(className: String? = null): CustomTag = customTag("hr", className = className)
