package com.kazeem.currencyconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.kazeem.currencyconverter.ui.theme.Blue500
import com.kazeem.currencyconverter.ui.theme.CurrencyConverterTheme
import com.kazeem.currencyconverter.ui.theme.Green500
import com.kazeem.currencyconverter.viewModel.MainActivityViewModel
import com.kazeem.currencyconverter.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // I will be using Jetpack Compose (Android modern toolkit for building native UI) to design the UI for this application since it simplifies and accelerates UI development on Android.
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityView()
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun MainActivityView() {
    val context = LocalContext.current
    Scaffold() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, end = 15.dp, start = 5.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { // TopAppBar
                IconButton(onClick = { Toast.makeText(context, "Not part of the assignment task", Toast.LENGTH_LONG).show() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu Button", tint = Green500, modifier = Modifier.size(24.dp))
                }
                Text(text = "Sign up", color = Green500, style = MaterialTheme.typography.h3)
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Blue500, fontSize = TextUnit(38f, TextUnitType.Sp), fontWeight = FontWeight.W900)) {
                                    append("Currency\nCalculator")
                                }
                                withStyle(style = SpanStyle(color = Green500, fontSize = TextUnit(40f, TextUnitType.Sp), fontWeight = FontWeight.W900)) {
                                    append(".")
                                }
                            },
                            modifier = Modifier.padding(vertical = 25.dp, horizontal = 15.dp)
                        ) // Currency Calculator.
                    }
                }
            }
        }
    }
}