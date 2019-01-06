package bartarys.github.io.hogentapp.settings.ui.settings

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.KoinComponent
import java.time.LocalTime

interface TimeSetCallback {

    fun onTimeSet()

}

class SelectTimeDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener, KoinComponent {

    private val model: SelectTimeModel by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hour = model.time.hour
        val minute = model.time.minute

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = LocalTime.of(hourOfDay, minute)
        model.time = time

        val callback = targetFragment as TimeSetCallback
        callback.onTimeSet()
    }

    companion object {

        fun newInstance(target: Fragment) : SelectTimeDialogFragment {
            val fragment = SelectTimeDialogFragment()
            fragment.setTargetFragment(target, 0)
            return fragment
        }
    }

}