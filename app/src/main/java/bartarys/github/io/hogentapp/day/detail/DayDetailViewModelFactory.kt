package bartarys.github.io.hogentapp.day.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bartarys.github.io.hogentapp.persistence.Persistence
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DayDetailViewModelFactory(private val id: Long) : ViewModelProvider.Factory, KoinComponent {

    val persistence: Persistence by inject()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DayDetailViewModel(persistence.day(id)) as T
    }

}