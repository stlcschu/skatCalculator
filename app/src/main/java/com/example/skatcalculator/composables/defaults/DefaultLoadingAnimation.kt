package com.example.skatcalculator.composables.defaults

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.animation.PathInterpolator
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R

@Composable
fun DefaultLoadingAnimation(
    cardIcons: List<Int>,
) {
    if (cardIcons.size < 8) return

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        for ((cardIndex, cardIcon) in cardIcons.withIndex()) {
            LoadingCard(cardIcon = cardIcon, cardIndex = cardIndex)
        }

    }
    
}

@Composable
fun LoadingCard(
    cardIcon: Int,
    cardIndex: Int
) {

    val keyFrames = getKeyFrames(cardIndex)
    val rotationFrames = getRotation(cardIndex)

    val offsetX = rememberInfiniteTransition("padding start")
    val offsetXAnimation by offsetX.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyFrames.first
        ), label = ""
    )


    val paddingY = rememberInfiniteTransition("padding top")
    val paddingYAnimation by paddingY.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyFrames.second
        ), label = ""
    )

    val rotation = rememberInfiniteTransition(label = "rotation")
    val rotationAnimation by rotation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = rotationFrames,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .offset(
                x = offsetXAnimation.dp,
                y = paddingYAnimation.dp
            )
    ) {
        Card(
            modifier = Modifier
                .width(62.5.dp)
                .rotate(rotationAnimation),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = cardIcon),
                contentDescription = "Loading icon",
                modifier = Modifier
                    .size(100.dp),
                tint = Color.Unspecified
            )
        }
    }

}

private fun getRotation(cardIndex: Int) : KeyframesSpec<Float> {

    return when(cardIndex) {
        0 -> {
            keyframes {
                durationMillis = 2000

                360f at 1000

            }
        }
        1 ->  {
            keyframes {
                durationMillis = 2000

                315f at 862
                315f at 1875
            }
        }
        2 -> {
            keyframes {
                durationMillis = 2000

                270f at 737
                270f at 1750
            }
        }
        3 -> {
            keyframes {
                durationMillis = 2000

                225f at 612
                225f at 1625
            }
        }
        4 -> {
            keyframes {
                durationMillis = 2000

                180f at 487
                180f at 1500
            }
        }
        5 -> {
            keyframes {
                durationMillis = 2000

                135f at 362
                135f at 1375
            }
        }
        6 -> {
            keyframes {
                durationMillis = 2000

                90f at 237
                90f at 1250
            }
        }
        7 -> {
            keyframes {
                durationMillis = 2000

                45f at 112
                45f at 1125
            }
        }
        8 -> {
            keyframes {
                durationMillis = 2000

                0f at 1000

            }
        }
        else -> throw Exception("CardIndex out of bounds")
    }

}

private fun getKeyFrames(cardIndex: Int) : Pair<KeyframesSpec<Float>, KeyframesSpec<Float>> {
    return when(cardIndex) {
        0 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 375

                83f at 389
                73f at 403
                63f at 416
                52f at 430
                42f at 444
                31f at 458
                21f at 472
                11f at 486

                0f at 500

                -11f at 514
                -21f at 528
                -31f at 541
                -42f at 555
                -52f at 569
                -63f at 583
                -73f at 597
                -83f at 611

                -88f at 625

                -93f at 639
                -101f at 653
                -108f at 666
                -113f at 680
                -117f at 694
                -121f at 708
                -123f at 722
                -124f at 736

                -125f at 750

                -124f at 764
                -123f at 778
                -121f at 791
                -117f at 805
                -113f at 819
                -108f at 833
                -101f at 847
                -93f at 861

                -88f at 875

                -83f at 889
                -73f at 903
                -63f at 916
                -52f at 930
                -42f at 944
                -31f at 958
                -21f at 972
                -11f at 986
                0f at 1000

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 375

                218f at 389
                226f at 403
                233f at 416
                238f at 430
                242f at 444
                246f at 458
                248f at 472
                249f at 486

                250f at 500

                249f at 514
                248f at 528
                246f at 541
                242f at 555
                238f at 569
                233f at 583
                226f at 597
                218f at 611

                213f at 625

                208f at 639
                198f at 653
                188f at 666
                177f at 680
                167f at 694
                156f at 708
                146f at 722
                136f at 736

                125f at 750

                114f at 764
                104f at 778
                94f at 791
                83f at 805
                73f at 819
                62f at 833
                52f at 847
                42f at 861

                37f at 875

                32f at 889
                24f at 903
                17f at 916
                12f at 930
                8f at 944
                4f at 958
                2f at 972
                1f at 986
                0f at 1000

            }
        )
        1 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 375

                83f at 389
                73f at 403
                63f at 416
                52f at 430
                42f at 444
                31f at 458
                21f at 472
                11f at 486

                0f at 500

                -11f at 514
                -21f at 528
                -31f at 541
                -42f at 555
                -52f at 569
                -63f at 583
                -73f at 597
                -83f at 611

                -88f at 625

                -93f at 639
                -101f at 653
                -108f at 666
                -113f at 680
                -117f at 694
                -121f at 708
                -123f at 722
                -124f at 736

                -125f at 750

                -124f at 764
                -123f at 778
                -121f at 791
                -117f at 805
                -113f at 819
                -108f at 833
                -101f at 847
                -93f at 861

                -88f at 862
                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 375

                218f at 389
                226f at 403
                233f at 416
                238f at 430
                242f at 444
                246f at 458
                248f at 472
                249f at 486

                250f at 500

                249f at 514
                248f at 528
                246f at 541
                242f at 555
                238f at 569
                233f at 583
                226f at 597
                218f at 611

                213f at 625

                208f at 639
                198f at 653
                188f at 666
                177f at 680
                167f at 694
                156f at 708
                146f at 722
                136f at 736

                125f at 750

                114f at 764
                104f at 778
                94f at 791
                83f at 805
                73f at 819
                62f at 833
                52f at 847
                42f at 861

                37f at 862
                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        2 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 375

                83f at 389
                73f at 403
                63f at 416
                52f at 430
                42f at 444
                31f at 458
                21f at 472
                11f at 486

                0f at 500

                -11f at 514
                -21f at 528
                -31f at 541
                -42f at 555
                -52f at 569
                -63f at 583
                -73f at 597
                -83f at 611

                -88f at 625

                -93f at 639
                -101f at 653
                -108f at 666
                -113f at 680
                -117f at 694
                -121f at 708
                -123f at 722
                -124f at 736

                -125f at 737
                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 11875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 375

                218f at 389
                226f at 403
                233f at 416
                238f at 430
                242f at 444
                246f at 458
                248f at 472
                249f at 486

                250f at 500

                249f at 514
                248f at 528
                246f at 541
                242f at 555
                238f at 569
                233f at 583
                226f at 597
                218f at 611

                213f at 625

                208f at 639
                198f at 653
                188f at 666
                177f at 680
                167f at 694
                156f at 708
                146f at 722
                136f at 736

                125f at 737
                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        3 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 375

                83f at 389
                73f at 403
                63f at 416
                52f at 430
                42f at 444
                31f at 458
                21f at 472
                11f at 486

                0f at 500

                -11f at 514
                -21f at 528
                -31f at 541
                -42f at 555
                -52f at 569
                -63f at 583
                -73f at 597
                -83f at 611

                -88f at 612
                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 375

                218f at 389
                226f at 403
                233f at 416
                238f at 430
                242f at 444
                246f at 458
                248f at 472
                249f at 486

                250f at 500

                249f at 514
                248f at 528
                246f at 541
                242f at 555
                238f at 569
                233f at 583
                226f at 597
                218f at 611

                213f at 612
                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        4 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 375

                83f at 389
                73f at 403
                63f at 416
                52f at 430
                42f at 444
                31f at 458
                21f at 472
                11f at 486

                0f at 487
                0f at 1500

                -11f at 1514
                -21f at 1528
                -31f at 1541
                -42f at 1555
                -52f at 1569
                -63f at 1583
                -73f at 1597
                -83f at 1611

                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 375

                218f at 389
                226f at 403
                233f at 416
                238f at 430
                242f at 444
                246f at 458
                248f at 472
                249f at 486

                250f at 487
                250f at 1500

                249f at 1514
                248f at 1528
                246f at 1541
                242f at 1555
                238f at 1569
                233f at 1583
                226f at 1597
                218f at 1611

                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        5 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 250

                124f at 264
                123f at 278
                121f at 291
                117f at 305
                113f at 319
                108f at 333
                101f at 347
                93f at 361

                88f at 362
                88f at 1375

                83f at 1389
                73f at 1403
                63f at 1416
                52f at 1430
                42f at 1444
                31f at 1458
                21f at 1472
                11f at 1486

                0f at 1500

                -11f at 1514
                -21f at 1528
                -31f at 1541
                -42f at 1555
                -52f at 1569
                -63f at 1583
                -73f at 1597
                -83f at 1611

                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 250

                136f at 264
                146f at 278
                156f at 291
                167f at 305
                177f at 319
                188f at 333
                198f at 347
                208f at 361

                213f at 362
                213f at 1375

                218f at 1389
                226f at 1403
                233f at 1416
                238f at 1430
                242f at 1444
                246f at 1458
                248f at 1472
                249f at 1486

                250f at 1500

                249f at 1514
                248f at 1528
                246f at 1541
                242f at 1555
                238f at 1569
                233f at 1583
                226f at 1597
                218f at 1611

                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        6 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 125

                93f at 139
                101f at 153
                108f at 166
                113f at 180
                117f at 194
                121f at 208
                123f at 222
                124f at 236

                125f at 237
                125f at 1250

                124f at 1264
                123f at 1278
                121f at 1291
                117f at 1305
                113f at 1319
                108f at 1333
                101f at 1347
                93f at 1361

                88f at 1375

                83f at 1389
                73f at 1403
                63f at 1416
                52f at 1430
                42f at 1444
                31f at 1458
                21f at 1472
                11f at 1486

                0f at 1500

                -11f at 1514
                -21f at 1528
                -31f at 1541
                -42f at 1555
                -52f at 1569
                -63f at 1583
                -73f at 1597
                -83f at 1611

                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 125

                42f at 139
                52f at 153
                62f at 166
                73f at 180
                83f at 194
                94f at 208
                104f at 222
                114f at 236

                125f at 237
                125f at 1250

                136f at 1264
                146f at 1278
                156f at 1291
                167f at 1305
                177f at 1319
                188f at 1333
                198f at 1347
                208f at 1361

                213f at 1375

                218f at 1389
                226f at 1403
                233f at 1416
                238f at 1430
                242f at 1444
                246f at 1458
                248f at 1472
                249f at 1486

                250f at 1500

                249f at 1514
                248f at 1528
                246f at 1541
                242f at 1555
                238f at 1569
                233f at 1583
                226f at 1597
                218f at 1611

                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        7 -> Pair(
            keyframes {
                durationMillis = 2000

                11f at 14
                21f at 28
                31f at 41
                42f at 55
                52f at 69
                63f at 83
                73f at 97
                83f at 111

                88f at 112
                88f at 1125

                93f at 1139
                101f at 1153
                108f at 1166
                113f at 1180
                117f at 1194
                121f at 1208
                123f at 1222
                124f at 1236

                125f at 1250

                124f at 1264
                123f at 1278
                121f at 1291
                117f at 1305
                113f at 1319
                108f at 1333
                101f at 1347
                93f at 1361

                88f at 1375

                83f at 1389
                73f at 1403
                63f at 1416
                52f at 1430
                42f at 1444
                31f at 1458
                21f at 1472
                11f at 1486

                0f at 1500

                -11f at 1514
                -21f at 1528
                -31f at 1541
                -42f at 1555
                -52f at 1569
                -63f at 1583
                -73f at 1597
                -83f at 1611

                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                1f at 14
                2f at 28
                4f at 41
                8f at 55
                12f at 69
                17f at 83
                24f at 97
                32f at 111

                37f at 112
                37f at 1125

                42f at 1139
                52f at 1153
                62f at 1166
                73f at 1180
                83f at 1194
                94f at 1208
                104f at 1222
                114f at 1236

                125f at 1250

                136f at 1264
                146f at 1278
                156f at 1291
                167f at 1305
                177f at 1319
                188f at 1333
                198f at 1347
                208f at 1361

                213f at 1375

                218f at 1389
                226f at 1403
                233f at 1416
                238f at 1430
                242f at 1444
                246f at 1458
                248f at 1472
                249f at 1486

                250f at 1500

                249f at 1514
                248f at 1528
                246f at 1541
                242f at 1555
                238f at 1569
                233f at 1583
                226f at 1597
                218f at 1611

                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        8 -> Pair(
            keyframes {
                durationMillis = 2000

                0f at 1000
                11f at 1014
                21f at 1028
                31f at 1041
                42f at 1055
                52f at 1069
                63f at 1083
                73f at 1097
                83f at 1111

                88f at 1125

                93f at 1139
                101f at 1153
                108f at 1166
                113f at 1180
                117f at 1194
                121f at 1208
                123f at 1222
                124f at 1236

                125f at 1250

                124f at 1264
                123f at 1278
                121f at 1291
                117f at 1305
                113f at 1319
                108f at 1333
                101f at 1347
                93f at 1361

                88f at 1375

                83f at 1389
                73f at 1403
                63f at 1416
                52f at 1430
                42f at 1444
                31f at 1458
                21f at 1472
                11f at 1486

                0f at 1500

                -11f at 1514
                -21f at 1528
                -31f at 1541
                -42f at 1555
                -52f at 1569
                -63f at 1583
                -73f at 1597
                -83f at 1611

                -88f at 1625

                -93f at 1639
                -101f at 1653
                -108f at 1666
                -113f at 1680
                -117f at 1694
                -121f at 1708
                -123f at 1722
                -124f at 1736

                -125f at 1750

                -124f at 1764
                -123f at 1778
                -121f at 1791
                -117f at 1805
                -113f at 1819
                -108f at 1833
                -101f at 1847
                -93f at 1861

                -88f at 1875

                -83f at 1889
                -73f at 1903
                -63f at 1916
                -52f at 1930
                -42f at 1944
                -31f at 1958
                -21f at 1972
                -11f at 1986

            },
            keyframes {
                durationMillis = 2000

                0f at 1000
                1f at 1014
                2f at 1028
                4f at 1041
                8f at 1055
                12f at 1069
                17f at 1083
                24f at 1097
                32f at 1111

                37f at 1125

                42f at 1139
                52f at 1153
                62f at 1166
                73f at 1180
                83f at 1194
                94f at 1208
                104f at 1222
                114f at 1236

                125f at 1250

                136f at 1264
                146f at 1278
                156f at 1291
                167f at 1305
                177f at 1319
                188f at 1333
                198f at 1347
                208f at 1361

                213f at 1375

                218f at 1389
                226f at 1403
                233f at 1416
                238f at 1430
                242f at 1444
                246f at 1458
                248f at 1472
                249f at 1486

                250f at 1500

                249f at 1514
                248f at 1528
                246f at 1541
                242f at 1555
                238f at 1569
                233f at 1583
                226f at 1597
                218f at 1611

                213f at 1625

                208f at 1639
                198f at 1653
                188f at 1666
                177f at 1680
                167f at 1694
                156f at 1708
                146f at 1722
                136f at 1736

                125f at 1750

                114f at 1764
                104f at 1778
                94f at 1791
                83f at 1805
                73f at 1819
                62f at 1833
                52f at 1847
                42f at 1861

                37f at 1875

                32f at 1889
                24f at 1903
                17f at 1916
                12f at 1930
                8f at 1944
                4f at 1958
                2f at 1972
                1f at 1986

            }
        )
        else -> throw Exception("CardIndex out of bounds")
    }

}

private fun getDefaultRotation() : KeyframesSpec<Float> {
    return keyframes {
        durationMillis = 1000

        90f at 250
        180f at 500
        270f at 750

    }
}

private fun getDefaultLoading() : Pair<KeyframesSpec<Float>, KeyframesSpec<Float>> {
    return Pair(
        keyframes {
            durationMillis = 1000

            11f at 14
            21f at 28
            31f at 41
            42f at 55
            52f at 69
            63f at 83
            73f at 97
            83f at 111

            88f at 125

            93f at 139
            101f at 153
            108f at 166
            113f at 180
            117f at 194
            121f at 208
            123f at 222
            124f at 236

            125f at 250

            124f at 264
            123f at 278
            121f at 291
            117f at 305
            113f at 319
            108f at 333
            101f at 347
            93f at 361

            88f at 375

            83f at 389
            73f at 403
            63f at 416
            52f at 430
            42f at 444
            31f at 458
            21f at 472
            11f at 486

            0f at 500

            -11f at 514
            -21f at 528
            -31f at 541
            -42f at 555
            -52f at 569
            -63f at 583
            -73f at 597
            -83f at 611

            -88f at 625

            -93f at 639
            -101f at 653
            -108f at 666
            -113f at 680
            -117f at 694
            -121f at 708
            -123f at 722
            -124f at 736

            -125f at 750

            -124f at 764
            -123f at 778
            -121f at 791
            -117f at 805
            -113f at 819
            -108f at 833
            -101f at 847
            -93f at 861

            -88f at 875

            -83f at 889
            -73f at 903
            -63f at 916
            -52f at 930
            -42f at 944
            -31f at 958
            -21f at 972
            -11f at 986

        },
        keyframes {
            durationMillis = 1000

            1f at 14
            2f at 28
            4f at 41
            8f at 55
            12f at 69
            17f at 83
            24f at 97
            32f at 111

            37f at 125

            42f at 139
            52f at 153
            62f at 166
            73f at 180
            83f at 194
            94f at 208
            104f at 222
            114f at 236

            125f at 250

            136f at 264
            146f at 278
            156f at 291
            167f at 305
            177f at 319
            188f at 333
            198f at 347
            208f at 361

            213f at 375

            218f at 389
            226f at 403
            233f at 416
            238f at 430
            242f at 444
            246f at 458
            248f at 472
            249f at 486

            250f at 500

            249f at 514
            248f at 528
            246f at 541
            242f at 555
            238f at 569
            233f at 583
            226f at 597
            218f at 611

            213f at 625

            208f at 639
            198f at 653
            188f at 666
            177f at 680
            167f at 694
            156f at 708
            146f at 722
            136f at 736

            125f at 750

            114f at 764
            104f at 778
            94f at 791
            83f at 805
            73f at 819
            62f at 833
            52f at 847
            42f at 861

            37f at 875

            32f at 889
            24f at 903
            17f at 916
            12f at 930
            8f at 944
            4f at 958
            2f at 972
            1f at 986

        }
    )
}

@Preview
@Composable
fun PreviewLoadingCard() {
    LoadingCard(
        cardIcon = R.drawable.card_icon_crosses_2,
        cardIndex = 0
    )
}

@Preview
@Composable
fun PreviewLoadingAnimation() {
    val cards = listOf(
        R.drawable.card_icon_crosses_ace,
        R.drawable.card_icon_crosses_1,
        R.drawable.card_icon_crosses_k,
        R.drawable.card_icon_crosses_j,
        R.drawable.card_icon_crosses_2,
        R.drawable.card_icon_crosses_7,
        R.drawable.card_icon_crosses_9,
        R.drawable.card_icon_crosses_10,
    )
    DefaultLoadingAnimation(cardIcons = cards)
}