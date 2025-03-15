package darkladyblog.darkladyblog.server.config

import darkladyblog.darkladyblog.common.model.AlertMessage
import darkladyblog.darkladyblog.server.services.AlertMessageSender
import darkladyblog.darkladyblog.server.services.DB
import io.github.flaxoos.ktor.server.plugins.taskscheduling.TaskScheduling
import io.github.flaxoos.ktor.server.plugins.taskscheduling.managers.lock.database.DefaultTaskLockTable
import io.github.flaxoos.ktor.server.plugins.taskscheduling.managers.lock.database.jdbc
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import org.jetbrains.exposed.sql.SchemaUtils
import org.koin.ktor.ext.inject

fun Application.configureJobs() {
    install(TaskScheduling) {
        jdbc {
            val db: DB by inject()
            database = db.db.also {
                db.transactionally { SchemaUtils.create(DefaultTaskLockTable) }
            }
        }
        task {
            name = "My task"
            task = { taskExecutionTime ->
                log.info("My task is running: $taskExecutionTime")
                val alertMessageSender: AlertMessageSender by inject()
                alertMessageSender.sendAll(AlertMessage(name))
            }
            kronSchedule = {
                hours {
                    0 every 1
                }
                minutes {
                    0 every 2
                }
                seconds {
                    0 every 60
                }
            }
            concurrency = 1
        }
    }
}
