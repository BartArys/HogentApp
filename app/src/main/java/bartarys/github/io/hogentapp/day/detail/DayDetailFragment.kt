package bartarys.github.io.hogentapp.day.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.databinding.DayDetailFragmentBinding
import org.koin.standalone.KoinComponent

class DayDetailFragment : Fragment(), KoinComponent {

    companion object {
        fun newInstance(id: Long): DayDetailFragment {
            val fragment = DayDetailFragment()
            val bundle = Bundle()
            bundle.putLong(idKey, id)
            fragment.arguments = bundle
            return fragment
        }

        private const val idKey = "ID"
    }

    private lateinit var viewModel: DayDetailViewModel
    private lateinit var binding: DayDetailFragmentBinding
    private lateinit var adapter: GoalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = GoalAdapter(context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.day_detail_fragment, container, false)

        with(binding.recyclerViewGoals) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@DayDetailFragment.adapter
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProviders.of(this, DayDetailViewModelFactory(arguments!!.getLong(idKey)))
            .get(DayDetailViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(DayDetailViewModel::class.java)
        binding.viewModel = viewModel
        adapter.goals = viewModel.day.goals
    }

}
