package com.example.swapi.api.models

data class People (
    val count: Long,
    val next: String,
    val previous: Any? = null,
    val results: MutableList<Person>
) {

    companion object
}

data class Person (
    val name: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String
)

