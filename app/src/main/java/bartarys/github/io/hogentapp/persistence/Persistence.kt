package bartarys.github.io.hogentapp.persistence

import bartarys.github.io.hogentapp.persistence.models.*
import org.jetbrains.anko.doAsync
import bartarys.github.io.hogentapp.persistence.models.Settings


class Persistence(private val database: DatabasePersistence, val online: OnlinePersistence) {

    private val settings get() = settings()

    fun updateSettings(spec: SettingsEditSpec) {
        doAsync {
            database.updateSettings(spec)
        }
    }

    fun settings(): Settings {
        return database.settings().let {
            Settings(it.userId, it.dayStart, it.dayEnd, it.onlineSync, this)
        }
    }

    fun deleteOnline() {
        doAsync {
            if (settings.onlineSync) {
                online.deleteInfo()
            }
        }
    }

    fun days(): List<Day> {
        return if (settings.onlineSync) {
            online.days(0, 7)
        } else {
            database.days(0, 7)
        }
    }

    fun day(id: Long): Day {
        return if (settings.onlineSync) {
            online.day(id)
        } else {
            database.day(id)
        }
    }

    fun addDay(day: DayCreationSpec) {
        doAsync {
            if (settings.onlineSync) day.goals.forEach { online.addGoal(it) }
            day.goals.forEach { database.newGoal(it) }
        }
    }

    fun today(): Day {
        return if (settings.onlineSync) {
            online.today()
        } else {
            database.today()
        }
    }

    fun completeToday(goals: List<GoalCompletionSpec>) {
        if(settings.onlineSync) goals.forEach { online.updateGoal(it.id.toString(), it) }
        goals.forEach { database.updateGoal(it) }
    }

}