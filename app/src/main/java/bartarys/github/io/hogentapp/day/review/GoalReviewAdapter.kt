package bartarys.github.io.hogentapp.day.review

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.day.detail.GoalDiffCallback
import bartarys.github.io.hogentapp.persistence.models.Goal

class GoalReviewAdapter(val context: Context) : RecyclerView.Adapter<GoalReviewHolder>() {


    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var goals: List<Goal.Open> = emptyList()
        set(value) {
            val postDiffCallback = GoalDiffCallback(goals, field)
            val diffResult = DiffUtil.calculateDiff(postDiffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalReviewHolder {
        val itemView = layoutInflater.inflate(R.layout.layout_goal_item, parent, false)
        return GoalReviewHolder(itemView)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalReviewHolder, position: Int) {
        holder.bind(goals[position])
    }


}

