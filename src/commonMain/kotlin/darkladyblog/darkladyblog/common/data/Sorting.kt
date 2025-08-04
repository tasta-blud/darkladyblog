package darkladyblog.darkladyblog.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Sorting(val name: ColumnName, val direction: SortDirection)