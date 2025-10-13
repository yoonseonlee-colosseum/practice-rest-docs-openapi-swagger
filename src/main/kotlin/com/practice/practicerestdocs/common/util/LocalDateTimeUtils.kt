package com.practice.practicerestdocs.common.util

import com.practice.practicerestdocs.config.Constant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalDateTimeUtils {

    private const val FORMAT_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * 문자열 → LocalDateTime 변환
     *
     * @param source 변환문자열
     * @return yyyy-MM-dd'T'HH:mm:ss 형식 [LocalDateTime]
     */
    fun toLocalDateTime(source: String?): LocalDateTime? =
        toLocalDateTime(source, FORMAT_LOCAL_DATE_TIME)

    /**
     * 문자열 → LocalDateTime 변환 (패턴 지정)
     */
    fun toLocalDateTime(source: String?, dateTimeFormat: String): LocalDateTime? {
        if (source.isNullOrEmpty()) return null
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(dateTimeFormat))
    }

    /**
     * LocalDateTime → 문자열 변환
     */
    fun toString(source: LocalDateTime?): String? =
        toString(source, FORMAT_LOCAL_DATE_TIME)

    /**
     * LocalDateTime → 문자열 변환 (패턴 지정)
     */
    fun toString(source: LocalDateTime?, dateTimeFormat: String): String? {
        if (source == null) return null
        return source.format(DateTimeFormatter.ofPattern(dateTimeFormat))
    }

    /**
     * 해당일 시작 시각 (00:00:00)
     */
    fun ofFirst(source: LocalDate): LocalDateTime =
        LocalDateTime.of(source, LocalTime.MIN)

    /**
     * 해당일 종료 시각 (23:59:59.999999999)
     */
    fun ofLast(source: LocalDate): LocalDateTime =
        LocalDateTime.of(source, LocalTime.MAX)

    /**
     * 해당월 첫날의 시작 시각
     */
    fun ofMonthFirstDateTime(source: LocalDate): LocalDateTime =
        ofFirst(LocalDateUtils.ofMonthFirstDay(source))

    /**
     * 해당월 마지막날의 종료 시각
     */
    fun ofMonthLastDateTime(source: LocalDate): LocalDateTime =
        ofLast(LocalDateUtils.ofMonthLastDay(source))

    /**
     * LocalDateTime → Asia/Seoul ZonedDateTime 문자열 변환
     */
    fun convertZoneDateTimeFormat(source: LocalDateTime?): String? {
        if (source == null) return null
        return source.atZone(Constant.DEFAULT_ZONE_ID)
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }
}

