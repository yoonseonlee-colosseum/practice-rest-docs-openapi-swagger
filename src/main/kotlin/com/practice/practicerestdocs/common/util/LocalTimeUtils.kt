package com.practice.practicerestdocs.common.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalTimeUtils {

    private const val FORMAT_LOCAL_TIME = "HH:mm:ss"

    /**
     * 문자열 → LocalTime 변환
     *
     * @param source 원천 문자열
     * @return "HH:mm:ss" 형식으로 변환된 [LocalTime]
     */
    fun toLocalTime(source: String?): LocalTime? =
        toLocalTime(source, FORMAT_LOCAL_TIME)

    /**
     * 문자열 → LocalTime 변환 (형식 지정)
     */
    fun toLocalTime(source: String?, timeFormat: String): LocalTime? {
        if (source.isNullOrEmpty()) return null
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(timeFormat))
    }

    /**
     * LocalTime → 문자열 변환
     *
     * @param source 원천
     * @return "HH:mm:ss" 형식으로 변환된 문자열
     */
    fun toString(source: LocalTime?): String? =
        toString(source, FORMAT_LOCAL_TIME)

    /**
     * LocalTime → 문자열 변환 (형식 지정)
     */
    fun toString(source: LocalTime?, timeFormat: String): String? {
        if (source == null) return null
        return source.format(DateTimeFormatter.ofPattern(timeFormat))
    }
}

