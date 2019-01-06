package bartarys.github.io.hogentapp.settings.ui.settings

import androidx.lifecycle.ViewModel
import bartarys.github.io.hogentapp.persistence.models.Settings
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SelectTimeModel : KoinComponent {

    enum class TargetField { START, END }

    private val settings: Settings by inject()

    var target: TargetField = TargetField.START
    var time = settings.dayStart

}