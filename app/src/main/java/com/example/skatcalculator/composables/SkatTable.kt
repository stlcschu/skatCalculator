package com.example.skatcalculator.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.skatcalculator.R

@Composable
fun SkatTable() {
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(75.dp)
        ) {
            Spacer(modifier = Modifier.width(208.dp))
            Divider(color = Color.Gray, modifier = Modifier
                .height(72.dp)
                .width(1.dp))
            Column(
                modifier = Modifier
                    .width(36.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Grand",
                    maxLines = 1,
                    modifier = Modifier.height(24.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 3.em
                )
                Text(
//                    modifier = Modifier.padding(5.dp),
                    text = "24"
                )
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(72.dp)
                .width(1.dp))
            Column(
                modifier = Modifier.width(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                Text(text = "12")
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(72.dp)
                .width(1.dp))
            Column(
                modifier = Modifier.width(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                Text(text = "11")
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(72.dp)
                .width(1.dp))
            Column(
                modifier = Modifier.width(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                Text(text = "10")
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(72.dp)
                .width(1.dp))
            Column(
                modifier = Modifier.width(36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                Text(text = "9")
            }
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
                Divider()
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "mit", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "1 Spiel 2", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "48", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "24", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "22", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "20", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "18", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
                Divider()
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "ohne", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "1 Spiel 2", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "48", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "24", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "22", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "20", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(96.dp)
                .width(1.dp))
            Text(text = "18", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "mit", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "2 Spiel 3", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "72", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "36", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "33", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "30", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "27", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                }
                Divider()
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "ohne", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "2 Spiel 3", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "72", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "36", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "33", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "30", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(46.dp)
                .width(1.dp))
            Text(text = "27", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
            Text(text = "mit", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "3 Spiel 4", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "96", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "48", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "44", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "40", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "36", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "ohne", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "3 Spiel 4", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "96", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "48", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "44", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "40", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "36", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Image(painter = painterResource(id = R.drawable.symbol_cross), contentDescription = "Crosses symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_spade), contentDescription = "Spades symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_heart), contentDescription = "Heart symbol")
                    Image(painter = painterResource(id = R.drawable.symbol_diamond), contentDescription = "Diamond symbol")
                }
            }
            Text(text = "mit", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "4 Spiel 5", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "120", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "60", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "55", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "50", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "45", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(96.dp)
            ) {
                Row() {
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
            Text(text = "ohne", modifier = Modifier.width(48.dp), textAlign = TextAlign.Center)
            Text(text = "4 Spiel 5", modifier = Modifier.width(64.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "120", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "60", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "55", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "50", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Text(text = "45", modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
        }
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.5f).height(24.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Null",
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "23",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(24.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Null Ouvert",
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "46",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.5f).height(24.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Null Hand",
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "35",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Divider(color = Color.Gray, modifier = Modifier
                .height(21.dp)
                .width(1.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(24.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Null Ouvert Hand",
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "59",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SkatTablePreview() {
    SkatTable()
}