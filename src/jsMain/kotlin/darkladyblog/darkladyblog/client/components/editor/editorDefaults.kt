package darkladyblog.darkladyblog.client.components.editor


val mdOptions: MdEditorOptions = MdEditorOptions(
    mode = "markdown",
    name = "item-source",
    height = "100%",
    path = "/webjars/editor.md/lib/",
    placeholder = /*[[#(admin-editor-placeholder)]]*/ "Please use markdown",
    autoHeight = true,
    lineNumbers = false,
    matchWordHighlight = false,//"onselected"
    toc = false,
    imageUpload = true,
    imageFormats = listOf("jpg", "jpeg", "gif", "png", "bmp", "webp"),
    imageUploadURL = "gargoyle.l0x.controllers.admin.UploadController.mdFileUpload",
    lang = MdEditorOptions.MdOptionLang(
        name = "ru",
        description = "Открытый редактор Markdown",
        tocTitle = "Оглавление",
        toolbar = MdEditorOptions.MdOptionLang.MdOptionLangToolbar(
            undo = "Отменить (Ctrl + Z)",
            redo = "Повторить (Ctrl + Y)",
            bold = "Жирный",
            del = "Перечёркнутый",
            italic = "Курсив",
            quote = "Цитата",
            ucwords = "Преобразовать инициалы каждого слова в верхний регистр",
            uppercase = "Преобразовать выделение в прописные",
            lowercase = "Преобразовать выделение в строчные",
            h1 = "Заголовок 1",
            h2 = "Заголовок 2",
            h3 = "Заголовок 3",
            h4 = "Заголовок 4",
            h5 = "Заголовок 5",
            h6 = "Заголовок 6",
            `list-ul` = "Неупорядоченный список",
            `list-ol` = "Упорядоченный список",
            hr = "Горизонтальная линия",
            link = "Ссылка",
            `reference-link` = "Якорь",
            image = "Добавить изображение",
            code = "Встроенный код",
            `preformatted-text` = "Предварительно отформатированный текст / кодовый блок (стиль с отступом)",
            `code-block` = "Код-блок (многоязычный стиль)",
            table = "Добавить таблицу",
            datetime = "Дата-время",
            emoji = "Emoji выражение",
            `html-entities` = "Символы сущностей HTML",
            pagebreak = "Вставить разрыв страницы",
            `goto-line` = "Перейти к строке",
            watch = "Закрыть предварительный просмотр",
            unwatch = "Открыть предварительный просмотр",
            preview = "Полноэкранный просмотр окна HTML (нажмите Shift + ESC восстановление)",
            fullscreen = "Во весь экран (восстановление по ESC)",
            clear = "Очистить",
            search = "Поиск",
            help = "Использовать помощь",
            info = "О программе"
        ),
        buttons = MdEditorOptions.MdOptionLang.MdOptionLangButtons(
            enter = "ОК",
            cancel = "Отмена",
            close = "Закрыть"
        ),
        dialog = MdEditorOptions.MdOptionLang.MdOptionLangDialog(
            link = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogLink(
                title = "Добавить ссылку",
                url = "Адрес",
                urlTitle = "Текст",
                urlEmpty = "Ошибка= пожалуйста, заполните адрес ссылки."
            ),
            referenceLink = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogReferenceLink(
                title = "Добавить ссылку",
                name = "Имя",
                url = "Адрес",
                urlId = "ID",
                urlTitle = "Текст",
                nameEmpty = "Ошибка= имя ссылки не может быть пустым.",
                idEmpty = "Ошибка= пожалуйста, введите идентификатор ссылки.",
                urlEmpty = "Ошибка= пожалуйста, введите URL ссылки."
            ),
            image = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogImage(
                title = "Добавить изображение",
                url = "Адрес",
                link = "Ссылка",
                alt = "Описание",
                uploadButton = "Загрузить",
                imageURLEmpty = "Ошибка= адрес изображения не может быть пустым.",
                uploadFileEmpty = "Ошибка= загруженное изображение не может быть пустым.",
                formatNotAllowed = "Ошибка= разрешена только загрузка файлов изображений. Формат файла изображений, который можно загружать="
            ),
            preformattedText = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogPreformattedText(
                title = "Добавить предварительно отформатированный текст или кодовые блоки",
                emptyAlert = "Ошибка= пожалуйста, заполните содержимое предварительно отформатированного текста или кода."
            ),
            codeBlock = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogCodeBlock(
                title = "Добавить блок кода",
                selectLabel = "Код языка=",
                selectDefaultText = "Пожалуйста, выберите язык кода",
                otherLanguage = "Другие языки",
                unselectedLanguageAlert = "Ошибка= Пожалуйста, выберите тип языка, к которому относится код.",
                codeEmptyAlert = "Ошибка= пожалуйста, заполните содержимое кода."
            ),
            htmlEntities = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogHtmlEntities(
                title = "Символы HTML-сущностей"
            ),
            help = MdEditorOptions.MdOptionLang.MdOptionLangDialog.MdOptionLangDialogHelp(
                title = "Помощь"
            )
        )
    )
)