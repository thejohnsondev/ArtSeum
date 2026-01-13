package com.thejohnsondev.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    private val eventFlow = Channel<OneTimeEvent>()
    protected val screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.None)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            handleError(throwable)
        }
    }

    fun getEventFlow() = eventFlow.receiveAsFlow()

    protected suspend fun BaseViewModel.sendEvent(event: OneTimeEvent) {
        withContext(Dispatchers.Main) {
            eventFlow.send(event)
        }
    }

    protected suspend fun BaseViewModel.loading() {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.Loading)
        }
    }

    protected suspend fun BaseViewModel.showContent() {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.ShowContent)
        }
    }

    protected suspend fun showError(message: String) {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.Error(message))
        }
    }

    protected suspend fun handleError(throwable: Throwable) {
        withContext(Dispatchers.Main) {
            showContent()
            val errorMessage = throwable.message ?: "Unknown error"
            val displayableMessageValue = DisplayableMessageValue.StringValue(errorMessage)
            sendEvent(OneTimeEvent.ErrorMessage(displayableMessageValue))
        }
    }

    protected fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            block.invoke(viewModelScope)
        }
        return job
    }

    protected fun BaseViewModel.launchLoading(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            loading()
            block.invoke(viewModelScope)
        }
        return job
    }

}