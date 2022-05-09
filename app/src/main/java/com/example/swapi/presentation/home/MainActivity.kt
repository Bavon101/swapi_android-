package com.example.swapi.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swapi.presentation.composables.CharacterCard
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

    LaunchedEffect(Unit, block = {
        if(peopleViewModel!!.people == null){
            peopleViewModel.getStarPeople()
        }
    })
    val centerModifier = Modifier
        .padding(12.dp)
        .fillMaxSize()
    val listState = rememberLazyListState()
    if(listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == (listState.layoutInfo.totalItemsCount -1)){
        if(!peopleViewModel!!.loadingMore){
            peopleViewModel.getStarPeople(true)
        }
    }

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
            // display people data or status as per the data status
            if((peopleViewModel!!.people == null && peopleViewModel.errorMessage.isEmpty() || peopleViewModel.errorMessage.isNotEmpty())){
                Box(
                    modifier = centerModifier,
                    contentAlignment = Alignment.Center
                ) {
                    if (peopleViewModel.errorMessage.isNotEmpty()){
                        Text("Something broke ${peopleViewModel.errorMessage}")
                    }else
                        CircularProgressIndicator()
                }
            }else LazyColumn(state = listState){

                items(peopleViewModel.people!!.results) { person ->
                    CharacterCard(person = person)
                }

            }
            if(peopleViewModel.loadingMore){
                Box(
                    modifier = centerModifier,
                    contentAlignment = Alignment.BottomCenter
                ) {
                    CircularProgressIndicator()
                }
            }
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