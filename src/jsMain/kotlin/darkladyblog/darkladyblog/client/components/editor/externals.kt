package darkladyblog.darkladyblog.client.components.editor

@JsModule("./editor.md/editormd.js")
@JsNonModule
external class EditorMD {
    @JsName("urls")
    var urls: EditorMDUrls = definedExternally

    @JsName("title")
    var title: String = definedExternally

    @JsName("version")
    var version: String = definedExternally

    @JsName("homePage")
    var homePage: String = definedExternally

    @JsName("classPrefix")
    var classPrefix: String = definedExternally

    @JsName("toolbarModes")
    var toolbarModes: List<MdEditorToolbarMode> = definedExternally
    val defaults: MdEditorOptions = definedExternally
    val settings: MdEditorOptions = definedExternally

    @JsName("classNames")
    var classNames: MutableMap<String, String> = definedExternally

    @JsName("dialogZindex")
    var dialogZindex: Int = definedExternally

    @JsName("trim")
    var trim: (String) -> String = definedExternally

    @JsName("ucwords")
    var ucwords: (String) -> String = definedExternally

    @JsName("firstUpperCase")
    var firstUpperCase: (String) -> String = definedExternally

    @JsName("regexs")
    var regexs: MdEditorRegexs = definedExternally

    @JsName("emoji")
    var emoji: MdEditorEmoji = definedExternally

    @JsName("filterHTMLTags")
    var filterHTMLTags: (html: String, filters: String) -> String = definedExternally

    @JsName("markdownToHTML")
    var markdownToHTML: (id: String, options: MdEditorOptions) -> String = definedExternally

    @JsName("themes")
    var themes: MutableList<String> = definedExternally

    @JsName("previewThemes")
    var previewThemes: MutableList<String> = definedExternally

    @JsName("editorThemes")
    var editorThemes: MutableList<String> = definedExternally

    @JsName("dateFormat")
    var dateFormat: (format: String) -> String = definedExternally
}

fun editormd(target: String, options: MdEditorOptions): EditorMD =
    js("editormd(target, options)")

var editormd: EditorMD =
    js("window.editormd")
