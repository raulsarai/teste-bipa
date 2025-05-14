package com.bipa.teste.domain.model

data class Node(
    val publicKey: String,
    val alias: String,
    val channels: Int,
    val capacity: String,
    val firstSeen: String,
    val updatedAt: String,
    val country: String
)