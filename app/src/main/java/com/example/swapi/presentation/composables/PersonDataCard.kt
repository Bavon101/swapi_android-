package com.example.swapi.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PersonDataCard(title: String,value: String?) {
    Row() {
        Text(text = "$title : ")
        if (value != null) {
            Text(text = value)
        }else{
            Text(text = "unknown")
        }
    }
}