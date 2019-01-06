package bartarys.github.io.hogentapp.day.detail

import androidx.lifecycle.ViewModel
import bartarys.github.io.hogentapp.persistence.models.Day
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DayDetailViewModel(val day: Day) : ViewModel()


fun dateToString(day: Day) : String {
    return day.day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
}
