package com.harry.carfinder.Search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.harry.carfinder.R
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.search_usecase.model.VehicleMake

@Composable
fun SearchScreen(
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            SearchDropDowns(
                makesHintText = selectedMake.observeAsState().value,
                modelsHintText = selectedModel.observeAsState().value,
                yearsHintText = selectedYear.observeAsState().value,
                makes = makes.observeAsState().value,
                models = models.observeAsState().value,
                dates = dates.observeAsState().value,
                onMakeSelected,
                onModelSelected,
                onYearSelected
            )
            SearchButton { onSearch }
        }
    }
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
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
            enabled = !items.isNullOrEmpty(),
            onClick = { expanded = true }) {
            Text(hintText)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxSize()
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
fun SearchButton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(onClick = onClick, Modifier.fillMaxWidth(0.5f)) {
            Text(text = stringResource(id = R.string.search_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarFinderTheme {

        val makes = listOf<VehicleMake>(
            VehicleMake("name 1", emptyList()),
            VehicleMake("name 2", emptyList()),
            VehicleMake("name 3", emptyList())
        )

        val models = listOf("model 1", "model 2", "model 3")

        val dates = listOf("2000", "2001", "2002")

        //SearchScreen(makes = makes, models = emptyList(), dates = dates) { }
    }
}
