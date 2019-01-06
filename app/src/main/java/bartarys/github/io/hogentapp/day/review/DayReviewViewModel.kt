package bartarys.github.io.hogentapp.day.review

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel;
import bartarys.github.io.hogentapp.persistence.Persistence
import bartarys.github.io.hogentapp.persistence.models.Goal
import bartarys.github.io.hogentapp.persistence.models.GoalCompletionSpec
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DayReviewViewModel : ViewModel(), KoinComponent {

    val buttonEnabled = ButtonEnabled()

    val goals = MutableLiveData<List<Goal.Open>>(emptyList())

    private val persistence: Persistence by inject()
    private val completedGoals = mutableListOf<GoalCompletionSpec>()

    init {
        val today = persistence.today()
        goals.value = today.goals.filterIsInstance<Goal.Open>()
    }

    fun save(){
    }

    fun completeGoal(goal: Goal.Open, reflection: String?){
        val spec = GoalCompletionSpec(goal.id, true, reflection)
        completedGoals.add(spec)
        goals.value = goals.value!! - goal
    }

    fun failGoal(goal: Goal.Open, reflection: String?) {
        val spec = GoalCompletionSpec(goal.id, true, reflection)
        completedGoals.add(spec)
        goals.value = goals.value!! - goal
    }
}
