package com.bipa.teste.data.remote

import com.bipa.teste.data.model.NodeDto

interface ApiService {
    @GET("/api/v1/lightning/nodes/rankings/connectivity")
    suspend fun getTopNodes(): List<NodeDto>
}