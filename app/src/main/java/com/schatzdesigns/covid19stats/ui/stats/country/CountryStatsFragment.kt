package com.schatzdesigns.covid19stats.ui.stats.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.schatzdesigns.covid19stats.BaseFragment
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.databinding.FragmentCountryStatsBinding
import com.schatzdesigns.covid19stats.ui.MainActivity
import com.schatzdesigns.covid19stats.ui.stats.worldwide.WorldwideStatsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryStatsFragment : BaseFragment() {

    private val args: CountryStatsFragmentArgs by navArgs()
    private lateinit var binding: FragmentCountryStatsBinding
    private lateinit var worldwideStatsViewModel: WorldwideStatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_stats, container, false)

        worldwideStatsViewModel =
            ViewModelProvider(this, viewModelFactory).get(WorldwideStatsViewModel::class.java)

        val country = args.country
        binding.country = country

        launch(coroutineContext) {
            val timestamp = withContext(Dispatchers.IO) {
                return@withContext worldwideStatsViewModel.getTimestamp()
            }
            binding.lastUpdated.text = timestamp
        }

        (activity as MainActivity?)?.updateTitle(country.name)

        binding.flag.load(country.flag) {
            crossfade(true)
            placeholder(R.drawable.ic_globe)
        }

        binding.flag.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}
