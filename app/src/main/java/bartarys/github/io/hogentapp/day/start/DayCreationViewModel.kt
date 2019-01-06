package bartarys.github.io.hogentapp.day.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import bartarys.github.io.hogentapp.persistence.Persistence
import bartarys.github.io.hogentapp.persistence.models.DayCreationSpec
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DayCreationViewModel : ViewModel(), KoinComponent {


    val persistence: Persistence by inject()

    val goals: MutableLiveData<List<GoalCreationSpec>> = MutableLiveData(emptyList())

    fun addGoal(goal: GoalCreationSpec) {
        goals.value = goals.value!! + goal
    }

    fun removeGoal(goal: GoalCreationSpec){
        goals.value = goals.value!! - goal
    }

    fun save() {
        val day = DayCreationSpec(goals.value!!)
        persistence.addDay(day)
    }

}
