package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor

sealed interface SkatRoundEvent {

    data class addRound(val skatRound: SkatRound) : SkatRoundEvent
    data class deleteRound(val skatRound: SkatRound) : SkatRoundEvent

    data class onRoundVariantChanged(val roundVariant: RoundVariant) : SkatRoundEvent
    data class onSelectedPlayerChanged(val selectedPlayer: PlayerWithScore) : SkatRoundEvent
    data class onSelectedShoveChanged(val selectedShove: Int) : SkatRoundEvent
    data class onSelectedRoundTypeChanged(val selectedRoundType: RoundType) : SkatRoundEvent
    data class onSelectedTrickChanged(val selectedTrick: TrickColor) : SkatRoundEvent
    data class onReCheckedChanged(val reChecked: Boolean) : SkatRoundEvent
    data class onKontraCheckedChanged(val kontraChecked: Boolean) : SkatRoundEvent
    data class onSchneiderCheckedChanged(val schneiderChecked: Boolean) : SkatRoundEvent
    data class onSchwarzCheckedChanged(val schwarzChecked: Boolean) : SkatRoundEvent
    data class onOuvertCheckedChanged(val ouvertChecked: Boolean) : SkatRoundEvent
    data class onHandCheckedChanged(val handChecked: Boolean) : SkatRoundEvent
    data class onJungfrauCheckedChanged(val jungfrauChecked: Boolean) : SkatRoundEvent
    data class onSuccessfulDurchmarschCheckedChanged(val successfulDurchmarschChecked: Boolean) : SkatRoundEvent
    data class onRoundTypeDropDownExpandedChanged(val roundTypeDropDownExpanded: Boolean) : SkatRoundEvent
    data class onRoundScoreValueChanged(val roundScore: Float) : SkatRoundEvent
    data class onNullSpielCheckedChanged(val successfulNullSpielChecked: Boolean) : SkatRoundEvent
    data class onIsSpaltarschChanged(val isSpaltarsch: Boolean): SkatRoundEvent
    data class onSuccessfulSchwarzCheckedChanged(val successfulSchwarzChecked: Boolean) : SkatRoundEvent
    data class onSuccessfulSchneiderChanged(val successfulSchneider: Boolean) : SkatRoundEvent
    data class onSelectedPlayerIndexChanged(val selectedPlayerIndex: Int) : SkatRoundEvent

    data class onFullReset(val defaultPlayer: PlayerWithScore) : SkatRoundEvent
    object onResetNormalVariant : SkatRoundEvent
    object onResetGrandVariant : SkatRoundEvent
    object onResetNullSpielVariant : SkatRoundEvent
    object onResetRamschVariant : SkatRoundEvent

    data class onUpdateRoundInformationState(val round: SkatRound, val player: Player) : SkatRoundEvent
}