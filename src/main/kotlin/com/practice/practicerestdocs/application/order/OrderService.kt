package com.practice.practicerestdocs.application.order

import com.practice.practicerestdocs.domain.order.OrderInfo
import com.practice.practicerestdocs.infrastructure.web.order.OrderCreateCommand
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService {

    fun create(createCommand: OrderCreateCommand): OrderInfo {
        return OrderInfo(
            id = 1L,
            memberNo = createCommand.memberNo,
            orderNo = "ORDER-1",
            createdAt = LocalDateTime.now()
        )
    }
}
