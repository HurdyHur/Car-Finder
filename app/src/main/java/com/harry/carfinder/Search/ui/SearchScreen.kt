package com.harry.carfinder.Search.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harry.carfinder.R
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.search_usecase.model.VehicleMake

@Composable
fun SearchScreen(makes: List<VehicleMake>?, models: List<String>?, dates: List<String>?, onSearch: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            SearchDropDowns(makes = makes, models = models, dates = dates)
            SearchButton { onSearch }
        }
    }
}

@Composable
fun SearchDropDowns(makes: List<VehicleMake>?, models: List<String>?, dates: List<String>?) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        DropDownList(hintText = R.string.drop_down_hint_make, items = makes?.map { it.name })

        DropDownList(hintText = R.string.drop_down_hint_model, items = models)

        DropDownList(hintText = R.string.drop_down_hint_year, items = dates)
    }
}

@Composable
fun DropDownList(@StringRes hintText: Int, items: List<String>?) {
    var expanded by remember { mutableStateOf(false) }

    var text by remember {
        mutableStateOf<String?>(null)
    }

    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            enabled = !items.isNullOrEmpty(),
            onClick = { expanded = true }) {
            Text(text ?: stringResource(id = hintText))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxSize()
        ) {
            items?.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    expanded = false
                    text = it
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

        SearchScreen(makes = makes, models = emptyList(), dates = dates) { }
    }
}
