package com.example.local_music.util

/**
 * Created by Emily on 11/1/21
 */
object TimeUtils {
    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     * @param duration The time needs to be parsed.
     */
    fun formatDuration(duration: Int): String {
        var dur = duration
        dur /= 1000 // milliseconds into seconds
        var minute = dur / 60
        val hour = minute / 60
        minute %= 60
        val second = dur % 60
        return if (hour != 0) {
            String.format("%2d:%02d:%02d", hour, minute, second)
        } else {
            String.format("%02d:%02d", minute, second)
        }
    }
}