package com.schatzdesigns.covid19stats.ui.stats.worldwide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.schatzdesigns.covid19stats.BaseFragment
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.api.ResourceState
import com.schatzdesigns.covid19stats.databinding.FragmentWorldwideStatsBinding
import com.schatzdesigns.covid19stats.ui.MainActivity
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats


class WorldwideStatsFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentWorldwideStatsBinding
    private lateinit var worldwideStatsViewModel: WorldwideStatsViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var worldwideStat: WorldwideStats? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_worldwide_stats, container, false)

        (activity as MainActivity?)?.updateTitle("Worldwide")

        worldwideStatsViewModel =
            ViewModelProvider(this, viewModelFactory).get(WorldwideStatsViewModel::class.java)

        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            refresh()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionSearchByCountry.setOnClickListener(this)

        with(worldwideStatsViewModel) {
            loadingState.observe(viewLifecycleOwner, Observer { state ->
                when (state) {
                    ResourceState.STATE_LOADING -> {
                        updateRefreshState(true)
                    }
                    ResourceState.STATE_SUCCESS -> {
                        updateRefreshState(false)
                    }
                    ResourceState.STATE_FAILED -> {
                        updateRefreshState(false)
                    }
                }
            })
            apiError.observe(viewLifecycleOwner, Observer { error ->
                showSnackbarMessage(binding.worldStats, error?.message!!)
            })
            worldwideStats.observe(viewLifecycleOwner, Observer { stats ->
                worldwideStat = stats
                binding.worldwideStats = worldwideStat
                binding.casesTitle.text = getString(R.string.cases)
            })
        }

        getStatistics()
    }

    private fun getStatistics() {
        worldwideStatsViewModel.getWorldwideStatistics()
    }

    private fun refresh() {
        updateRefreshState(true)
        getStatistics()
    }

    private fun updateRefreshState(refreshing: Boolean) {
        swipeRefresh.isRefreshing = refreshing
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.action_search_by_country -> {
                findNavController().navigate(R.id.countriesFragment)
            }
        }
    }
}
