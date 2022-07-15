package com.harry.carfinder.Search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.harry.carfinder.R
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.carfinder.ui.theme.CarFinderTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchQueryScreen(
    selectedMake: LiveData<String>,
    selectedModel: LiveData<String>,
    selectedYear: LiveData<String>,
    makes: LiveData<List<String>>,
    models: LiveData<List<String>>,
    dates: LiveData<List<String>>,
    onSearch: () -> Unit,
    onMakeSelected: (String) -> Unit,
    onModelSelected: (String) -> Unit,
    onYearSelected: (String) -> Unit
) {
    Column {
        CarFinderTopBar()
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Column {
                    QueryTitleView()

                    val makesHintText = selectedMake.observeAsState().value
                    val modelsHintText = selectedModel.observeAsState().value
                    val yearsHintText = selectedYear.observeAsState().value

                    SearchDropDowns(
                        makesHintText = makesHintText,
                        modelsHintText = modelsHintText,
                        yearsHintText = yearsHintText,
                        makes = makes.observeAsState().value,
                        models = models.observeAsState().value,
                        dates = dates.observeAsState().value,
                        onMakeSelected,
                        onModelSelected,
                        onYearSelected
                    )

                    val enabled =
                        makesHintText != null && modelsHintText != null && yearsHintText != null

                    SearchButton(enabled) { onSearch() }
                }
            }
        }
    }
}

@Composable
fun QueryTitleView() {
    Text(
        stringResource(id = R.string.search_query_title),
        textAlign = TextAlign.Center,
        fontSize = 28.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun SearchDropDowns(
    makesHintText: String?,
    modelsHintText: String?,
    yearsHintText: String?,
    makes: List<String>?,
    models: List<String>?,
    dates: List<String>?,
    onMakeSelected: (String) -> Unit,
    onModelSelected: (String) -> Unit,
    onYearSelected: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {

        DropDownList(
            hintText = makesHintText ?: stringResource(id = R.string.drop_down_hint_make),
            items = makes,
            onMakeSelected
        )

        DropDownList(
            hintText = modelsHintText ?: stringResource(id = R.string.drop_down_hint_model),
            items = models,
            onModelSelected
        )

        DropDownList(
            hintText = yearsHintText ?: stringResource(id = R.string.drop_down_hint_year),
            items = dates,
            onYearSelected
        )
    }
}

@Composable
fun DropDownList(hintText: String, items: List<String>?, onClick: (item: String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            enabled = !items.isNullOrEmpty(),
            onClick = { expanded = true }) {
            Text(hintText, textAlign = TextAlign.Left, modifier = Modifier.fillMaxWidth())
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .offset(y = 24.dp)
                .fillMaxSize()
        ) {
            items?.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    expanded = false
                    onClick(item)
                })
            }
        }
    }
}

@Composable
fun SearchButton(enabled: Boolean, onClick: () -> Unit) {
    val mainButtonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Red,
        contentColor = Color.White
    )

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            enabled = enabled,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = mainButtonColor
        ) {
            Text(
                text = stringResource(id = R.string.search_button),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchQueryPreview() {
    CarFinderTheme {

        val makes = listOf("name 1", "name 2", "name 3")

        val models = listOf("model 1", "model 2", "model 3")

        val dates = listOf("2000", "2001", "2002")

        SearchDropDowns(makes = makes,
            models = models,
            dates = dates,
            makesHintText = "Makes",
            modelsHintText = "Models",
            yearsHintText = "Years",
            onModelSelected = {},
            onMakeSelected = {},
            onYearSelected = {})
    }
}
