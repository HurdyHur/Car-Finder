package com.harry.carfinder.ui.theme


import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.harry.carfinder.R

@Composable
fun CarFinderTopBar() {
    TopAppBar {
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(fontSize = dimensionResource(id = R.dimen.top_bar_text_size).value.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))
        )
    }
}

