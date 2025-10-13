package com.practice.practicerestdocs.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateUtils {

    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    private const val FIRST_DAY_OF_MONTH = 1
    private const val FORMAT_LOCAL_DATE = "yyyy-MM-dd"

    /**
     * 문자열 → LocalDate 변환
     *
     * @param source 원천 문자열
     * @return "yyyy-MM-dd" 형식으로 변환된 [LocalDate]
     */
    fun toLocalDate(source: String?): LocalDate? =
        toLocalDate(source, FORMAT_LOCAL_DATE)

    /**
     * 문자열 → LocalDate 변환 (형식 지정)
     */
    fun toLocalDate(source: String?, dateFormat: String): LocalDate? {
        if (source.isNullOrEmpty()) return null
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(dateFormat))
    }

    /**
     * LocalDate → 문자열 변환
     *
     * @param source 원천일
     * @return yyyy-MM-dd 형식 문자열
     */
    fun toString(source: LocalDate?): String? {
        if (source == null) return null
        return source.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT))
    }

    /**
     * LocalDate → 문자열 변환 (형식 지정)
     */
    fun toString(source: LocalDate?, dateFormat: String): String? {
        if (source == null) return null
        return source.format(DateTimeFormatter.ofPattern(dateFormat))
    }

    /**
     * 해당월 첫번째 날 (1일)
     */
    fun ofMonthFirstDay(source: LocalDate): LocalDate =
        source.withDayOfMonth(FIRST_DAY_OF_MONTH)

    /**
     * 해당월 마지막 날
     */
    fun ofMonthLastDay(source: LocalDate): LocalDate =
        source.withDayOfMonth(source.lengthOfMonth())
}

