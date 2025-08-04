package darkladyblog.darkladyblog.server.services

import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Connection
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

    fun <R> transactionally(
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = false,
        block: Transaction.() -> R
    ): R =
        transaction(
            db = db,
            transactionIsolation = isolationLevel?.value ?: db.transactionManager.defaultIsolationLevel,
            readOnly = readonly,
            statement = block
        )

    suspend fun <R> transactionallyAsync(
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = false,
        context: CoroutineContext = Dispatchers.IO,
        block: suspend Transaction.() -> R
    ): R =
        newSuspendedTransaction(
            context = context,
            db = db,
            transactionIsolation = isolationLevel?.value ?: db.transactionManager.defaultIsolationLevel,
            readOnly = readonly,
            statement = block
        )

    fun <R> transactionallyCatching(
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = false,
        block: Transaction.() -> R
    ): Result<R> =
        runCatching {
            transactionally(
                isolationLevel = isolationLevel,
                readonly = readonly,
                block = block
            )
        }

    fun <R> transactionallyReadonly(
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = true,
        block: Transaction.() -> R
    ): R =
        transaction(
            db = db,
            transactionIsolation = isolationLevel?.value ?: db.transactionManager.defaultIsolationLevel,
            readOnly = readonly,
            statement = block
        )

    suspend fun <R> transactionallyReadonlyAsync(
        context: CoroutineContext = Dispatchers.IO,
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = true,
        block: suspend Transaction.() -> R
    ): R =
        newSuspendedTransaction(
            context = context,
            db = db,
            readOnly = readonly,
            transactionIsolation = isolationLevel?.value ?: db.transactionManager.defaultIsolationLevel,
            statement = block
        )

    fun <R> transactionallyReadonlyCatching(
        isolationLevel: IsolationLevel? = null,
        readonly: Boolean = true,
        block: Transaction.() -> R
    ): Result<R> =
        runCatching {
            transactionallyReadonly(
                isolationLevel = isolationLevel,
                readonly = readonly,
                block = block
            )
        }

    override fun close() {
    }

    enum class IsolationLevel(val value: Int) {
        TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
        TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
        TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
        TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE),
        TRANSACTION_NONE(Connection.TRANSACTION_NONE),
        ;

        companion object {
            fun of(value: Int): IsolationLevel =
                entries.first { it.value == value }
        }
    }
}
