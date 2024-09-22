package dev.kimchiloof.financially.utils

import java.time.format.DateTimeFormatter

open class Constants {
    companion object {
        const val DATABASE_NAME = "receipts"
        val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }

    class UI {
        class RADIUS {
            companion object {
                const val S = 8
                const val M = 16
                const val L = 24
            }
        }
    }



}
