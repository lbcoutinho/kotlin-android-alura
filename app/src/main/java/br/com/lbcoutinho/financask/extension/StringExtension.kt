package br.com.lbcoutinho.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun String.convertToCalendar(): Calendar {
    val date = Calendar.getInstance()
    date.time = SimpleDateFormat("dd/MM/yyyy").parse(this)
    return date
}