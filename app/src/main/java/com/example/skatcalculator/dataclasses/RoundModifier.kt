package com.example.skatcalculator.dataclasses

import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundOutcome
data class RoundModifier(
    val declarations: List<Declaration>,
    val roundOutcomes: List<RoundOutcome>
)