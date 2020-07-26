package com.schatzdesigns.covid19stats.ui.stats.country.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.schatzdesigns.covid19stats.BaseFragment
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.api.ResourceState
import com.schatzdesigns.covid19stats.databinding.FragmentCountriesBinding
import com.schatzdesigns.covid19stats.ui.MainActivity
import com.schatzdesigns.covid19stats.ui.stats.country.CountriesViewModel
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import com.schatzdesigns.covid19stats.utils.RecyclerviewOnClickListener
import java.util.*
import kotlin.collections.ArrayList

class CountriesFragment : BaseFragment(), RecyclerviewOnClickListener, View.OnClickListener {

    private lateinit var binding: FragmentCountriesBinding
    private lateinit var countriesAdapter: CountriesAdapter
    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var countriesRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)

        (activity as MainActivity?)?.updateTitle("All Countries")

        countriesViewModel =
            ViewModelProvider(this, viewModelFactory).get(CountriesViewModel::class.java)

        countriesAdapter = CountriesAdapter(this)

        countriesRecyclerView = binding.countriesRecyclerView
        countriesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = countriesAdapter
        }

        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            refresh()
        }

        binding.worldwide.setOnClickListener {
            findNavController().popBackStack(R.id.worldStatsFragment, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(countriesViewModel) {

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
                showSnackbarMessage(binding.countries, error?.message!!)
            })

            countries.observe(viewLifecycleOwner, Observer { countries ->
                binding.countries.visibility = View.VISIBLE
                countriesAdapter.updateCountries(countries as ArrayList<Country>)
                binding.searchCountry.apply {
                    doAfterTextChanged {
                        if (it.toString().isEmpty()) {
                            countriesAdapter.updateCountries(countries)
                        } else {
                            filterCountry(countries, it.toString())
                        }
                    }
                }
            })
        }

        getCountries()
    }

    private fun getCountries() {
        countriesViewModel.getCountries()
    }

    private fun refresh() {
        updateRefreshState(true)
        getCountries()
    }

    private fun updateRefreshState(refreshing: Boolean) {
        swipeRefresh.isRefreshing = refreshing
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.action_retry -> refresh()
        }
    }

    override fun onRecyclerviewClicked(country: Country) {
        findNavController().navigate(
            R.id.countryStatsFragment,
            bundleOf("country" to country)
        )
        binding.searchCountry.text.clear()
    }

    private fun filterCountry(countries: ArrayList<Country>, searchTerm: String) {
        val filteredCountries = ArrayList<Country>()
        for (country in countries) {
            if (country.name.toLowerCase(Locale.getDefault()).startsWith(
                    searchTerm.toLowerCase(
                        Locale.getDefault()
                    )
                )
            ) {
                filteredCountries.add(country)
            }
        }
        countriesAdapter.updateCountries(filteredCountries)
    }

}