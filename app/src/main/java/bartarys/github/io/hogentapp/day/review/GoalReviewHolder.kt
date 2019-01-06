package bartarys.github.io.hogentapp.day.review

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.persistence.models.Goal

class GoalReviewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    lateinit var mGoal: Goal.Open

    fun bind(goal: Goal.Open) {
        mGoal = goal
    }

}