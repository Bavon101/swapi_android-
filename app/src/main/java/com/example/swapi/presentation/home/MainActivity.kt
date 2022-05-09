package com.example.swapi.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swapi.presentation.search.SearchView
import com.example.swapi.ui.theme.SWAPITheme
import com.example.swapi.viewmodels.PeopleViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pvm = PeopleViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            SWAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App(peopleViewModel = pvm)
                }
            }
        }
    }
}

@Composable
fun App(peopleViewModel: PeopleViewModel){
    val navHostController = rememberNavController()
    NavHost(navHostController, startDestination = "home" ){
        composable( route = "home"){
            HomeView(navController = navHostController, peopleViewModel = peopleViewModel )
        }

        composable( route ="search"){
            SearchView(navController = navHostController, peopleViewModel = peopleViewModel)
        }
    }

}

@Composable
fun HomeView(navController: NavController?, peopleViewModel: PeopleViewModel?) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("People of Star Wars") },
            navigationIcon = {
                Icon(Icons.Filled.Star, contentDescription = "The force")
            },
            actions = {
                IconButton(onClick = {
                    navController?.navigate("search")
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }

            }
        )
    },
        content = {
            Text("Welcome to the stars")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SWAPITheme {
        HomeView(null,null)
    }
}