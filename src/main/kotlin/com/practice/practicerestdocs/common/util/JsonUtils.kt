package com.practice.practicerestdocs.common.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object JsonUtils {

    private val mapper: ObjectMapper = ObjectMapper().apply {
        val javaTimeModule = JavaTimeModule().apply {
            addSerializer(
                LocalDateTime::class.java,
                LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
            addDeserializer(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
            addSerializer(
                ZonedDateTime::class.java,
                ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            )
            addDeserializer(
                ZonedDateTime::class.java,
                InstantDeserializer.ZONED_DATE_TIME
            )
        }

        registerModule(javaTimeModule)
        registerKotlinModule()
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    fun getMapper(): ObjectMapper = mapper

    fun <T> fromJson(inputStream: InputStream, clazz: Class<T>): T =
        try {
            mapper.readValue(inputStream, clazz)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(json: String, clazz: Class<T>): T =
        try {
            mapper.readValue(json, clazz)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }


    fun <T> fromJson(jsonNode: JsonNode, clazz: Class<T>): T =
        try {
            mapper.readValue(jsonNode.traverse(), clazz)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(inputStream: InputStream, typeRef: TypeReference<T>): T =
        try {
            mapper.readValue(inputStream, typeRef)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(json: String, typeRef: TypeReference<T>): T =
        try {
            mapper.readValue(json, typeRef)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(jsonNode: JsonNode, typeRef: TypeReference<T>): T =
        try {
            mapper.readValue(jsonNode.traverse(), typeRef)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(bytes: ByteArray, typeRef: TypeReference<T>): T =
        try {
            mapper.readValue(bytes, typeRef)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun <T> fromJson(bytes: ByteArray, clazz: Class<T>): T =
        try {
            mapper.readValue(bytes, clazz)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun fromJson(inputStream: InputStream): JsonNode =
        try {
            mapper.readTree(inputStream)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    fun fromJson(json: String): JsonNode =
        try {
            mapper.readTree(json)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }

    // ---------- JSON Array ----------
    fun <T> fromJsonArray(inputStream: InputStream, clazz: Class<T>): List<T> {
        val collectionType: CollectionType =
            TypeFactory.defaultInstance().constructCollectionType(List::class.java, clazz)
        return try {
            mapper.readValue(inputStream, collectionType)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }
    }

    fun <T> fromJsonArray(json: String, clazz: Class<T>): List<T> {
        val collectionType: CollectionType =
            TypeFactory.defaultInstance().constructCollectionType(List::class.java, clazz)
        return try {
            mapper.readValue(json, collectionType)
        } catch (e: IOException) {
            throw JsonDecodeException(e)
        }
    }

    // ---------- Object → JSON ----------
    fun toJson(obj: Any): String =
        try {
            mapper.writeValueAsString(obj)
        } catch (e: IOException) {
            throw JsonEncodeException(e)
        }

    fun toJsonByte(obj: Any): ByteArray =
        try {
            mapper.writeValueAsBytes(obj)
        } catch (e: IOException) {
            throw JsonEncodeException(e)
        }

    fun toPrettyJson(obj: Any): String =
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
        } catch (e: IOException) {
            throw JsonEncodeException(e)
        }

    // ---------- 기타 ----------
    fun get(jsonNode: JsonNode): String =
        jsonNode.asText()

    fun <T> convertValue(obj: Any, typeRef: TypeReference<T>): T =
        mapper.convertValue(obj, typeRef)

    class JsonEncodeException(cause: Throwable) : RuntimeException(cause)
    class JsonDecodeException(cause: Throwable) : RuntimeException(cause)
}

