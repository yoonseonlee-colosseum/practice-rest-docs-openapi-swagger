package com.practice.practicerestdocs.config

import java.time.ZoneId

object Constant {
    const val PROFILE_TEST: String = "test"
    const val PROFILE_LOCAL: String = "local"
    const val PROFILE_DEV: String = "dev"
    const val PROFILE_STAGE: String = "stg"
    const val PROFILE_PERFORMANCE: String = "perf"
    const val PROFILE_PRODUCTION: String = "prod"

    const val HOST_LOCAL: String = "12.0.0.1"
    const val HOST_DEV: String = "" // 개발DNS 는 배포에 따라 맞춰 진행

    const val FORMAT_LOCAL_DATE_TIME: String = "yyyy-MM-dd'T'HH:mm:ss"
    const val FORMAT_LOCAL_DATE: String = "yyyy-MM-dd"
    const val FORMAT_LOCAL_TIME: String = "HH:mm:ss"

    val DEFAULT_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
}
