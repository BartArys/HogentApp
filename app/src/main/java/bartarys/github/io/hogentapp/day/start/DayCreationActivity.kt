package bartarys.github.io.hogentapp.day.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.day.DayActivity
import bartarys.github.io.hogentapp.persistence.models.DayCreationSpec
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec

class DayCreationActivity : AppCompatActivity(), DayCreationCallback, NewGoalCallback {

    private lateinit var viewModel: DayCreationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DayCreationViewModel::class.java)

        setContentView(R.layout.activity_day_creation)

        if (findViewById<FrameLayout>(R.id.fragment_container_creation) != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_creation,
                    DayCreationFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun toOverview() {
        Intent(this, DayActivity    ::class.java)
            .let(this::startActivity)
    }

    override fun addGoal(goal: GoalCreationSpec) {
        viewModel.addGoal(goal)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_creation,
                DayCreationFragment.newInstance()
            )
            .commitNow()

    }


    override fun startNewGoalCreation() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_creation,
                NewGoalFragment.newInstance()
            )
            .addToBackStack(null)
            .commit()
    }
}
