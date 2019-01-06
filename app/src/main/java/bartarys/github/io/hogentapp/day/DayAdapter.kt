package bartarys.github.io.hogentapp.day

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.persistence.models.Day
import org.koin.standalone.KoinComponent


class DayAdapter(val context: Context, private val listener: DayHolderListener) : RecyclerView.Adapter<DayHolder>(), KoinComponent {

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var days: List<Day> = emptyList()
        set(value) {
                val postDiffCallback = DayDiffCallback(days, value)
                val diffResult = DiffUtil.calculateDiff(postDiffCallback)
                field = value
                diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val itemView = layoutInflater.inflate(R.layout.layout_day_item, parent, false)
        return DayHolder(itemView, listener)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(days[position])
    }

}

private class DayDiffCallback(private val oldDays: List<Day>, private val newDays: List<Day>) : DiffUtil.Callback() {

    override fun getNewListSize(): Int = newDays.size

    override fun getOldListSize(): Int = oldDays.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDays[oldItemPosition].id == newDays[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDays[oldItemPosition] == newDays[newItemPosition]
    }
}