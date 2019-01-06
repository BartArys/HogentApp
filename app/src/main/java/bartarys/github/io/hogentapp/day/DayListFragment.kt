package bartarys.github.io.hogentapp.day

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.databinding.DayListFragmentBinding
import bartarys.github.io.hogentapp.persistence.Persistence
import bartarys.github.io.hogentapp.persistence.models.Day
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface DayListCallback {

    fun onDayClicked(day: Day)

    fun review()

    fun settings()

}

class DayListFragment : Fragment(), DayHolderListener, KoinComponent {

    companion object {
        const val lastPositionKey = "last_position"

        fun newInstance() = DayListFragment()
    }

    private val persistence: Persistence by inject()
    private var mLastChangePosition: Int = 0
    private lateinit var callback: DayListCallback
    private lateinit var viewModel: DayListViewModel
    private lateinit var adapter: DayAdapter
    private lateinit var binding: DayListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mLastChangePosition = savedInstanceState.getInt(lastPositionKey, 0)
        }
        adapter = DayAdapter(context!!, this)
        callback = activity as DayListCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.day_list_fragment, container, false)

        with(binding.recyclerViewDays) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@DayListFragment.adapter
        }

        binding.today.onClick { callback.onDayClicked(persistence.today()) }
        binding.settings.onClick { callback.settings() }
        binding.review.onClick { callback.review() }

        return binding.root
    }

    override fun onItemClicked(day: Day) {
        callback.onDayClicked(day)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DayListViewModel::class.java)
        adapter.days = viewModel.items.value!!
        binding.viewModel = viewModel
    }


}
