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
            is SkatRoundEvent.AddRound -> {
                viewModelScope.launch {
                    skatRoundDao.upsertRound(event.skatRound)
                }
            }
            is SkatRoundEvent.DeleteRound -> {
                viewModelScope.launch {
                    skatRoundDao.deleteRound(event.skatRound)
                }
            }
            is SkatRoundEvent.OnHandCheckedChanged -> {
                _state.update {
                    it.copy(
                        handChecked = event.handChecked
                    )
                }
            }
            is SkatRoundEvent.OnJungfrauCheckedChanged -> {
                _state.update {
                    it.copy(
                        jungfrauChecked = event.jungfrauChecked
                    )
                }
            }
            is SkatRoundEvent.OnKontraCheckedChanged -> {
                _state.update {
                    it.copy(
                        kontraChecked = event.kontraChecked
                    )
                }
            }
            is SkatRoundEvent.OnOuvertCheckedChanged -> {
                _state.update {
                    it.copy(
                        ouvertChecked = event.ouvertChecked
                    )
                }
            }
            is SkatRoundEvent.OnReCheckedChanged -> {
                _state.update {
                    it.copy(
                        reChecked = event.reChecked
                    )
                }
            }
            is SkatRoundEvent.OnRoundTypeDropDownExpandedChanged -> {
                _state.update {
                    it.copy(
                        roundTypeDropDownExpanded = event.roundTypeDropDownExpanded
                    )
                }
            }
            is SkatRoundEvent.OnRoundVariantChanged -> {
                _state.update {
                    it.copy(
                        roundVariant = event.roundVariant
                    )
                }
            }
            is SkatRoundEvent.OnSchneiderCheckedChanged -> {
                _state.update {
                    it.copy(
                        schneiderChecked = event.schneiderChecked
                    )
                }
            }
            is SkatRoundEvent.OnSchwarzCheckedChanged -> {
                _state.update {
                    it.copy(
                        schwarzChecked = event.schwarzChecked
                    )
                }
            }
            is SkatRoundEvent.OnSelectedPlayerChanged -> {
                _state.update {
                    it.copy(
                        selectedPlayer = event.selectedPlayer,
                    )
                }
            }
            is SkatRoundEvent.OnSelectedRoundTypeChanged -> {
                _state.update {
                    it.copy(
                        selectedRoundType = event.selectedRoundType
                    )
                }
            }
            is SkatRoundEvent.OnSelectedShoveChanged -> {
                _state.update {
                    it.copy(
                        selectedShove = event.selectedShove
                    )
                }
            }
            is SkatRoundEvent.OnSelectedTrickChanged -> {
                _state.update {
                    it.copy(
                        selectedTrick = event.selectedTrick
                    )
                }
            }
            is SkatRoundEvent.OnSuccessfulDurchmarschCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulDurchmarschChecked = event.successfulDurchmarschChecked
                    )
                }
            }
            is SkatRoundEvent.OnNullSpielCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulNullSpielChecked = event.successfulNullSpielChecked
                    )
                }
            }
            is SkatRoundEvent.OnRoundScoreValueChanged -> {
                _state.update {
                    it.copy(
                        roundScore = event.roundScore
                    )
                }
            }
            is SkatRoundEvent.OnIsSpaltarschChanged -> {
                _state.update {
                    it.copy(
                        isSpaltarsch = event.isSpaltarsch
                    )
                }
            }
            is SkatRoundEvent.OnSuccessfulSchwarzCheckedChanged -> {
                _state.update {
                    it.copy(
                        successfulSchwarz = event.successfulSchwarzChecked
                    )
                }
            }
            is SkatRoundEvent.OnSuccessfulSchneiderChanged -> {
                _state.update {
                    it.copy(
                        successfulSchneider = event.successfulSchneider
                    )
                }
            }
            is SkatRoundEvent.OnUpdateRoundInformationState -> {
                _roundInformationState.update {
                    it.copy(
                        round = event.round,
                        player = event.player
                    )
                }
            }
            is SkatRoundEvent.OnSelectedPlayerIndexChanged -> {
                _state.update {
                    it.copy(
                        selectedPlayerIndex = event.selectedPlayerIndex
                    )
                }
            }

            is SkatRoundEvent.OnFullReset -> {
                _state.update {
                    it.copy(
                        roundVariant = RoundVariant.NORMAL,
                        selectedPlayer = PlayerWithScore(),
                        selectedPlayerIndex = 1,
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

            SkatRoundEvent.OnResetGrandVariant -> {
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

            SkatRoundEvent.OnResetNormalVariant -> {
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

            SkatRoundEvent.OnResetNullSpielVariant -> {
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

            SkatRoundEvent.OnResetRamschVariant -> {
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