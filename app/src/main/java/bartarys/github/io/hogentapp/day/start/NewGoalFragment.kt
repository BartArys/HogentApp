package bartarys.github.io.hogentapp.day.start

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.databinding.NewGoalFragmentBinding
import bartarys.github.io.hogentapp.persistence.models.GoalCreationSpec
import org.jetbrains.anko.sdk27.coroutines.onClick

interface NewGoalCallback {

    fun addGoal(goal: GoalCreationSpec)

}

class NewGoalFragment : Fragment() {

    companion object {
        fun newInstance() = NewGoalFragment()
    }

    private lateinit var viewModel: NewGoalViewModel
    private lateinit var binding : NewGoalFragmentBinding
    private lateinit var callback: NewGoalCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = activity as NewGoalCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_goal_fragment, container, false)

        binding.addButtonaddButton.onClick {
            if(viewModel.title.get() == null) {
                Toast.makeText(context, "please add a title", Toast.LENGTH_SHORT).show()
            }else{
                callback.addGoal(GoalCreationSpec(viewModel.title.get()!!, viewModel.description.get()))

            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewGoalViewModel::class.java)
        binding.viewModel = viewModel
    }

}
