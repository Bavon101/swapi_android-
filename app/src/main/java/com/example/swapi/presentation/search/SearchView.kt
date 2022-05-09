package com.example.swapi.presentation.search

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.swapi.presentation.search.ui.theme.SWAPITheme
import com.example.swapi.viewmodels.PeopleViewModel


@Composable
fun SearchView(navController: NavController?, peopleViewModel: PeopleViewModel?) {
    Scaffold() {
        
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SWAPITheme {
        SearchView(null,null)
    }
}