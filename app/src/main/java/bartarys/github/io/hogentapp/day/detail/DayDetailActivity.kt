package bartarys.github.io.hogentapp.day.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import bartarys.github.io.hogentapp.R

class DayDetailActivity : AppCompatActivity() {

    companion object {
        const val dayKey = "DAY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_detail)

        if (findViewById<FrameLayout>(R.id.fragment_container_detail) != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_detail,
                    DayDetailFragment.newInstance(intent.extras!!.getLong(dayKey))
                )
                .commitNow()
        }
    }
}
