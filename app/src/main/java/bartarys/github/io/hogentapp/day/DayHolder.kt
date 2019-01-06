package bartarys.github.io.hogentapp.day

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import bartarys.github.io.hogentapp.persistence.models.Day
import kotlinx.android.synthetic.main.layout_day_item.view.*

interface DayHolderListener {
    fun onItemClicked(day: Day)
}

class DayHolder(
    val view: View,
    val listener: DayHolderListener
) : RecyclerView.ViewHolder(view) {

    lateinit var mDay: Day

    init {
        itemView.setOnClickListener { listener.onItemClicked(mDay) }
    }

    fun bind(day: Day) {
        mDay = day
        view.textView.text = mDay.id.toString()
    }



}