package com.bipa.teste.data.model

data class NodeDto(
    val publicKey: String,
    val alias: String?,
    val channels: Int,
    val capacity: Long,
    val firstSeen: Long,
    val updatedAt: Long,
    val city: String?,
    val country: Map<String, String>?
)