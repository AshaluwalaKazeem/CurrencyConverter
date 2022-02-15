package com.kazeem.currencyconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kazeem.currencyconverter.db.CurrencyDatabaseBuilder
import com.kazeem.currencyconverter.db.CurrencyDatabaseHelperImpl
import com.kazeem.currencyconverter.ui.custom.ChartMainView
import com.kazeem.currencyconverter.ui.custom.CurrencyInputView
import com.kazeem.currencyconverter.ui.custom.CurrencyPickerDialog
import com.kazeem.currencyconverter.ui.custom.CurrencyView
import com.kazeem.currencyconverter.ui.theme.Blue500
import com.kazeem.currencyconverter.ui.theme.Blue700
import com.kazeem.currencyconverter.ui.theme.CurrencyConverterTheme
import com.kazeem.currencyconverter.ui.theme.Green500
import com.kazeem.currencyconverter.viewModel.MainActivityViewModel
import com.kazeem.currencyconverter.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // I will be using Jetpack Compose (Android modern toolkit for building native UI) to design the UI for this application since it simplifies and accelerates UI development on Android.

        val currencyDb = CurrencyDatabaseHelperImpl(CurrencyDatabaseBuilder.getInstance(applicationContext))
        val viewModelFactory = ViewModelFactory(currencyDb)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.getAllCurrency()
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val systemUiController = rememberSystemUiController()
                    LaunchedEffect(key1 = 0) {
                        systemUiController.setSystemBarsColor(
                            color = Color.White,
                            darkIcons = true
                        )
                        systemUiController.setNavigationBarColor(
                            color = Blue700,
                            darkIcons = false
                        )
                    }
                    MainActivityView(viewModel)
                }
            }
        }
    }
}

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun MainActivityView(viewModel: MainActivityViewModel) {
    val context = LocalContext.current
    val currencyPickerSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()
    val selectedButton = rememberSaveable{
        mutableStateOf(0)// Zero means Base currency other number means target currency
    }
    Scaffold() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 15.dp, start = 5.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) { // TopAppBar
                IconButton(onClick = {
                    Toast.makeText(
                        context,
                        "Not part of the assignment task",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Menu Button",
                        tint = Green500,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(text = "Sign up", color = Green500, style = MaterialTheme.typography.h3)
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Blue500,
                                        fontSize = TextUnit(38f, TextUnitType.Sp),
                                        fontWeight = FontWeight.W900
                                    )
                                ) {
                                    append("Currency\nCalculator")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Green500,
                                        fontSize = TextUnit(40f, TextUnitType.Sp),
                                        fontWeight = FontWeight.W900
                                    )
                                ) {
                                    append(".")
                                }
                            },
                            modifier = Modifier.padding(vertical = 25.dp, horizontal = 15.dp).fillMaxWidth()
                        ) // Currency Calculator.
                        CurrencyInputView(
                            textInput = viewModel.baseCurrencyValue,
                            currency = viewModel.selectedBaseCurrency
                        ) // Input layout for base currency
                        CurrencyInputView(
                            textInput = viewModel.targetCurrencyValue,
                            currency = viewModel.selectedTargetCurrency
                        ) // Input layout for target currency
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp, vertical = 15.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CurrencyView(
                                selectedCurrency = viewModel.selectedBaseCurrency,
                                modifier = Modifier.weight(1f),
                                viewModel.currencyList,
                                onclick = {
                                    coroutine.launch {
                                        selectedButton.value = 0
                                        if(it) currencyPickerSheetState.show()
                                    }
                                }
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Rounded.SwapHoriz,
                                    contentDescription = "Swap base currency to target currency"
                                )
                            }
                            CurrencyView(
                                selectedCurrency = viewModel.selectedTargetCurrency,
                                modifier = Modifier.weight(1f),
                                viewModel.currencyList,
                                onclick = {
                                    coroutine.launch {
                                        selectedButton.value = 1
                                        if(it) currencyPickerSheetState.show()
                                    }
                                }
                            )
                        } // Select base or target currency
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp, vertical = 15.dp)
                        ) {
                            Text("Convert", modifier = Modifier.padding(7.dp))
                        }

                        TextButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                        ) {
                            Text(text = "Mid-market as exchange rate at 13:38 UTC", textDecoration = TextDecoration.Underline, color = Blue500)
                        }

                        ChartMainView()
                    }
                }
            }
        }
    }
    CurrencyPickerDialog(currencyPickerSheetState, viewModel = viewModel, selectedButton = selectedButton, )
}