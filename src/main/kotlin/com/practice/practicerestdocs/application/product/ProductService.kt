package com.practice.practicerestdocs.application.product

import com.practice.practicerestdocs.domain.product.Product
import com.practice.practicerestdocs.domain.product.ProductInfo
import com.practice.practicerestdocs.infrastructure.web.product.ProductCreateCommand
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ProductService {

    private val productIdCreator = AtomicLong(0)
    private val products: ArrayList<Product> = ArrayList()

    fun create(command: ProductCreateCommand): ProductInfo {
        val product = command.toProduct(
            id = productIdCreator.incrementAndGet()
        )

        products.add(product)

        return ProductInfo.from(product)
    }
}
