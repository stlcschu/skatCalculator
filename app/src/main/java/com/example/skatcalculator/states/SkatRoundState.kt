package com.example.skatcalculator.states

import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor

data class SkatRoundState (
    val roundVariant: RoundVariant = RoundVariant.NORMAL,
    val selectedPlayer: PlayerWithScore = PlayerWithScore(),
    val selectedShove: Int = 0,
    val selectedRoundType: RoundType = RoundType.WITH_WITHOUT_ONE,
    val selectedTrick: TrickColor = TrickColor.CROSSES,
    val reChecked: Boolean = false,
    val kontraChecked: Boolean = false,
    val schneiderChecked: Boolean = false,
    val schwarzChecked: Boolean = false,
    val ouvertChecked: Boolean = false,
    val handChecked: Boolean = false,
    val jungfrauChecked: Boolean = false,
    val roundScore: Float = 0f,
    val successfulNullSpielChecked: Boolean = false,
    val successfulDurchmarschChecked: Boolean = false,
    val playerDropdownExpanded: Boolean = false,
    val shoveDropdownExpanded: Boolean = false,
    val roundTypeDropDownExpanded: Boolean = false,
    val isSpaltarsch: Boolean = false,
    val successfulSchwarz: Boolean = false,
    val successfulSchneider: Boolean = false,
)