package com.bipa.teste.data.remote

class RemoteDataSource(private val api: ApiService) {
    suspend fun fetchTopNodes() = api.getTopNodes()
}