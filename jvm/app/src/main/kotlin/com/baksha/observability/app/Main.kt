package com.baksha.observability.app

import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

fun main() {
    // Create a service with the default `Printer` monitor
    val service: UserService = UserServiceImpl()
        .monitored()

    // Simulate a series of operations
    repeat(5) { iteration ->
        println("\nIteration ${iteration + 1}")
        println("===========")

        // Test getUser
        runCatching {
            val userId = "user_${Random.nextInt(1000)}"
            println("Getting user $userId: ${service.getUser(userId)}")
        }.onFailure { println("Failed to get user: ${it.message}") }

        // Test validateCredentials
        service.validateCredentials("testuser", "testuser")
            .onSuccess { valid -> println("Credentials validation: $valid") }
            .onFailure { println("Validation failed: ${it.message}") }

        Thread.sleep(1.seconds.inWholeMilliseconds)
    }
}