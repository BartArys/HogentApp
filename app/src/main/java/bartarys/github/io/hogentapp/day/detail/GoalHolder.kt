package bartarys.github.io.hogentapp.day.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.persistence.models.Goal
import kotlinx.android.synthetic.main.layout_goal_item.view.*

class GoalHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var mGoal: Goal

    fun bind(goal: Goal) {
        mGoal = goal
        view.textView.text = goal.title
        when (goal) {
            is Goal.Complete.Success -> view.textView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_thumb_up_black_24dp,
                0,
                0,
                0
            )

            is Goal.Complete.Failure -> view.textView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_thumb_down_black_24dp,
                0,
                0,
                0
            )

        }


    }
}