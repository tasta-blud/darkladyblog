package darkladyblog.darkladyblog.server.util

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.LikeEscapeOp
import org.jetbrains.exposed.sql.LikePattern
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.lowerCase

infix fun <T : String?> Expression<T>.search(pattern: String): LikeEscapeOp =
    lowerCase() like LikePattern.ofLiteral(pattern.lowercase()).let { "%${it.pattern}%" }