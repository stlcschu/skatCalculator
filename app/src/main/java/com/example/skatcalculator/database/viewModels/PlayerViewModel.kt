package com.example.skatcalculator.database.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.skatcalculator.database.daos.PlayerDao
import com.example.skatcalculator.database.events.PlayerEvent
import com.example.skatcalculator.database.tables.Player
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerDao: PlayerDao
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<Player>())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _state
        .flatMapLatest { _ -> playerDao.getAllPlayers() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEvent(event: PlayerEvent) {
        when(event) {
            is PlayerEvent.deletePlayer -> {
                viewModelScope.launch {
                    playerDao.deletePlayer(event.player)
                }
            }
            is PlayerEvent.savePlayer -> {
                viewModelScope.launch {
                    playerDao.upsertPlayer(event.player)
                }
            }
        }
    }
}

class PlayerViewModelFactory(private val playerDao: PlayerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(playerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}