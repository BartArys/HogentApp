package bartarys.github.io.hogentapp.day.start

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec
import kotlinx.android.synthetic.main.layout_goal_creation_item.view.*

class NewGoalHolder(val view: View) : RecyclerView.ViewHolder(view) {

    lateinit var mGoal: GoalCreationSpec

    fun bind(goal: GoalCreationSpec) {
        mGoal = goal
        view.textView.text = goal.title

    }


}