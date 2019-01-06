package bartarys.github.io.hogentapp.settings.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.persistence.models.Settings
import org.jetbrains.anko.support.v4.runOnUiThread
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.time.format.DateTimeFormatter


class SettingsFragment : PreferenceFragmentCompat(), KoinComponent, TimeSetCallback {

    private val settings: Settings by inject()

    private val timeModel: SelectTimeModel by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_fragment, rootKey)
        setData()
    }

    private fun setData() {

        //sync toggle
        with(findPreference(getString(R.string.online_sync)) as SwitchPreferenceCompat) {
            isChecked = settings.onlineSync
            summary = when (settings.onlineSync) {
                true -> getString(R.string.online_sync_enabled)
                false -> getString(R.string.online_sync_disabled)
            }

            setOnPreferenceChangeListener { _, newValue ->
                settings.onlineSync = newValue as Boolean
                summary = when (settings.onlineSync) {
                    true -> getString(R.string.online_sync_enabled)
                    false -> getString(R.string.online_sync_disabled)
                }

                true
            }
        }


        with(findPreference(getString(R.string.day_start))) {

            summary = getString(
                R.string.day_start_summary,
                settings.dayStart.format(DateTimeFormatter.ofPattern("HH:mm"))
            )

            setOnPreferenceClickListener {
                timeModel.time = settings.dayStart
                timeModel.target = SelectTimeModel.TargetField.START
                val dialog = SelectTimeDialogFragment.newInstance(this@SettingsFragment)
                dialog.show(fragmentManager!!, "start")
                summary = getString(
                    R.string.day_start_summary,
                    settings.dayStart.format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                true
            }

        }

        with(findPreference(getString(R.string.day_end))) {


            summary = getString(R.string.day_end_summary, settings.dayEnd.format(DateTimeFormatter.ofPattern("HH:mm")))

            setOnPreferenceClickListener {
                timeModel.time = settings.dayEnd
                timeModel.target = SelectTimeModel.TargetField.END
                val dialog = SelectTimeDialogFragment.newInstance(this@SettingsFragment)
                dialog.show(fragmentManager!!, "end")
                summary = getString(
                    R.string.day_end_summary,
                    settings.dayEnd.format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                true
            }

        }


        with(findPreference(getString(R.string.delete))) {

            setOnPreferenceClickListener {
                runOnUiThread {
                    val dialog = ConfirmDeleteDialogFragment()
                    dialog.show(fragmentManager!!, "delete")
                }
                true
            }

        }

    }

    override fun onTimeSet() {
        runOnUiThread {
            val target = timeModel.target
            val time = timeModel.time

            when (target) {
                SelectTimeModel.TargetField.START -> {
                    settings.dayStart = time
                    findPreference(getString(R.string.day_start)).summary =
                            getString(
                                R.string.day_start_summary,
                                time.format(DateTimeFormatter.ofPattern("HH:mm"))
                            )
                }

                SelectTimeModel.TargetField.END -> {
                    settings.dayEnd = time
                    findPreference(getString(R.string.day_end)).summary =
                            getString(
                                R.string.day_end_summary,
                                time.format(DateTimeFormatter.ofPattern("HH:mm"))
                            )
                }

            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }


}
