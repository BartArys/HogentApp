package bartarys.github.io.hogentapp.day

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bartarys.github.io.hogentapp.persistence.Persistence
import bartarys.github.io.hogentapp.persistence.models.Day
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DayListViewModel : ViewModel(), KoinComponent {

    val items = MutableLiveData<List<Day>>()

    val persistence: Persistence by inject()

    init {
        items.value = persistence.days()
    }

}


fun listToVisibility(list: List<*>): Int = when {
    list.isEmpty() -> View.GONE
    else -> View.VISIBLE
}