package com.example.skatcalculator.database.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.skatcalculator.database.daos.ScoreDao
import com.example.skatcalculator.database.daos.SkatGameDao
import com.example.skatcalculator.database.daos.SpecialRoundsDao
import com.example.skatcalculator.database.events.SkatGameEvent
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatGameWithRounds
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SkatGameWithScores
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.enums.SpecialRound
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SkatGameViewModel(
    private val skatGameDao: SkatGameDao,
    private val scoreDao: ScoreDao,
    private val specialRoundsDao: SpecialRoundsDao
) : ViewModel() {

    private val _gameId = MutableStateFlow("")

    private val _state = MutableStateFlow(SkatGameWithRoundsAndScores())

    private val _upcomingSpecialRounds = MutableStateFlow(SpecialRounds())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _state
        .flatMapLatest { _ -> skatGameDao.getSkatGame(_gameId.value) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SkatGameWithRoundsAndScores())

    @OptIn(ExperimentalCoroutinesApi::class)
    val upcomingSpecialRounds = _upcomingSpecialRounds
        .flatMapLatest { _ -> specialRoundsDao.getSpecialRounds(_gameId.value) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SpecialRounds())

    private val _historyGames = MutableStateFlow(emptyList<SkatGameWithScores>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val historyGames = _historyGames
        .flatMapLatest { _ -> skatGameDao.getRecentSkatGames() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<SkatGameWithScores>())

    fun onEvent(event: SkatGameEvent) {
        when(event) {

            is SkatGameEvent.setSkatGameId -> {
                _gameId.value = event.skatGameId
            }

            is SkatGameEvent.deleteSkatGame -> {
                viewModelScope.launch {
                    skatGameDao.deleteSkatGame(event.skatGame)
                }
            }

            is SkatGameEvent.saveSkatGame -> {
                viewModelScope.launch {
                    skatGameDao.upsertSkatGame(event.skatGame)
                }
            }
            is SkatGameEvent.saveScore -> {
                viewModelScope.launch {
                    scoreDao.upsertScore(event.score)
                }
            }

            is SkatGameEvent.saveSpecialRounds -> {
                viewModelScope.launch {
                    specialRoundsDao.upsertSpecialRounds(event.specialRounds)
                }
            }

        }
    }
}

class SkatGameViewModelFactory(private val skatGameDao: SkatGameDao, private val scoreDao: ScoreDao, private val specialRoundsDao: SpecialRoundsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkatGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SkatGameViewModel(skatGameDao, scoreDao, specialRoundsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}