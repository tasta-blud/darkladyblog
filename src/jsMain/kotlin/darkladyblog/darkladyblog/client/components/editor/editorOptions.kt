package darkladyblog.darkladyblog.client.components.editor

class MdEditorOptions(
    @JsName("appendMarkdown") var appendMarkdown: String = "",
    @JsName("atLink") var atLink: Boolean = true,
    @JsName("autoCloseBrackets") var autoCloseBrackets: Boolean = true,
    @JsName("autoCloseTags") var autoCloseTags: Boolean = true,
    @JsName("autoFocus") var autoFocus: Boolean = true,
    @JsName("autoHeight") var autoHeight: Boolean = false,
    @JsName("autoLoadModules") var autoLoadModules: Boolean = true,
    @JsName("codeFold") var codeFold: Boolean = false,
    @JsName("crossDomainUpload") var crossDomainUpload: Boolean = false,
    @JsName("delay") var delay: Int = 300,
    @JsName("dialogDraggable") var dialogDraggable: Boolean = true,
    @JsName("dialogLockScreen") var dialogLockScreen: Boolean = true,
    @JsName("dialogMaskBgColor") var dialogMaskBgColor: String = "#fff",
    @JsName("dialogMaskOpacity") var dialogMaskOpacity: Double = 0.1,
    @JsName("dialogShowMask") var dialogShowMask: Boolean = true,
    @JsName("disabledKeyMaps") var disabledKeyMaps: List<String> = listOf(),
    @JsName("editorTheme") var editorTheme: String = "default",
    @JsName("emailLink") var emailLink: Boolean = true,
    @JsName("emoji") var emoji: Boolean = false,
    @JsName("flowChart") var flowChart: Boolean = false,
    @JsName("fontSize") var fontSize: String = "13px",
    @JsName("gotoLine") var gotoLine: Boolean = true,
    @JsName("height") var height: String = "100%",
    @JsName("htmlDecode") var htmlDecode: Boolean = false,
    @JsName("imageFormats") var imageFormats: List<String> = listOf("jpg", "jpeg", "gif", "png", "bmp", "webp"),
    @JsName("imageUpload") var imageUpload: Boolean = false,
    @JsName("imageUploadURL") var imageUploadURL: String = "",
    @JsName("indentUnit") var indentUnit: Int = 4,
    @JsName("indentWithTabs") var indentWithTabs: Boolean = true,
    @JsName("lang") var lang: MdOptionLang = MdOptionLang(),
    @JsName("lineNumbers") var lineNumbers: Boolean = true,
    @JsName("lineWrapping") var lineWrapping: Boolean = true,
    @JsName("markdown") var markdown: String = "",
    @JsName("matchBrackets") var matchBrackets: Boolean = true,
    @JsName("matchWordHighlight") var matchWordHighlight: Boolean = true,
    @JsName("mode") var mode: String = "markdown",
    @JsName("name") var name: String = "",
    @JsName("onchange") var onchange: () -> Unit = {},
    @JsName("onfullscreen") var onfullscreen: () -> Unit = {},
    @JsName("onfullscreenExit") var onfullscreenExit: () -> Unit = {},
    @JsName("onload") var onload: () -> Unit = {},
    @JsName("onpreviewed") var onpreviewed: () -> Unit = {},
    @JsName("onpreviewing") var onpreviewing: () -> Unit = {},
    @JsName("onpreviewscroll") var onpreviewscroll: () -> Unit = {},
    @JsName("onresize") var onresize: () -> Unit = {},
    @JsName("onscroll") var onscroll: () -> Unit = {},
    @JsName("onunwatch") var onunwatch: () -> Unit = {},
    @JsName("onwatch") var onwatch: () -> Unit = {},
    @JsName("pageBreak") var pageBreak: Boolean = true,
    @JsName("path") var path: String = "./lib/",
    @JsName("placeholder") var placeholder: String = "",
    @JsName("pluginPath") var pluginPath: String = "",
    @JsName("previewCodeHighlight") var previewCodeHighlight: Boolean = true,
    @JsName("previewTheme") var previewTheme: String = "",
    @JsName("readOnly") var readOnly: Boolean = false,
    @JsName("saveHTMLToTextarea") var saveHTMLToTextarea: Boolean = false,
    @JsName("searchReplace") var searchReplace: Boolean = true,
    @JsName("sequenceDiagram") var sequenceDiagram: Boolean = false,
    @JsName("showTrailingSpace") var showTrailingSpace: Boolean = true,
    @JsName("styleActiveLine") var styleActiveLine: Boolean = true,
    @JsName("styleSelectedText") var styleSelectedText: Boolean = true,
    @JsName("syncScrolling") var syncScrolling: Boolean = true,
    @JsName("tabSize") var tabSize: Int = 4,
    @JsName("taskList") var taskList: Boolean = false,
    @JsName("tex") var tex: Boolean = false,
    @JsName("theme") var theme: String = "",
    @JsName("toc") var toc: Boolean = true,
    @JsName("tocContainer") var tocContainer: String = "",
    @JsName("tocDropdown") var tocDropdown: Boolean = false,
    @JsName("tocStartLevel") var tocStartLevel: Int = 1,
    @JsName("tocTitle") var tocTitle: String = "",
    @JsName("tocm") var tocm: String = "",
    @JsName("toolbar") var toolbar: Boolean = true,
    @JsName("toolbarAutoFixed") var toolbarAutoFixed: Boolean = true,
    @JsName("toolbarCustomIcons") var toolbarCustomIcons: MdOptionToolbarCustomIcons = MdOptionToolbarCustomIcons(),
    @JsName("toolbarHandlers") var toolbarHandlers: MdOptionToolbarHandlers = MdOptionToolbarHandlers(),
    @JsName("toolbarIconTexts") var toolbarIconTexts: MdOptionToolbarIconTexts = MdOptionToolbarIconTexts(),
    @JsName("toolbarIcons") var toolbarIcons: String = "full",
    @JsName("toolbarIconsClass") var toolbarIconsClass: MdOptionToolbarIconsClass = MdOptionToolbarIconsClass(),
    @JsName("toolbarTitles") var toolbarTitles: MdOptionToolbarTitles = MdOptionToolbarTitles(),
    @JsName("uploadCallbackURL") var uploadCallbackURL: String = "",
    @JsName("value") var value: String = "",
    @JsName("watch") var watch: Boolean = true,
    @JsName("width") var width: String = "100%"
) {
    class MdOptionLang(
        @JsName("buttons") var buttons: MdOptionLangButtons = MdOptionLangButtons(),
        @JsName("description") var description: String = "",
        @JsName("dialog") var dialog: MdOptionLangDialog = MdOptionLangDialog(),
        @JsName("name") var name: String = "en-En",
        @JsName("tocTitle") var tocTitle: String = "",
        @JsName("toolbar") var toolbar: MdOptionLangToolbar = MdOptionLangToolbar()
    ) {
        class MdOptionLangButtons(
            @JsName("cancel") var cancel: String = "",
            @JsName("close") var close: String = "",
            @JsName("enter") var enter: String = ""
        )

        class MdOptionLangDialog(
            @JsName("codeBlock") var codeBlock: MdOptionLangDialogCodeBlock = MdOptionLangDialogCodeBlock(),
            @JsName("help") var help: MdOptionLangDialogHelp = MdOptionLangDialogHelp(),
            @JsName("htmlEntities") var htmlEntities: MdOptionLangDialogHtmlEntities = MdOptionLangDialogHtmlEntities(),
            @JsName("image") var image: MdOptionLangDialogImage = MdOptionLangDialogImage(),
            @JsName("link") var link: MdOptionLangDialogLink = MdOptionLangDialogLink(),
            @JsName("preformattedText") var preformattedText: MdOptionLangDialogPreformattedText = MdOptionLangDialogPreformattedText(),
            @JsName("referenceLink") var referenceLink: MdOptionLangDialogReferenceLink = MdOptionLangDialogReferenceLink()
        ) {
            class MdOptionLangDialogCodeBlock(
                @JsName("codeEmptyAlert") var codeEmptyAlert: String = "",
                @JsName("otherLanguage") var otherLanguage: String = "",
                @JsName("selectDefaultText") var selectDefaultText: String = "",
                @JsName("selectLabel") var selectLabel: String = "",
                @JsName("title") var title: String = "",
                @JsName("unselectedLanguageAlert") var unselectedLanguageAlert: String = ""
            )

            class MdOptionLangDialogHelp(
                @JsName("title") var title: String = ""
            )

            class MdOptionLangDialogHtmlEntities(
                @JsName("title") var title: String = ""
            )

            class MdOptionLangDialogImage(
                @JsName("alt") var alt: String = "",
                @JsName("formatNotAllowed") var formatNotAllowed: String = "",
                @JsName("imageURLEmpty") var imageURLEmpty: String = "",
                @JsName("link") var link: String = "",
                @JsName("title") var title: String = "",
                @JsName("uploadButton") var uploadButton: String = "",
                @JsName("uploadFileEmpty") var uploadFileEmpty: String = "",
                @JsName("url") var url: String = ""
            )

            class MdOptionLangDialogLink(
                @JsName("title") var title: String = "",
                @JsName("url") var url: String = "",
                @JsName("urlEmpty") var urlEmpty: String = "",
                @JsName("urlTitle") var urlTitle: String = ""
            )

            class MdOptionLangDialogPreformattedText(
                @JsName("emptyAlert") var emptyAlert: String = "",
                @JsName("title") var title: String = ""
            )

            class MdOptionLangDialogReferenceLink(
                @JsName("idEmpty") var idEmpty: String = "",
                @JsName("name") var name: String = "",
                @JsName("nameEmpty") var nameEmpty: String = "",
                @JsName("title") var title: String = "",
                @JsName("url") var url: String = "",
                @JsName("urlEmpty") var urlEmpty: String = "",
                @JsName("urlId") var urlId: String = "",
                @JsName("urlTitle") var urlTitle: String = ""
            )
        }

        class MdOptionLangToolbar(
            @JsName("bold") var bold: String = "",
            @JsName("clear") var clear: String = "",
            @JsName("code") var code: String = "",
            @JsName("`code-block`") var `code-block`: String = "",
            @JsName("datetime") var datetime: String = "",
            @JsName("del") var del: String = "",
            @JsName("emoji") var emoji: String = "",
            @JsName("fullscreen") var fullscreen: String = "",
            @JsName("`goto-line`") var `goto-line`: String = "",
            @JsName("h1") var h1: String = "",
            @JsName("h2") var h2: String = "",
            @JsName("h3") var h3: String = "",
            @JsName("h4") var h4: String = "",
            @JsName("h5") var h5: String = "",
            @JsName("h6") var h6: String = "",
            @JsName("help") var help: String = "",
            @JsName("hr") var hr: String = "",
            @JsName("`html-entities`") var `html-entities`: String = "",
            @JsName("image") var image: String = "",
            @JsName("info") var info: String = "",
            @JsName("italic") var italic: String = "",
            @JsName("link") var link: String = "",
            @JsName("`list-ol`") var `list-ol`: String = "",
            @JsName("`list-ul`") var `list-ul`: String = "",
            @JsName("lowercase") var lowercase: String = "",
            @JsName("pagebreak") var pagebreak: String = "",
            @JsName("`preformatted-text`") var `preformatted-text`: String = "",
            @JsName("preview") var preview: String = "",
            @JsName("quote") var quote: String = "",
            @JsName("redo") var redo: String = "",
            @JsName("`reference-link`") var `reference-link`: String = "",
            @JsName("search") var search: String = "",
            @JsName("table") var table: String = "",
            @JsName("ucwords") var ucwords: String = "",
            @JsName("undo") var undo: String = "",
            @JsName("unwatch") var unwatch: String = "",
            @JsName("uppercase") var uppercase: String = "",
            @JsName("watch") var watch: String = ""
        )
    }

    class MdOptionToolbarCustomIcons(
        @JsName("lowercase")
        var lowercase: String =
            "<a href=\\\"javascript:;\\\" title=\\\"Lowercase\\\" unselectable=\\\"on\\\"><i class=\\\"fa\\\" name=\\\"lowercase\\\" style=\\\"font-size:24px;margin-top: -10px;\\\">a</i></a>",
        @JsName("ucwords")
        var ucwords: String =
            "<a href=\\\"javascript:;\\\" title=\\\"ucwords\\\" unselectable=\\\"on\\\"><i class=\\\"fa\\\" name=\\\"ucwords\\\" style=\\\"font-size:20px;margin-top: -3px;\\\">Aa</i></a>"
    )

    class MdOptionToolbarHandlers(
        @JsName("lowercase") var lowercase: () -> Unit = {},
        @JsName("ucwords") var ucwords: () -> Unit = {}
    )

    class MdOptionToolbarIconTexts

    class MdOptionToolbarIconsClass(
        @JsName("bold") var bold: String = "fa-bold",
        @JsName("clear") var clear: String = "fa-eraser",
        @JsName("code") var code: String = "fa-code",
        @JsName("`code-block`") var `code-block`: String = "fa-file-code-o",
        @JsName("datetime") var datetime: String = "fa-clock-o",
        @JsName("del") var del: String = "fa-strikethrough",
        @JsName("emoji") var emoji: String = "fa-smile-o",
        @JsName("fullscreen") var fullscreen: String = "fa-arrows-alt",
        @JsName("`goto-line`") var `goto-line`: String = "fa-terminal",
        @JsName("h1") var h1: String = "EditorMD-bold",
        @JsName("h2") var h2: String = "EditorMD-bold",
        @JsName("h3") var h3: String = "EditorMD-bold",
        @JsName("h4") var h4: String = "EditorMD-bold",
        @JsName("h5") var h5: String = "EditorMD-bold",
        @JsName("h6") var h6: String = "EditorMD-bold",
        @JsName("help") var help: String = "fa-question-circle",
        @JsName("hr") var hr: String = "fa-minus",
        @JsName("`html-entities`") var `html-entities`: String = "fa-copyright",
        @JsName("image") var image: String = "fa-picture-o",
        @JsName("info") var info: String = "a-info-circle",
        @JsName("italic") var italic: String = "fa-italic",
        @JsName("link") var link: String = "fa-link",
        @JsName("`list-ol`") var `list-ol`: String = "fa-list-ol",
        @JsName("`list-ul`") var `list-ul`: String = "fa-list-ul",
        @JsName("pagebreak") var pagebreak: String = "fa-newspaper-o",
        @JsName("`preformatted-text`") var `preformatted-text`: String = "fa-file-code-o",
        @JsName("preview") var preview: String = "fa-desktop",
        @JsName("quote") var quote: String = "fa-quote-left",
        @JsName("redo") var redo: String = "fa-redo",
        @JsName("`reference-link`") var `reference-link`: String = "fa-anchor",
        @JsName("search") var search: String = "fa-search",
        @JsName("table") var table: String = "fa-table",
        @JsName("undo") var undo: String = "fa-undo",
        @JsName("unwatch") var unwatch: String = "fa-eye",
        @JsName("uppercase") var uppercase: String = "fa-font",
        @JsName("watch") var watch: String = "fa-eye-slash"
    )

    class MdOptionToolbarTitles
}