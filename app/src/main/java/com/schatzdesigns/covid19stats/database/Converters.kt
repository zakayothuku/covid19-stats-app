package com.schatzdesigns.covid19stats.database

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

/**
 * Created by Billionaire on 11/10/2017.
 */
class Converters {

    companion object {

        @JvmStatic
        @TypeConverter
        fun dateFromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @JvmStatic
        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

        @JvmStatic
        @TypeConverter
        fun bigDecimalFromString(value: String?): BigDecimal? {
            return if (value == null) null else BigDecimal(value)
        }

        @JvmStatic
        @TypeConverter
        fun bigDecimalToString(decimal: BigDecimal?): String? {
            return decimal?.toString()
        }

    }
}