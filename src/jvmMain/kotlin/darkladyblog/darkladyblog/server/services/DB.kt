package darkladyblog.darkladyblog.server.services

import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

@Single
class DB() : KoinComponent, Closeable {
    private val application: Application by inject()
    private val config: ApplicationConfig = application.environment.config

    private val url: String
        get() = config.property("db.url").getString()
    private val driver: String
        get() = config.property("db.driver").getString()
    private val user: String
        get() = config.property("db.username").getString()
    private val password: String
        get() = config.property("db.password").getString()

    val db: Database by lazy { Database.connect(url, driver, user, password) }

    fun <R> transactionally(block: Transaction.() -> R): R =
        transaction(db) {
            block()
        }

    suspend fun <R> transactionallyAsync(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend Transaction.() -> R
    ): R =
        newSuspendedTransaction(context, db) {
            block()
        }

    fun <R> transactionallyCatching(block: Transaction.() -> R): Result<R> =
        runCatching {
            transactionally(block)
        }

    override fun close() {
    }
}
