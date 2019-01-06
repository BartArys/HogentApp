package bartarys.github.io.hogentapp.settings.ui.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.persistence.Persistence
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ConfirmDeleteDialogFragment : DialogFragment(), KoinComponent {

    private val persistence: Persistence by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.delete_data) { _, _ -> persistence.deleteOnline() }
                .setNegativeButton(R.string.delete_cancel) { _, _ -> dismiss() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}