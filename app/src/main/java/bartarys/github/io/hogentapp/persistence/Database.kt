package bartarys.github.io.hogentapp.persistence

import bartarys.github.io.hogentapp.persistence.models.*
import org.jetbrains.anko.doAsync
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object Settings : LongIdTable() {

    val userId = varchar("user_id", 50)

    var dayStart = datetime("day_start")

    var dayEnd = datetime("day_end")

    var onlineSync = bool("online_sync")

}

object Days : LongIdTable() {
    val date = date("")
}

object Goals : LongIdTable() {
    val dayId = reference("day_id", Days.id)

    val title = varchar("title", 140)
    val description = varchar("description", 520).nullable()
    val success = bool("success").nullable()
    val reflection = varchar("reflection", 520).nullable()
}

data class SettingsData(val userId: String?, val dayStart: LocalTime, val dayEnd: LocalTime, val onlineSync: Boolean)

class DatabasePersistence(private val database: Database) {

    private fun toGoal(row: ResultRow): Goal {
        val id = row[Goals.id].value
        val title = row[Goals.title]
        val description = row[Goals.description]
        return when (val ratingValue = row[Goals.success]) {
            null -> Goal.Open(id, title, description)
            else -> {
                val reflection = row[Goals.reflection]
                when (row[Goals.success]!!) {
                    true -> Goal.Complete.Success(id, title, description, reflection)
                    false -> Goal.Complete.Failure(id, title, description, reflection)
                }
            }
        }
    }

    private fun toDay(it: ResultRow): Day {
        val id = it[Days.id].value
        val date = it[Days.date]
        val goals = Goals.select { Goals.dayId eq id }.map(::toGoal).toList()

        val localDate = LocalDate.of(date.year, date.monthOfYear, date.dayOfMonth)
        return Day(id, localDate, goals)
    }

    fun day(id: Long) : Day {
        return transaction(database) {
            Days.select { Days.id eq id }.first().let(::toDay)
        }
    }

    fun settings() : SettingsData {
        return transaction(database) {
            Settings.selectAll().firstOrNull()?.let { row ->
                val start = row[Settings.dayStart]
                val javaStart = LocalTime.of(start.hourOfDay, start.minuteOfHour)

                val end = row[Settings.dayEnd]
                val javaEnd = LocalTime.of(end.hourOfDay, end.minuteOfHour)

                SettingsData(row[Settings.userId], javaStart, javaEnd, row[Settings.onlineSync])
            } ?: SettingsData(null, LocalTime.of(6,0), LocalTime.of(20,0), false)
        }

    }

    fun updateSettings(spec: SettingsEditSpec){
        transaction(database) {
            Settings.update {
                spec.userId?.let { id -> it[userId] = id }
                val start = org.joda.time.LocalDateTime.now()
                    .withHourOfDay(spec.dayStart.hour)
                    .withMinuteOfHour(spec.dayStart.minute)
                    .toDateTime()

                it[dayStart] = start

                val end = org.joda.time.LocalDateTime.now()
                    .withHourOfDay(spec.dayEnd.hour)
                    .withMinuteOfHour(spec.dayEnd.minute)
                    .toDateTime()

                it[dayEnd] = end

                it[onlineSync] = spec.onlineSync
            }
        }
    }

    fun days(skip: Int, max: Int): List<Day> {
        return transaction(database) {
            Days.selectAll().orderBy(Days.date to false).limit(skip, max).map(::toDay).toList()
        }
    }

    fun updateGoal(spec: GoalCompletionSpec): Pair<Day, Goal> {
        return transaction(database) {
            Goals.update({ Goals.id eq spec.id }) {
                it[reflection] = spec.reflection
                it[success] = spec.complete
            }

            val row = Goals.select { Goals.id eq spec.id }.first()
            println(row)
            val day = Days.select { Days.id eq row[Goals.dayId] }.first().let(::toDay)
            val newGoal = toGoal(row)
            day to newGoal
        }
    }

    fun newGoal(spec: GoalCreationSpec): Pair<Day, Goal> {
        val id = transaction(database) {
            Goals.insertAndGetId {
                it[title] = spec.title
                it[description] = spec.description
                it[dayId] = selectAll().limit(n = 1).orderBy(Days.date to false).first()[Days.id]
            }.value
        }

        val day = today()
        return day to day.goals.first { it.id == id }
    }

    fun today(): Day {
        return transaction(database) {
            Days.selectAll().limit(n = 1).orderBy(Days.date to false)
                .firstOrNull()
                ?.let(::toDay)
                ?: run {
                    val date = DateTime.now()
                    val id = Days.insertAndGetId {
                        it[Days.date] = date
                    }.value

                    val localDate = LocalDate.of(date.year, date.monthOfYear, date.dayOfMonth)
                    Day(id, localDate)
                }
        }
    }

}
