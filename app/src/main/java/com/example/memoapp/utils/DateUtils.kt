package com.example.memoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
    private val dateTimeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN)
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.JAPAN)

    /**
     * 日付を文字列に変換（yyyy/MM/dd）
     */
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    /**
     * 日時を文字列に変換（yyyy/MM/dd HH:mm）
     */
    fun formatDateTime(date: Date): String {
        return dateTimeFormat.format(date)
    }

    /**
     * 時刻を文字列に変換（HH:mm）
     */
    fun formatTime(date: Date): String {
        return timeFormat.format(date)
    }

    /**
     * 相対時間を表示（今日、昨日、など）
     */
    fun getRelativeTimeString(date: Date): String {
        val now = Date()
        val diff = now.time - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "たった今"
            minutes < 60 -> "${minutes}分前"
            hours < 24 -> "${hours}時間前"
            days == 1L -> "昨日"
            days < 7 -> "${days}日前"
            else -> formatDate(date)
        }
    }
}