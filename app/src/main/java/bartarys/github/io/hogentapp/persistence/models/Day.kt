package bartarys.github.io.hogentapp.persistence.models

import java.time.LocalDate
import java.time.LocalDateTime


data class Day(
    val id: Long,
    val day: LocalDate = LocalDate.now(),
    val goals: List<Goal> = emptyList()
) {

    fun isToday() : Boolean {
        return day.toEpochDay() == LocalDateTime.now().toLocalDate().toEpochDay()
    }

}

class DayCreationSpec(val goals: List<GoalCreationSpec>) {
    val day: LocalDate = LocalDate.now()
}