package darkladyblog.darkladyblog.common.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class ColumnName(val name: String) {
}