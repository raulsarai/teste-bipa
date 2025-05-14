package com.bipa.teste.domain.repository

import com.bipa.teste.domain.model.Node

interface NodeRepository {
    suspend fun getTopNodes(): List<Node>
}