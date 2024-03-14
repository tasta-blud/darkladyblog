package com.github.tasta_blud.darkladyblog.client.util

import io.kvision.core.Container
import io.kvision.html.Image
import io.kvision.html.Span
import io.kvision.html.image
import io.kvision.html.span

fun Container.svgSpan(className: String? = null, svg: String): Span = span(svg, rich = true, className = className)
fun Container.svgImage(svg: String): Image = image(src = "data:image/svg+xml;base64," + btoa(svg))
