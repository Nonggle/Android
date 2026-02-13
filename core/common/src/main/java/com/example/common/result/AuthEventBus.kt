package com.example.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface AuthEvent {
    data object SessionExpired : AuthEvent
}

interface AuthEventBus {
    val events: Flow<AuthEvent>
    suspend fun emit(event: AuthEvent)
}

@Singleton
class DefaultAuthEventBus @Inject constructor() : AuthEventBus {
    private val _events = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 1)
    override val events = _events.asSharedFlow()

    override suspend fun emit(event: AuthEvent) {
        _events.emit(event)
    }
}