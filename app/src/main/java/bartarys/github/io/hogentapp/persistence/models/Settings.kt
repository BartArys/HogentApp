package bartarys.github.io.hogentapp.persistence.models

import bartarys.github.io.hogentapp.persistence.Persistence
import org.jetbrains.anko.doAsync
import java.time.LocalTime

data class SettingsEditSpec(val userId: String?, val dayStart: LocalTime, val dayEnd: LocalTime, val onlineSync: Boolean)

class Settings(userId: String?, dayStart: LocalTime, dayEnd: LocalTime, onlineSync: Boolean, private val persistence: Persistence) {

    var userId: String? = userId
    set(value) {
        doAsync { persistence.updateSettings(asEditSpec()) }
        field = value
    }

    var dayStart: LocalTime = dayStart
    set(value) {
        doAsync { persistence.updateSettings(asEditSpec()) }
        field = value
    }

    var dayEnd: LocalTime = dayEnd
    set(value) {
        doAsync { persistence.updateSettings(asEditSpec()) }
        field = value
    }

    var onlineSync: Boolean = onlineSync
    set(value) {
        doAsync { persistence.updateSettings(asEditSpec()) }
        field = value
    }

    private fun asEditSpec() = SettingsEditSpec(userId, dayStart, dayEnd, onlineSync)
}