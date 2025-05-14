package com.bipa.teste.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bipa.teste.data.remote.ApiService
import com.bipa.teste.data.remote.RemoteDataSource
import com.bipa.teste.data.repository.NodeRepositoryImpl
import com.bipa.teste.domain.repository.NodeRepository
import com.bipa.teste.domain.usecase.GetTopNodesUseCase

val appModule = module {
    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://mempool.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { RemoteDataSource(get()) }
    single<NodeRepository> { NodeRepositoryImpl(get()) }
    single { GetTopNodesUseCase(get()) }

}