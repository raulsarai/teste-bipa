package com.bipa.teste.data.repository

import com.bipa.teste.data.remote.RemoteDataSource
import com.bipa.teste.domain.model.Node
import com.bipa.teste.domain.repository.NodeRepository
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.collections.map

class NodeRepositoryImpl(
    private val remote: RemoteDataSource
) : NodeRepository {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        .withZone(ZoneId.systemDefault())

    override suspend fun getTopNodes(): List<Node> = remote.fetchTopNodes().map {
        Node(
            publicKey = it.publicKey,
            alias = it.alias ?: "(Sem nome)",
            channels = it.channels,
            capacity = it.capacity,
            firstSeen = dateFormatter.format(Instant.ofEpochSecond(it.firstSeen)),
            updatedAt = dateFormatter.format(Instant.ofEpochSecond(it.updatedAt)),
            country = it.country?.get("pt-BR")
                ?: it.country?.get("en")
                ?: "Desconhecido",
        )
    }
}

