package com.bipa.teste.data.remote

import com.bipa.teste.data.model.NodeDto
import retrofit2.http.GET

interface ApiService {
    @GET("/api/v1/lightning/nodes/rankings/connectivity")
    suspend fun getTopNodes(): List<NodeDto>
}