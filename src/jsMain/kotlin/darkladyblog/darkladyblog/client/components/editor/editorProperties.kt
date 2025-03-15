package darkladyblog.darkladyblog.client.components.editor

enum class MdEditorToolbarMode(val list: List<String>) {
    full(
        listOf(
            "undo",
            "redo",
            "|",
            "bold",
            "del",
            "italic",
            "quote",
            "ucwords",
            "uppercase",
            "lowercase",
            "|",
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "|",
            "list-ul",
            "list-ol",
            "hr",
            "|",
            "link",
            "reference-link",
            "image",
            "code",
            "preformatted-text",
            "code-block",
            "table",
            "datetime",
            "emoji",
            "html-entities",
            "pagebreak",
            "|",
            "goto-line",
            "watch",
            "preview",
            "fullscreen",
            "clear",
            "search",
            "|",
            "help",
            "info"
        )
    ),
    mini(
        listOf(
            "undo", "redo", "|",
            "watch", "preview", "|",
            "help", "info"
        )
    ),
    simple(
        listOf(
            "undo", "redo", "|",
            "bold", "del", "italic", "quote", "uppercase", "lowercase", "|",
            "h1", "h2", "h3", "h4", "h5", "h6", "|",
            "list-ul", "list-ol", "hr", "|",
            "watch", "preview", "fullscreen", "|",
            "help", "info"
        )
    )
}

class MdEditorRegexs(
    @JsName("atLink") var atLink: Regex = "@(\\w+)".toRegex(),
    @JsName("editormdLogo") var editormdLogo: Regex = ":(EditorMD-logo-?(\\w+)?):".toRegex(),
    @JsName("email") var email: Regex = "(\\w+)@(\\w+)\\.(\\w+)\\.?(\\w+)?".toRegex(),
    @JsName("emailLink") var emailLink: Regex = "(mailto:)?([\\w\\.\\_]+)@(\\w+)\\.(\\w+)\\.?(\\w+)?".toRegex(),
    @JsName("emoji") var emoji: Regex = "/:([\\w\\+-]+):".toRegex(),
    @JsName("emojiDatetime") var emojiDatetime: Regex = "/(\\d{1,2}:\\d{1,2}:\\d{1,2})".toRegex(),
    @JsName("fontAwesome") var fontAwesome: Regex = ":(fa-([\\w]+)(-(\\w+)){0,}):".toRegex(),
    @JsName("pageBreak") var pageBreak: Regex = "^\\[[=]{8,}\\]\$".toRegex(),
    @JsName("twemoji") var twemoji: Regex = ":(tw-([\\w]+)-?(\\w+)?):".toRegex()
)

class MdEditorEmoji(
    @JsName("path") var path: String = "http://www.emoji-cheat-sheet.com/graphics/emojis/",
    @JsName("ext") var ext: String = ".png"
)

class EditorMDUrls {
    @JsName("atLinkBase")
    var atLinkBase: String = ""
}