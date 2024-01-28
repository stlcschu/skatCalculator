package com.example.skatcalculator.util

import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundOutcome
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.SpecialRound
import com.example.skatcalculator.states.SkatRoundState
import kotlin.math.roundToInt

class ScoreCalculator {

    fun calculateDisplayScore(skatRoundState: SkatRoundState, isBockRound: Boolean = false) : Pair<Int,Int> {

        if (skatRoundState.roundVariant == RoundVariant.NULLSPIEL) {
            var base = 23
            if (skatRoundState.handChecked && skatRoundState.ouverChecked) {
                base = 59
            } else if (skatRoundState.handChecked) {
                base = 35
            } else if (skatRoundState.ouverChecked) {
                base = 46
            }
            if (skatRoundState.kontraChecked) {
                base *= Declaration.KONTRA.multiplier
            }
            if (skatRoundState.reChecked) {
                base *= Declaration.RE.multiplier
            }
            if (isBockRound) {
                base *= 2
            }
            val gain = base
            val loss = base * -2
            return Pair(gain, loss)
        }
        if (skatRoundState.roundVariant == RoundVariant.RAMSCH) {
            var base = skatRoundState.roundScore.roundToInt()
            if (skatRoundState.selectedShove > 0) base *= (skatRoundState.selectedShove * 2)
            if (skatRoundState.jungfrauChecked) {
                base *= RoundOutcome.DURCHMARSCH.modifier
            }
            if (isBockRound) {
                base *= 2
            }
            val loss = base * -2
            if (skatRoundState.successfulDurchmarschChecked) {
                base *= RoundOutcome.JUNGFRAU.modifier
            }
            val gain = base
            return Pair(gain, loss)
        }

        var base =
            if (skatRoundState.roundVariant == RoundVariant.GRAND) 24 * skatRoundState.selectedRoundType.value
            else skatRoundState.selectedTrick.baseValue * skatRoundState.selectedRoundType.value

        if (skatRoundState.handChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
        }
        if (skatRoundState.ouverChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
        }

        if (skatRoundState.kontraChecked) {
            base *= Declaration.KONTRA.multiplier
        }

        if (skatRoundState.reChecked) {
            base *= Declaration.RE.multiplier
        }

        if (skatRoundState.schneiderChecked) {
            base *= Declaration.SCHNEIDER.multiplier
        }

        if (skatRoundState.schwarzChecked) {
            base *= Declaration.SCHWARZ.multiplier
        }

        if (isBockRound) {
            base *= 2
        }

        val loss = base * -2
        val gain = base

        return Pair(gain, loss)
    }

    fun calculateFinalScoreWithSpecialRounds(skatRoundState: SkatRoundState, isBockRound: Boolean = false) : Pair<Int, List<SpecialRound>> {
        val specialRounds = mutableListOf<SpecialRound>()
        if (skatRoundState.roundVariant == RoundVariant.NULLSPIEL) {
            var base = 23

            if (skatRoundState.handChecked && skatRoundState.ouverChecked) {
                base = 59
            } else if (skatRoundState.handChecked) {
                base = 35
            } else if (skatRoundState.ouverChecked) {
                base = 46
            }
            if (skatRoundState.kontraChecked) {
                base *= Declaration.KONTRA.multiplier
            }
            if (skatRoundState.reChecked) {
                base *= Declaration.RE.multiplier
            }
            if (isBockRound) {
                base *= 2
            }
            if (skatRoundState.successfulNullSpielChecked) {
                val gain = base
                if (skatRoundState.kontraChecked || skatRoundState.reChecked) specialRounds.add(SpecialRound.BOCK)
                return Pair(gain, specialRounds)
            }
            val loss = base * -2
            if (skatRoundState.kontraChecked) specialRounds.addAll(arrayOf(SpecialRound.BOCK, SpecialRound.RAMSCH))
            return Pair(loss, specialRounds)
        }

        if  (skatRoundState.isSpaltarsch) {
            specialRounds.add(SpecialRound.BOCK)
        }

        if (skatRoundState.roundVariant == RoundVariant.RAMSCH) {
            var base = skatRoundState.roundScore.roundToInt()
            if (skatRoundState.selectedShove > 0) base *= (skatRoundState.selectedShove * 2)
            if (skatRoundState.jungfrauChecked) {
                base *= RoundOutcome.JUNGFRAU.modifier
            }
            if (isBockRound) {
                base *= 2
            }
            if (skatRoundState.successfulDurchmarschChecked) {
                base *= RoundOutcome.DURCHMARSCH.modifier
                val gain = base
                return Pair(gain, specialRounds)
            }
            val loss = base * -2
            return Pair(loss, specialRounds)
        }

        var base =
            if (skatRoundState.roundVariant == RoundVariant.GRAND) 24 * skatRoundState.selectedRoundType.value
            else skatRoundState.selectedTrick.baseValue * skatRoundState.selectedRoundType.value

        if (skatRoundState.handChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
            if (skatRoundState.roundVariant == RoundVariant.GRAND) specialRounds.add(SpecialRound.RAMSCH)
        }
        if (skatRoundState.ouverChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
        }

        if (skatRoundState.kontraChecked) {
            base *= com.example.skatcalculator.enums.Declaration.KONTRA.multiplier
        }

        if (skatRoundState.reChecked) {
            base *= com.example.skatcalculator.enums.Declaration.RE.multiplier
        }

        if (skatRoundState.schneiderChecked) {
            base *= com.example.skatcalculator.enums.Declaration.SCHNEIDER.multiplier
        }

        if (skatRoundState.schwarzChecked) {
            base *= com.example.skatcalculator.enums.Declaration.SCHWARZ.multiplier
        }

        if (isBockRound) {
            base *= 2
        }

        if (skatRoundState.roundScore.roundToInt() <= 60 ||
            (skatRoundState.schwarzChecked && !skatRoundState.successfulSchwarz) ||
            (skatRoundState.schneiderChecked && !skatRoundState.successfulSchneider)) {
            val loss = base * -2
            if (skatRoundState.kontraChecked) specialRounds.addAll(arrayOf(SpecialRound.BOCK, SpecialRound.RAMSCH))
            return Pair(loss, specialRounds)
        }
        val gain = base
        if (skatRoundState.kontraChecked || skatRoundState.reChecked) specialRounds.add(SpecialRound.BOCK)
        return Pair(gain, specialRounds)
    }

    fun calculatePotentialSingleScore(skatRoundState: SkatRoundState, isBockRound: Boolean = false) : Int {

        if (skatRoundState.roundVariant == RoundVariant.NULLSPIEL) {
            var base = 23
            if (skatRoundState.handChecked && skatRoundState.ouverChecked) {
                base = 59
            } else if (skatRoundState.handChecked) {
                base = 35
            } else if (skatRoundState.ouverChecked) {
                base = 46
            }
            if (skatRoundState.kontraChecked) {
                base *= Declaration.KONTRA.multiplier
            }
            if (skatRoundState.reChecked) {
                base *= Declaration.RE.multiplier
            }
            if (isBockRound) {
                base *= 2
            }
            if (skatRoundState.successfulNullSpielChecked) {
                return base
            }
            return base * -2
        }
        if (skatRoundState.roundVariant == RoundVariant.RAMSCH) {
            var base = skatRoundState.roundScore.roundToInt()
            if (skatRoundState.selectedShove > 0) base *= (skatRoundState.selectedShove * 2)
            if (skatRoundState.jungfrauChecked) {
                base *= RoundOutcome.JUNGFRAU.modifier
            }
            if (isBockRound) {
                base *= 2
            }
            if (skatRoundState.successfulDurchmarschChecked) {
                base *= RoundOutcome.DURCHMARSCH.modifier
                return base
            }
            return base * -2
        }

        var base =
            if (skatRoundState.roundVariant == RoundVariant.GRAND) 24 * skatRoundState.selectedRoundType.value
            else skatRoundState.selectedTrick.baseValue * skatRoundState.selectedRoundType.value

        if (skatRoundState.handChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
        }
        if (skatRoundState.ouverChecked) {
            base += if (skatRoundState.roundVariant == RoundVariant.GRAND) 24
            else skatRoundState.selectedTrick.baseValue
        }

        if (skatRoundState.kontraChecked) {
            base *= com.example.skatcalculator.enums.Declaration.KONTRA.multiplier
        }

        if (skatRoundState.reChecked) {
            base *= com.example.skatcalculator.enums.Declaration.RE.multiplier
        }

        if (skatRoundState.schneiderChecked) {
            base *= com.example.skatcalculator.enums.Declaration.SCHNEIDER.multiplier
        }

        if (skatRoundState.schwarzChecked) {
            base *= com.example.skatcalculator.enums.Declaration.SCHWARZ.multiplier
        }

        if (isBockRound) {
            base *= 2
        }

        if (skatRoundState.roundScore.roundToInt() <= 60 ||
            (skatRoundState.schwarzChecked && !skatRoundState.successfulSchwarz) ||
            (skatRoundState.schneiderChecked && !skatRoundState.successfulSchneider)) {
            return base * -2
        }
        return base
    }

}