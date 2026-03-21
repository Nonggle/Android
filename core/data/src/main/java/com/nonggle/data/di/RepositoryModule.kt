package com.nonggle.data.di

import com.nonggle.data.repositoryimpl.ImageContentReaderRepositoryImpl
import com.nonggle.data.repositoryimpl.LoginRepositoryImpl
import com.nonggle.data.repositoryimpl.ResumeDraftStore
import com.nonggle.data.repositoryimpl.ResumeRepositoryImpl
import com.nonggle.data.util.ConnectivityManagerNetworkMonitor
import com.nonggle.data.util.NetworkMonitor
import com.nonggle.domain.repository.ImageContentReaderRepository
import com.nonggle.domain.repository.LoginRepository
import com.nonggle.domain.repository.ResumeDraftStoreInterface
import com.nonggle.domain.repository.ResumeRepository
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