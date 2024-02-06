package com.example.skatcalculator.database.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.skatcalculator.database.daos.SkatRoundDao
import com.example.skatcalculator.database.events.SkatRoundEvent
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor
import com.example.skatcalculator.states.SkatRoundInformationState
import com.example.skatcalculator.states.SkatRoundState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SkatRoundViewModel(
    private val skatRoundDao: SkatRoundDao
) : ViewModel() {

    private val _state = MutableStateFlow(SkatRoundState())
    val state = _state

    private val _roundInformationState = MutableStateFlow(SkatRoundInformationState())
    val roundInformationState = _roundInformationState

    fun onEvent(event: SkatRoundEvent) {
        when(event) {
            is SkatRoundEvent.addRound -> {
                viewModelScope.launch {
                    skatRoundDao.upsertRound(event.skatRound)
                }
                _state.update {
                    it.copy(
                        roundVariant = RoundVariant.NORMAL,
                        selectedPlayer = PlayerWithScore(),
                        selectedShove = 0,
                        selectedRoundType = RoundType.WITH_WITHOUT_ONE,
                        selectedTrick = TrickColor.CROSSES,
                        reChecked = false,
                        kontraChecked = false,
                        schneiderChecked = false,
                        schwarzChecked = false,
                        ouvertChecked = false,
                        handChecked = false,
                        jungfrauChecked = false,
                        roundScore = 0f,
                        successfulNullSpielChecked = false,
                        successfulDurchmarschChecked = false,
                        shoveDropdownExpanded = false,
                        roundTypeDropDownExpanded = false,
                        isSpaltarsch = false,
                        successfulSchwarz = false,
                        successfulSchneider = false
                    )
                }
            }
            is SkatRoundEvent.deleteRound -> {
                viewModelScope.launch {
                    skatRoundDao.deleteRound(event.skatRound)
                }
            }
            is SkatRoundEvent.onHandCheckedChanged -> {
                _state.update {
                    it.copy(
                        handChecked = event.handChecked
                    )
                }
            }
            is SkatRoundEvent.onJungfrauCheckedChanged -> {
                _state.update {
                    it.copy(
                        jungfrauChecked = event.jungfrauChecked
                    )
                }
            }
            is SkatRoundEvent.onKontraCheckedChanged -> {
                _state.update {
                    it.copy(
                        kontraChecked = event.kontraChecked
                    )
                }
            }
            is SkatRoundEvent.onOuvertCheckedChanged -> {
                _state.update {
                    it.copy(
                        ouvertChecked = event.ouvertChecked
                    )
                }
            }
            is SkatRoundEvent.onReCheckedChanged -> {
                _state.update {
                    it.copy(
                        reChecked = event.reChecked
                    )
                }
            }
            is SkatRoundEvent.onRoundTypeDropDownExpandedChanged -> {
                _state.update {
                    it.copy(
                        roundTypeDropDownExpanded = event.roundTypeDropDownExpanded
                    )
                }
            }
            is SkatRoundEvent.onRoundVariantChanged -> {
                _state.update {
                    it.copy(
                        roundVariant = event.roundVariant
                    )
                }
            }
            is SkatRoundEvent.onSchneiderCheckedChanged -> {
                _state.update {
                    it.copy(
                        schneiderChecked = event.schneiderChecked
                    )
                }
            }
            is SkatRoundEvent.onSchwarzCheckedChanged -> {
                _state.update {
                    it.copy(
                        schwarzChecked = event.schwarzChecked
                    )
                }
            }
            is SkatRoundEvent.onSelectedPlayerChanged -> {
                _state.update {
                    it.copy(
                        selectedPlayer = event.selectedPlayer,
                    )
                }
            }
            is SkatRoundEvent.onSelectedRoundTypeChanged -> {
                _state.update {
                    it.copy(
                        selectedRoundType = event.selectedRoundType
                    )
                }
            }
            is SkatRoundEvent.onSelectedShoveChanged -> {
                _state.update {
                    it.copy(
                        selectedShove = event.selectedShove
                    )
                }
            }
            is SkatRoundEvent.onSelectedTrickChanged -> {
                _state.update {
                    it.copy(
                        selectedTrick = event.selectedTrick
                    )
                }
            }
            is SkatRoundEvent.onShoveDropdownExpandedChanged -> {
                _state.update {
                    it.copy(
                        shoveDropdownExpanded = event.shoveDropdownExpanded
                    )
                }
            }
            is SkatRoundEvent.onSuccessfulDurchmarschCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulDurchmarschChecked = event.successfulDurchmarschChecked
                    )
                }
            }
            is SkatRoundEvent.onNullSpielCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulNullSpielChecked = event.successfulNullSpielChecked
                    )
                }
            }
            is SkatRoundEvent.onRoundScoreValueChanged -> {
                _state.update {
                    it.copy(
                        roundScore = event.roundScore
                    )
                }
            }
            is SkatRoundEvent.onIsSpaltarschChanged -> {
                _state.update {
                    it.copy(
                        isSpaltarsch = event.isSpaltarsch
                    )
                }
            }
            is SkatRoundEvent.onSuccessfulSchwarzCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulSchwarz = event.successfulSchwarzChecked
                    )
                }
            }
            is SkatRoundEvent.onSuccessfulSchneiderChanged -> {
                _state.update {
                    it.copy(
                        successfulSchneider = event.successfulSchneider
                    )
                }
            }
            is SkatRoundEvent.onUpdateRoundInformationState -> {
                _roundInformationState.update {
                    it.copy(
                        round = event.round,
                        player = event.player
                    )
                }
            }
            is SkatRoundEvent.onSelectedPlayerIndexChanged -> {
                _state.update {
                    it.copy(
                        selectedPlayerIndex = event.selectedPlayerIndex
                    )
                }
            }

            SkatRoundEvent.onFullReset -> {
                _state.update {
                    it.copy(
                        roundVariant = RoundVariant.NORMAL,
                        selectedPlayer = PlayerWithScore(),
                        selectedShove = 0,
                        selectedRoundType = RoundType.WITH_WITHOUT_ONE,
                        selectedTrick = TrickColor.CROSSES,
                        reChecked = false,
                        kontraChecked = false,
                        schneiderChecked = false,
                        schwarzChecked = false,
                        ouvertChecked = false,
                        handChecked = false,
                        jungfrauChecked = false,
                        roundScore = 0f,
                        successfulNullSpielChecked = false,
                        successfulDurchmarschChecked = false,
                        shoveDropdownExpanded = false,
                        roundTypeDropDownExpanded = false,
                        isSpaltarsch = false,
                        successfulSchwarz = false,
                        successfulSchneider = false
                    )
                }
            }

            SkatRoundEvent.onResetGrandVariant -> {
                _state.update {
                    it.copy(
                        selectedPlayer = PlayerWithScore(),
                        selectedRoundType = RoundType.WITH_WITHOUT_ONE,
                        reChecked = false,
                        kontraChecked = false,
                        schneiderChecked = false,
                        schwarzChecked = false,
                        ouvertChecked = false,
                        handChecked = false,
                        roundScore = 0f,
                        roundTypeDropDownExpanded = false,
                        isSpaltarsch = false,
                        successfulSchwarz = false,
                        successfulSchneider = false
                    )
                }
            }

            SkatRoundEvent.onResetNormalVariant -> {
                _state.update {
                    it.copy(
                        selectedPlayer = PlayerWithScore(),
                        selectedRoundType = RoundType.WITH_WITHOUT_ONE,
                        selectedTrick = TrickColor.CROSSES,
                        reChecked = false,
                        kontraChecked = false,
                        schneiderChecked = false,
                        schwarzChecked = false,
                        ouvertChecked = false,
                        handChecked = false,
                        roundScore = 0f,
                        roundTypeDropDownExpanded = false,
                        isSpaltarsch = false,
                        successfulSchwarz = false,
                        successfulSchneider = false
                    )
                }
            }

            SkatRoundEvent.onResetNullSpielVariant -> {
                _state.update {
                    it.copy(
                        selectedPlayer = PlayerWithScore(),
                        reChecked = false,
                        kontraChecked = false,
                        ouvertChecked = false,
                        handChecked = false,
                        successfulNullSpielChecked = false,
                    )
                }
            }

            SkatRoundEvent.onResetRamschVariant -> {
                _state.update {
                    it.copy(
                        selectedPlayer = PlayerWithScore(),
                        selectedShove = 0,
                        shoveDropdownExpanded = false,
                        jungfrauChecked = false,
                        roundScore = 0f,
                        successfulDurchmarschChecked = false

                    )
                }
            }
        }
    }

}


class SkatRoundViewModelFactory(private val skatRoundDao: SkatRoundDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkatRoundViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SkatRoundViewModel(skatRoundDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}