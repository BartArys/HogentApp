package bartarys.github.io.hogentapp.day.start

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.day.detail.GoalDiffCallback
import bartarys.github.io.hogentapp.day.review.GoalReviewHolder
import bartarys.github.io.hogentapp.persistence.models.Goal
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec

class NewGoalAdapter(val context: Context) : RecyclerView.Adapter<NewGoalHolder>() {


    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var goals: List<GoalCreationSpec> = emptyList()
        set(value) {
            val postDiffCallback = GoalDiffCallback(goals, value)
            val diffResult = DiffUtil.calculateDiff(postDiffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewGoalHolder {
        val itemView = layoutInflater.inflate(R.layout.layout_goal_creation_item, parent, false)
        return NewGoalHolder(itemView)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: NewGoalHolder, position: Int) {
        holder.bind(goals[position])
    }



}

private class GoalDiffCallback(private val oldGoals: List<GoalCreationSpec>, private val newGoals: List<GoalCreationSpec>) : DiffUtil.Callback() {

    override fun getNewListSize(): Int = newGoals.size

    override fun getOldListSize(): Int = oldGoals.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGoals[oldItemPosition] == newGoals[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGoals[oldItemPosition] == newGoals[newItemPosition]
    }
}