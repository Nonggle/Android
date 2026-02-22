package com.example.core.data.di

import com.example.core.data.util.ConnectivityManagerNetworkMonitor
import com.example.core.data.util.NetworkMonitor
import com.example.data.repositoryimpl.ImageContentReaderRepositoryImpl
import com.example.data.repositoryimpl.LoginRepositoryImpl
import com.example.data.repositoryimpl.ResumeDraftStore
import com.example.data.repositoryimpl.ResumeRepositoryImpl
import com.example.domain.repository.ImageContentReaderRepository
import com.example.domain.repository.LoginRepository
import com.example.domain.repository.ResumeDraftStoreInterface
import com.example.domain.repository.ResumeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    @Singleton
    internal abstract fun bindsLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    @Singleton
    internal abstract fun bindsResumeRepository(
        resumeRepositoryImpl: ResumeRepositoryImpl,
    ): ResumeRepository

    @Binds
    @Singleton
    internal abstract fun bindResumeDraftStore(
        draftStore: ResumeDraftStore
    ): ResumeDraftStoreInterface

    @Binds
    @Singleton
    internal abstract fun bindImageContentReaderRepository(
        repositoryImpl: ImageContentReaderRepositoryImpl
    ): ImageContentReaderRepository

}