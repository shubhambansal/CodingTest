package test.app.nuegelb.util

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_FROM = "yyyy-MM-dd"
private const val DATE_FORMAT_TO = "dd MMM yyyy"

object DateUtil {

    val fromDateFormat = SimpleDateFormat(DATE_FORMAT_FROM, Locale.US)
    val toDateFormat = SimpleDateFormat(DATE_FORMAT_TO, Locale.US)

    fun todayDate(): String {
        return fromDateFormat.format(Date().time)
    }

}