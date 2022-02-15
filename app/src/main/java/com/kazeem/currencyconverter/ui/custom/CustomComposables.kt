package com.kazeem.currencyconverter.ui.custom

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kazeem.currencyconverter.R
import com.kazeem.currencyconverter.databinding.LineChartLayoutBinding
import com.kazeem.currencyconverter.model.CurrencyData
import com.kazeem.currencyconverter.ui.theme.*
import com.kazeem.currencyconverter.util.Utils
import com.kazeem.currencyconverter.viewModel.MainActivityViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

// This file holds all the custom views that is needed to build the app Ui.

/**
 * Simple custom view for currency input
 */
@ExperimentalFoundationApi
@ExperimentalUnitApi
@Composable
fun CurrencyInputView(
    textInput: MutableState<String>, currency: MutableState<CurrencyData>,
    enabled: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .clip(MaterialTheme.shapes.small)
            .background(color = Gray100, shape = MaterialTheme.shapes.small)
            .combinedClickable(
                onClick = {

                },
                onLongClick = {
                    if (textInput.value.isNotEmpty()) Utils.copyTextToClipboard(
                        context = context,
                        textInput.value,
                        "Text copied to clipboard"
                    )
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            value = textInput.value.toString(),
            onValueChange = {
                if (it.length < 20) textInput.value = it
            },
            modifier = Modifier
                .weight(1F)
                .padding(10.dp),
            textStyle = TextStyle(
                fontSize = TextUnit(22f, TextUnitType.Sp),
                color = Gray500,
                fontWeight = FontWeight.Bold
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            enabled = enabled
        )

        Text(
            text = currency.value.currency,
            color = Gray300,
            style = TextStyle(
                fontSize = TextUnit(22f, TextUnitType.Sp),
                color = Gray300,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(10.dp)
        )
    }
}

/**
 * Simple custom view for selecting currency. It holds the country flag, currency and the drop down icon state
 */
@ExperimentalUnitApi
@Composable
fun CurrencyView(
    selectedCurrency: MutableState<CurrencyData>,
    modifier: Modifier,
    currencyDataList: List<CurrencyData>,
    onclick: (hide: Boolean) -> Unit
) {
    val clickedState = rememberSaveable {
        mutableStateOf(false)
    }
    val icon = Icons.Rounded.KeyboardArrowDown
    OutlinedButton(
        onClick = { clickedState.value = !clickedState.value; onclick(clickedState.value) },
        border = BorderStroke(1.dp, color = Gray400),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = selectedCurrency.value.flag,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(5.dp)
        )
        Text(
            text = selectedCurrency.value.currency,
            color = Gray300,
            style = TextStyle(
                fontSize = TextUnit(22f, TextUnitType.Sp),
                color = Gray300,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(5.dp)
        )

        Icon(imageVector = icon, contentDescription = "Arrow Icons", tint = Gray300)
    }
}

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun CurrencyPickerDialog(
    currencyPickerSheetState: ModalBottomSheetState,
    selectedButton: MutableState<Int>,
    viewModel: MainActivityViewModel
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetState = currencyPickerSheetState,
        modifier = Modifier.fillMaxWidth(),
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                items(viewModel.currencyList.size) { index ->
                    val currencyData = viewModel.currencyList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                coroutine.launch {
                                    if (selectedButton.value == 0) {
                                        if (viewModel.selectedTargetCurrency.value.currency == currencyData.currency) {
                                            viewModel.selectedTargetCurrency.value = CurrencyData(
                                                currency = viewModel.selectedBaseCurrency.value.currency,
                                                flag = viewModel.selectedBaseCurrency.value.flag,
                                                name = viewModel.selectedBaseCurrency.value.name
                                            )
                                        }
                                        viewModel.selectedBaseCurrency.value = CurrencyData(
                                            currency = currencyData.currency,
                                            flag = currencyData.flag,
                                            name = currencyData.name
                                        )
                                    } else {
                                        if (viewModel.selectedBaseCurrency.value.currency == currencyData.currency) {
                                            viewModel.selectedBaseCurrency.value = CurrencyData(
                                                currency = viewModel.selectedTargetCurrency.value.currency,
                                                flag = viewModel.selectedTargetCurrency.value.flag,
                                                name = viewModel.selectedTargetCurrency.value.name
                                            )
                                        }
                                        viewModel.selectedTargetCurrency.value = CurrencyData(
                                            currency = currencyData.currency,
                                            flag = currencyData.flag,
                                            name = currencyData.name
                                        )
                                    }
                                    currencyPickerSheetState.hide()
                                    if (viewModel.baseCurrencyValue.value.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                                        viewModel.convertCurrency(context)
                                    }
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = currencyData.flag,
                                builder = {
                                    transformations(CircleCropTransformation())
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(5.dp)
                        )
                        Text(
                            text = "${currencyData.name}",
                            color = Color.DarkGray,
                            style = TextStyle(
                                fontSize = TextUnit(22f, TextUnitType.Sp),
                                color = Gray300,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        },
    ) {}
}

@Composable
fun ChartMainView(viewModel: MainActivityViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .background(
                color = Blue700,
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
            )
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            MyLineChart(viewModel = viewModel, context = context)
        }

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
        ) {
            Text(text = "Get rate alerts straights to your email inbox", textDecoration = TextDecoration.Underline, color = Color.White)
        }
    }
}



@Composable
fun MyLineChart(viewModel: MainActivityViewModel, context: Context) {
    val entries: ArrayList<Entry> = ArrayList()
    var count: Float = -1F
    for(data in 0..10){
        count++
        entries.add(BarEntry(count, floatArrayOf(data.times(Random.nextInt().toFloat()))))
    }
    val dataset = LineDataSet(entries, "currency")
    val fillGradient = ContextCompat.getDrawable(context, R.drawable.line_chart_gradient)
    dataset.setDrawFilled(true)
    dataset.fillDrawable = fillGradient
    val data = LineData(dataset)
    AndroidViewBinding(LineChartLayoutBinding::inflate, modifier = Modifier.fillMaxWidth()){
        val description = Description()
        description.text = "Past Currency Trend"
        lineChart.notifyDataSetChanged()
        lineChart.setNoDataText("No chart data available.")
        description.also { lineChart.description = it }
        lineChart.animateXY(2000, 2000)
        lineChart.setDrawBorders(true)
    }
}