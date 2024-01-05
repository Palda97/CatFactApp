package cz.dpalecek.catfact.core.domain.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

fun <T, R> ViewModel.mapStateFlow(
    stateFlow: StateFlow<T>,
    mapper: (value: T) -> R
): StateFlow<R> = stateFlow.map { mapper(it) }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(),
    mapper(stateFlow.value)
)

fun <S, T, R> ViewModel.combineStateFlow(
    stateFlow1: StateFlow<S>,
    stateFlow2: StateFlow<T>,
    mapper: (value1: S, value2: T) -> R
): StateFlow<R> = stateFlow1.combine(stateFlow2) { value1, value2 -> mapper(value1, value2) }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        mapper(stateFlow1.value, stateFlow2.value)
    )
