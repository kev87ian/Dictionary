package com.kev.dictionary.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.dictionary.feature_dictionary.core.util.Resource
import com.kev.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val useCase: GetWordInfoUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf<String>("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

  /*  private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()*/

    private var searchJob: Job? = null
    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            useCase(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true,
                                wordInfoItems = emptyList()
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                isLoading = false,
                                wordInfoItems = result.data ?: emptyList(),
                                errorMessage = result.errorMessage
                            )
                           /* _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.errorMessage
                                )
                            )*/
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data,
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    // to help emit a snackbar
    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}
