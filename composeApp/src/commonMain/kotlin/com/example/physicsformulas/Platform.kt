package com.example.physicsformulas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform