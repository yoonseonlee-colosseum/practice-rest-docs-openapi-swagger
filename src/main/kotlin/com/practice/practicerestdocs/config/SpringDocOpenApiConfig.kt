package com.practice.practicerestdocs.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info = Info(
        title = "Swagger 를 통해 생성됨",
        version = "v2025.10.14",
        description = """
                        설명 작성
                    """,
        contact = Contact(
            url = "http://localhost:8080/",
            name = "name",
            email = "email"
        )
    )
)
@Configuration
class SpringDocOpenApiConfig {
}
