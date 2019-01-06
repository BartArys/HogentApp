package bartarys.github.io.hogentapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.settings.ui.settings.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commitNow()
        }
    }

}
