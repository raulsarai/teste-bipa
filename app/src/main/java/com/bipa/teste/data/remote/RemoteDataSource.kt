package com.bipa.teste.data.remote

open class RemoteDataSource(private val api: ApiService?) {
    open suspend fun fetchTopNodes() = api?.getTopNodes()
}