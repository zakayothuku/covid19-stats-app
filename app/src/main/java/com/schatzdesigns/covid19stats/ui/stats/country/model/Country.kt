package com.schatzdesigns.covid19stats.ui.stats.country.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "countries")
data class Country(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val flag: String?,

    @SerializedName("total_cases")
    @ColumnInfo(name = "total_cases")
    val totalCases: String,

    @SerializedName("new_cases")
    @ColumnInfo(name = "new_cases")
    val newCases: String,

    @SerializedName("total_deaths")
    @ColumnInfo(name = "total_deaths")
    val totalDeaths: String,

    @SerializedName("new_deaths")
    @ColumnInfo(name = "new_deaths")
    val newDeaths: String,

    @SerializedName("total_recovered")
    @ColumnInfo(name = "total_recovered")
    val totalRecovered: String,

    @SerializedName("active_cases")
    @ColumnInfo(name = "active_cases")
    val activeCases: String,

    @SerializedName("critical_cases")
    @ColumnInfo(name = "critical_cases")
    val criticalCases: String

) : Parcelable