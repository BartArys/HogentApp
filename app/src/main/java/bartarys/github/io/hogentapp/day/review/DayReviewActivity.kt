package bartarys.github.io.hogentapp.day.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.day.DayActivity

class DayReviewActivity : AppCompatActivity(), DayReviewCallback {

    lateinit var viewModel: DayReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_review)

        viewModel = ViewModelProviders.of(this).get(DayReviewViewModel::class.java)

        if (findViewById<FrameLayout>(R.id.fragment_container_review) != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_review,
                    DayReviewFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun completeDay() {
        viewModel.save()
        Intent(this, DayActivity::class.java)
            .let(this::startActivity)
    }
}
