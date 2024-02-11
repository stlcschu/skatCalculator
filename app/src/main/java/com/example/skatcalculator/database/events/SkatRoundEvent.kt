package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor

sealed interface SkatRoundEvent {

    data class AddRound(val skatRound: SkatRound) : SkatRoundEvent
    data class DeleteRound(val skatRound: SkatRound) : SkatRoundEvent

    data class OnRoundVariantChanged(val roundVariant: RoundVariant) : SkatRoundEvent
    data class OnSelectedPlayerChanged(val selectedPlayer: PlayerWithScore) : SkatRoundEvent
    data class OnSelectedShoveChanged(val selectedShove: Int) : SkatRoundEvent
    data class OnSelectedRoundTypeChanged(val selectedRoundType: RoundType) : SkatRoundEvent
    data class OnSelectedTrickChanged(val selectedTrick: TrickColor) : SkatRoundEvent
    data class OnReCheckedChanged(val reChecked: Boolean) : SkatRoundEvent
    data class OnKontraCheckedChanged(val kontraChecked: Boolean) : SkatRoundEvent
    data class OnSchneiderCheckedChanged(val schneiderChecked: Boolean) : SkatRoundEvent
    data class OnSchwarzCheckedChanged(val schwarzChecked: Boolean) : SkatRoundEvent
    data class OnOuvertCheckedChanged(val ouvertChecked: Boolean) : SkatRoundEvent
    data class OnHandCheckedChanged(val handChecked: Boolean) : SkatRoundEvent
    data class OnJungfrauCheckedChanged(val jungfrauChecked: Boolean) : SkatRoundEvent
    data class OnSuccessfulDurchmarschCheckedChanged(val successfulDurchmarschChecked: Boolean) : SkatRoundEvent
    data class OnRoundTypeDropDownExpandedChanged(val roundTypeDropDownExpanded: Boolean) : SkatRoundEvent
    data class OnRoundScoreValueChanged(val roundScore: Float) : SkatRoundEvent
    data class OnNullSpielCheckedChanged(val successfulNullSpielChecked: Boolean) : SkatRoundEvent
    data class OnIsSpaltarschChanged(val isSpaltarsch: Boolean): SkatRoundEvent
    data class OnSuccessfulSchwarzCheckedChanged(val successfulSchwarzChecked: Boolean) : SkatRoundEvent
    data class OnSuccessfulSchneiderChanged(val successfulSchneider: Boolean) : SkatRoundEvent
    data class OnSelectedPlayerIndexChanged(val selectedPlayerIndex: Int) : SkatRoundEvent

    data class OnFullReset(val defaultPlayer: PlayerWithScore) : SkatRoundEvent
    data object OnResetNormalVariant : SkatRoundEvent
    data object OnResetGrandVariant : SkatRoundEvent
    data object OnResetNullSpielVariant : SkatRoundEvent
    data object OnResetRamschVariant : SkatRoundEvent

    data class OnUpdateRoundInformationState(val round: SkatRound, val player: Player) : SkatRoundEvent
}