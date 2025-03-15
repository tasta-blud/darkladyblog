package darkladyblog.darkladyblog.server.services.comp

import com.overzealous.remark.Remark
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.html.HtmlRenderer.SOFT_BREAK
import com.vladsch.flexmark.html.HtmlRenderer.builder
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataHolder
import com.vladsch.flexmark.util.data.MutableDataSet
import darkladyblog.darkladyblog.common.services.DescriptionCompiler
import org.koin.core.annotation.Single

@Single
class DescriptionCompilerImpl : DescriptionCompiler {
    companion object {
        private const val PATTERN_LINES = "\n"
        private const val BR = "<br />\n"
    }


    override fun compile(description: String): String =
        toHtml(description)

    private val parser: Parser
    private val renderer: HtmlRenderer
    private val remark: Remark

    init {
        val options: MutableDataHolder = MutableDataSet()
        options.set(SOFT_BREAK, BR)
        parser = Parser.builder(options).build()
        renderer = builder(options).build()
        remark = Remark()
    }

    private fun toHtml(source: String): String {
        return renderer.render(parser.parse(source)).let {
            if (it.startsWith("<p>") && it.indexOf("<p>", 1) < 0)
                it.removePrefix("<p>").removeSuffix("</p>")
            else
                it
        }
    }

    private fun toSource(html: String?): String {
        return remark.convertFragment(html)
    }
}
