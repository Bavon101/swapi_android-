package com.example.swapi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapi.api.models.People
import com.example.swapi.api.services.PeopleApi
import com.example.swapi.api.services.SearchApi
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel(){
    private val _people = mutableStateOf<People?>(null)
    private  var _loadingMore: Boolean by mutableStateOf(false)
    val loadingMore: Boolean get() = _loadingMore
    var errorMessage: String by mutableStateOf("")
    val people: People? get() = _people.value

    // get the people's data
    fun getStarPeople(getMore: Boolean = false){
        viewModelScope.launch {
            if(getMore){
                _loadingMore = true
            }
            val peopleSevice = PeopleApi.getInstance()
            try {
                errorMessage = ""
                handlePeopleData((peopleSevice.getPeople(null,_currentPage.toString())))

            }catch (e: Exception){
                if(!getMore){
                    errorMessage = e.message.toString()
                }
                _loadingMore = false

            }
        }
    }

    private val _searchPeople = mutableStateOf<People?>(null)
    private var _searching: Boolean by mutableStateOf(false)
    val searching : Boolean get() = _searching
    var searchErrorMessage: String by mutableStateOf("")
    val searchPeople: People? get() = _searchPeople.value

    //    add search query logic
    private var _query: String by mutableStateOf("")
    val query : String get() = _query

    fun  updateQueryValue (q: String){
        _query = q
    }
    fun searchStarPeople(){
        viewModelScope.launch {
            val searchApi = SearchApi.getInstance()
            try{
                searchErrorMessage = ""
                _searching = true
                _searchPeople.value = (searchApi.getPeople(query))
                _searching = false
            }catch (e: Exception){
                searchErrorMessage = e.message.toString()
                _searching = false
            }
        }
    }

    // handle nextPage data
    private var _currentPage: Int by mutableStateOf(1)
    private fun handlePeopleData (people: People){
        if(_people.value == null){
            _people.value = people
        }else{
            _people.value!!.results.addAll(
                people.results
            )
            _loadingMore = false
        }
        _currentPage += 1
    }
}