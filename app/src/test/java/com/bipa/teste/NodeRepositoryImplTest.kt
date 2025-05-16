package com.bipa.teste

import com.bipa.teste.data.model.NodeDto
import org.junit.Test

import com.bipa.teste.data.remote.RemoteDataSource
import com.bipa.teste.data.repository.NodeRepositoryImpl
import com.bipa.teste.domain.model.Node
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class NodeRepositoryImplTest {
    private val epochFirst = 0L
    private val epochUpdated = 3600L


    private class FakeRemoteDataSource(private val dtos: List<NodeDto>) : RemoteDataSource(
        api = null
    ) {
        override suspend fun fetchTopNodes(): List<NodeDto> = dtos
    }

    @Test
    fun `maps fields correctly from dto to domain Node`() = runBlocking {

        val dto = NodeDto(
            publicKey = "pk",
            alias     = null,
            channels  = 10,
            capacity  = 500_000_000L,
            firstSeen = epochFirst,
            updatedAt = epochUpdated,
            city      = null,
            country   = mapOf("pt-BR" to "Brasil", "en" to "Brazil"),
        )
        val repository = NodeRepositoryImpl(FakeRemoteDataSource(listOf(dto)))
        val result: List<Node> = repository.getTopNodes()


        assertEquals(1, result.size)
        val node = result[0]
        assertEquals("pk", node.publicKey)
        assertEquals("(Sem nome)", node.alias)
        assertEquals(10, node.channels)
        assertEquals(500_000_000L, node.capacity)
        assertEquals("31/12/1969 21:00", node.firstSeen)
        assertEquals("31/12/1969 22:00", node.updatedAt)
        assertEquals("Brasil", node.country)
    }

    @Test
    fun `falls back to en if pt-BR not present`() = runBlocking {
        val dto = NodeDto(
            publicKey = "pk2",
            alias = "Test",
            channels = 5,
            capacity = 100L,
            firstSeen = 0L,
            updatedAt = 0L,
            city = null,
            country = mapOf("en" to "England"),
        )
        val repository = NodeRepositoryImpl(FakeRemoteDataSource(listOf(dto)))
        val node = repository.getTopNodes()[0]
        assertEquals("England", node.country)
    }

    @Test
    fun `unknown country when map is empty or null`() = runBlocking {
        val dto1 = NodeDto(
            publicKey = "pk3", alias = "X", channels = 1,
            capacity = 1L, firstSeen = 0L, updatedAt = 0L,
            city = null, country = emptyMap()
        )
        val dto2 = dto1.copy(country = null)
        val repository = NodeRepositoryImpl(FakeRemoteDataSource(listOf(dto1, dto2)))

        val nodes = repository.getTopNodes()
        assertEquals("Desconhecido", nodes[0].country)
        assertEquals("Desconhecido", nodes[1].country)
    }
}
