package com.practice.practicerestdocs.documenation

import com.practice.practicerestdocs.common.util.JsonUtils
import com.practice.practicerestdocs.common.util.LocalDateTimeUtils
import com.practice.practicerestdocs.common.util.LocalDateUtils
import com.practice.practicerestdocs.common.util.LocalTimeUtils
import com.practice.practicerestdocs.infrastructure.web.common.handler.GlobalRestControllerExceptionHandler
import org.springframework.core.convert.converter.Converter
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import org.springframework.web.filter.CharacterEncodingFilter
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object MockMvcFactory {

    fun getRestDocsMockMvc(
        restDocumentationContextProvider: RestDocumentationContextProvider,
        host: String,
        vararg controllers: Any,
    ): MockMvc {

        val documentationConfigurer =
            MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider)

        documentationConfigurer.uris().withScheme("https").withHost(host).withPort(443)

        return getMockMvcBuilder(*controllers).apply<StandaloneMockMvcBuilder>(documentationConfigurer).build()
    }

    fun document(
        snippetName: String,
        requestFieldDescription: Array<out FieldDescriptor>? = null,
        responseFieldDescription: Array<out FieldDescriptor>? = null,
    ): ResultHandler {

        if (requestFieldDescription == null && responseFieldDescription == null) {
            return MockMvcRestDocumentation.document(
                snippetName,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            )
        }

        if (requestFieldDescription == null) {
            return MockMvcRestDocumentation.document(
                snippetName,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                responseFields(*responseFieldDescription!!),
            )
        }

        if (responseFieldDescription == null) {
            return MockMvcRestDocumentation.document(
                snippetName,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                requestFields(*requestFieldDescription),
            )
        }

        return MockMvcRestDocumentation.document(
            snippetName,
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
            requestFields(*requestFieldDescription),
            responseFields(*responseFieldDescription),
        )
    }


    fun getMockMvc(vararg controllers: Any): MockMvc =
        getMockMvcBuilder(*controllers).build()

    private fun getMockMvcBuilder(vararg controllers: Any): StandaloneMockMvcBuilder {
        val conversionService = DefaultFormattingConversionService().apply {
            addConverter(LocalDateTimeConverter())
            addConverter(LocalDateConverter())
            addConverter(LocalTimeConverter())
        }

        return MockMvcBuilders.standaloneSetup(*controllers)
            .setControllerAdvice(
                GlobalRestControllerExceptionHandler()
            )
            .setConversionService(conversionService)
            .setMessageConverters(MappingJackson2HttpMessageConverter(JsonUtils.getMapper()))
            .addFilter(CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true));
    }

    class LocalDateTimeConverter : Converter<String, LocalDateTime> {
        override fun convert(source: String) = LocalDateTimeUtils.toLocalDateTime(source)
    }

    class LocalDateConverter : Converter<String, LocalDate> {
        override fun convert(source: String) = LocalDateUtils.toLocalDate(source)
    }

    class LocalTimeConverter : Converter<String, LocalTime> {
        override fun convert(source: String) = LocalTimeUtils.toLocalTime(source)
    }
}
