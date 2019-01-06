package bartarys.github.io.hogentapp.day.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.persistence.models.Goal
import org.koin.standalone.KoinComponent

class GoalAdapter(val context: Context) : RecyclerView.Adapter<GoalHolder>(), KoinComponent {

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var goals: List<Goal> = emptyList()
    set(value) {
        val postDiffCallback = GoalDiffCallback(goals, value)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)
        field = value
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val itemView = layoutInflater.inflate(R.layout.layout_goal_item, parent, false)
        return GoalHolder(itemView)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goals[position])
    }


}

class GoalDiffCallback(private val oldGoals: List<Goal>, private val newGoals: List<Goal>) : DiffUtil.Callback() {

    override fun getNewListSize(): Int = newGoals.size

    override fun getOldListSize(): Int = oldGoals.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGoals[oldItemPosition].id == newGoals[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGoals[oldItemPosition] == newGoals[newItemPosition]
    }
}