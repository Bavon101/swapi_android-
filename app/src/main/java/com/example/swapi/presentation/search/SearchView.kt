package com.example.swapi.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.swapi.presentation.composables.CharacterCard
import com.example.swapi.presentation.search.ui.theme.SWAPITheme
import com.example.swapi.viewmodels.PeopleViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(navController: NavController?, peopleViewModel: PeopleViewModel?) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val centerModifier = Modifier
        .padding(12.dp)
        .fillMaxSize()
    Scaffold(topBar = {
        Column() {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                elevation = 8.dp,
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    TextField(
                        singleLine =true,
                        modifier = Modifier
                            .fillMaxWidth(.85f)
                        ,
                        value = peopleViewModel!!.query,
                        onValueChange = {
                            peopleViewModel.updateQueryValue(it)
                        },

                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = "")
                        },

                        label = {
                            Text("Search")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                peopleViewModel.searchStarPeople()
                                keyboardController?.hide()
                            }
                        )


                    )
                    IconButton(onClick = {
                        navController?.popBackStack("search", inclusive = true )
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }


                }
            }

        }
    },
        content = {

            // show user search prompt as per the status

            if(peopleViewModel!!.query.isEmpty()){
                Box( modifier = centerModifier,
                    contentAlignment = Alignment.Center) {
                    Text("May the force be with you as you search.")
                }
            }else if(peopleViewModel.searching){
                Box( centerModifier,
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else if(peopleViewModel.searchErrorMessage.isNotEmpty()){
                Box(
                    modifier = centerModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text("Something broke \n${peopleViewModel.searchErrorMessage}")
                }
            }

            else if( peopleViewModel.searchPeople != null && peopleViewModel.searchPeople!!.results.isEmpty()){
                Box( centerModifier,
                    contentAlignment = Alignment.Center) {
                    Text("We searched all available planets,\nthe searched user does not exist.")
                }
            }
            else if(peopleViewModel.searchPeople != null){
                LazyColumn(){
                    items(peopleViewModel.searchPeople!!.results){ person ->
                        CharacterCard(person = person)
                    }

                }
            }

            else{
                Box(centerModifier,
                    contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row() {
                            Text("Press ")
                            Icon(Icons.Filled.Search, contentDescription = "search_icon")
                            Text(" on keyboard to search ")
                        }
                        Text(text = "May the force protect you")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SWAPITheme {
        SearchView(null,null)
    }
}