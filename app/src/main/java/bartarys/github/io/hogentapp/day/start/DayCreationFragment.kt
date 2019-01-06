package bartarys.github.io.hogentapp.day.start

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*

import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.databinding.DayCreationFragmentBinding
import bartarys.github.io.hogentapp.persistence.models.DayCreationSpec
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec
import org.jetbrains.anko.sdk27.coroutines.onClick

interface DayCreationCallback {

    fun startNewGoalCreation()

    fun toOverview()

}

class DayCreationFragment : Fragment() {

    companion object {
        fun newInstance() = DayCreationFragment()
    }

    private lateinit var viewModel: DayCreationViewModel
    private lateinit var binding: DayCreationFragmentBinding
    private lateinit var adapter: NewGoalAdapter
    private lateinit var callback: DayCreationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NewGoalAdapter(context!!)
        callback = activity as DayCreationCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.day_creation_fragment, container, false)

        with(binding.recyclerViewGoals) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 1)
            adapter = this@DayCreationFragment.adapter

            val swipeHandler = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vh = viewHolder as NewGoalHolder
                    viewModel.removeGoal(vh.mGoal)
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false
            })

            swipeHandler.attachToRecyclerView(this)

        }

        binding.createDay.onClick {
            viewModel.save()
            callback.toOverview()

        }

        binding.addButtonaddButton.onClick {
            callback.startNewGoalCreation()
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(DayCreationViewModel::class.java)
        adapter.goals = viewModel.goals.value!!
        viewModel.goals.observe(this, Observer {
            adapter.goals = it
        })
    }

}

