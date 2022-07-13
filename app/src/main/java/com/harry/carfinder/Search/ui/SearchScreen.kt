package com.harry.carfinder.Search.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harry.carfinder.R
import com.harry.carfinder.ui.theme.CarFinderTheme
import com.harry.search_usecase.model.VehicleMake

@Composable
fun SearchScreen(makes: List<VehicleMake>, models: List<String>, dates: List<String>) {
    SearchDropDowns(makes = makes, models = models, dates = dates)
}

@Composable
fun SearchDropDowns(makes: List<VehicleMake>, models: List<String>, dates: List<String>) {
    Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
        Box {
            DropDownList(hintText = R.string.drop_down_hint_make, items = makes.map { it.name })
        }
        Box {
            DropDownList(hintText = R.string.drop_down_hint_model, items = models)
        }
        Box {
            DropDownList(hintText = R.string.drop_down_hint_year, items = dates)
        }
    }
}

@Composable
fun DropDownList(@StringRes hintText: Int, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }) {
                Text(stringResource(id = hintText))
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxSize()) {
                items.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { expanded = false })
                }
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

        SearchScreen(makes = makes, models = models, dates = dates)
    }
}
