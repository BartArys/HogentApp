package bartarys.github.io.hogentapp.day.review

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.databinding.DayReviewFragmentBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

interface DayReviewCallback{

    fun completeDay()

}

class DayReviewFragment : Fragment() {

    companion object {
        fun newInstance() = DayReviewFragment()
    }

    private lateinit var viewModel: DayReviewViewModel
    private lateinit var adapter: GoalReviewAdapter
    private lateinit var binding: DayReviewFragmentBinding
    private lateinit var callback: DayReviewCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = GoalReviewAdapter(context!!)
        callback = activity as DayReviewCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.day_review_fragment, container, false)

        binding.completeDay.onClick { callback.completeDay() }

        with(binding.recyclerViewGoals) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 1)
            adapter = this@DayReviewFragment.adapter

            val swipeHandler = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vh = viewHolder as GoalReviewHolder
                    when(direction){
                        ItemTouchHelper.LEFT -> viewModel.completeGoal(vh.mGoal, null)
                        ItemTouchHelper.RIGHT -> viewModel.failGoal(vh.mGoal, null)
                    }

                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false
            })

            swipeHandler.attachToRecyclerView(this)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(DayReviewViewModel::class.java)
        adapter.goals = viewModel.goals.value!!
        viewModel.goals.observe(this, Observer {
            adapter.goals = viewModel.goals.value!!
            if(viewModel.goals.value?.isEmpty() == true) {
                binding.completeDay.isEnabled = true
            }
        })
    }

}
