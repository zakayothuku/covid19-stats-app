package com.schatzdesigns.covid19stats.ui.stats.country.search

import coil.api.load
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.adapters.DataBoundAdapter
import com.schatzdesigns.covid19stats.databinding.ItemCountryBinding
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import com.schatzdesigns.covid19stats.utils.RecyclerviewOnClickListener
import com.schatzdesigns.covid19stats.viewholders.DataBoundViewHolder

class CountriesAdapter(private val recyclerviewOnClickListener: RecyclerviewOnClickListener) :
    DataBoundAdapter<ItemCountryBinding>(R.layout.item_country) {

    var mCountries: ArrayList<Country> = ArrayList()

    override fun bindItem(
        holder: DataBoundViewHolder<ItemCountryBinding>?,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val country = mCountries[position]

        holder?.binding?.country = country
        holder?.binding?.countryFlag?.load(country.flag) {
            crossfade(true)
            placeholder(R.drawable.ic_globe)
        }

        holder?.binding?.root?.setOnClickListener {
            recyclerviewOnClickListener.onRecyclerviewClicked(country)
        }
    }

    override fun getItemCount(): Int {
        return mCountries.size
    }

    fun updateCountries(countries: ArrayList<Country>) {
        mCountries = countries
        notifyDataSetChanged()
    }
}