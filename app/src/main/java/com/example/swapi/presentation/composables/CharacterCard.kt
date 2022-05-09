package com.example.swapi.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swapi.api.models.Person

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterCard(person: Person?) {

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = person!!.name)
            },
            text = {
                Column() {
                    PersonDataCard(title = "Height", value = person!!.height )
                    PersonDataCard(title = "Mass", value = person.mass )
                    PersonDataCard(title = "Hair Color", value = person.hair_color )
                    PersonDataCard(title = "Eye Color", value = person.eye_color )
                    PersonDataCard(title = "YOB", value = person.birth_year )
                    PersonDataCard(title = "Gender", value = person.gender)
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

    Card(shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(8.dp),
        onClick = {openDialog.value = true }
    ) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = person!!.name)
        }
    }

}
