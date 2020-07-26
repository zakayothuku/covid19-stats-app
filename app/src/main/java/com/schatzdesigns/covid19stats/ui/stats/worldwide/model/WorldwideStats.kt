package com.schatzdesigns.covid19stats.ui.stats.worldwide.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "worldwide_stats")
data class WorldwideStats(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val rowId: Long,
    val timestamp: String,
    val reports: String,
    val recovered: String,
    val deaths: String,

    @Embedded(prefix = "a_")
    @SerializedName("active_cases")
    val activeCases: ActiveCases,

    @Embedded(prefix = "c_")
    @SerializedName("closed_cases")
    val closedCases: ClosedCases

) : Parcelable {

    @Parcelize
    data class ActiveCases(

        @SerializedName("currently_infected")
        @ColumnInfo(name = "currently_infected")
        val currentlyInfected: String,

        @SerializedName("mild_condition")
        @ColumnInfo(name = "mild_condition")
        val mildCondition: String,


        val critical: String
    ) : Parcelable

    @Parcelize
    data class ClosedCases(

        @SerializedName("all_closed")
        @ColumnInfo(name = "all_closed")
        val allClosed: String,

        val recovered: String,
        val deaths: String
    ) : Parcelable
}