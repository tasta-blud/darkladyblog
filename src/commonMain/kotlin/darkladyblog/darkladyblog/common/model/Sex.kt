package darkladyblog.darkladyblog.common.model

import kotlinx.css.Color

enum class Sex(val icon: String, val color: Color) {
    NON_BINARY("bi bi-gender-ambiguous", Color("violet")),
    FEMALE("bi bi-gender-female", Color("pink")),
    MALE("bi bi-gender-male", Color("blue"));
}
