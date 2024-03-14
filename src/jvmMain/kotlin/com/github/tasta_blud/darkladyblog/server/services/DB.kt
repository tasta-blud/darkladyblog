package com.github.tasta_blud.darkladyblog.server.services

import com.github.tasta_blud.darkladyblog.server.entities.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory
import java.sql.ResultSet

@Factory
class DB(private val application: Application) {

    private val driver: String = getConfigValue("db.driver", "org.h2.Driver")
    private val jdbcUrl: String = getConfigValue("db.jdbcUrl", "jdbc:h2:file:~/darkladyblog;AUTO_SERVER=TRUE;")
    private val username: String = getConfigValue("db.username", "darkladyblog")
    private val password: String = getConfigValue("db.password", "darkladyblog")

    val database: Database = Database.connect(
        url = jdbcUrl,
        driver = driver,
        user = username,
        password = password,
    )

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    fun <R : Any> String.executeNative(transform: (ResultSet) -> R): List<R> {
        val result = arrayListOf<R>()
        TransactionManager.current().exec(this) { rs ->
            while (rs.next()) {
                result += transform(rs)
            }
        }
        return result
    }

    fun <R : Any> execute(action: Transaction.() -> R): R = transaction(database, action)


    private fun getConfigValue(key: String, def: String): String =
        application.environment.config.propertyOrNull(key)?.getString() ?: def

}
