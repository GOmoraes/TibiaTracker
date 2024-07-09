package com.example.tibiatracker.activity.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tibiatracker.activity.model.CharPorNomeResponse
import com.example.tibiatracker.activity.repository.TibiaDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TibiaDataViewModel(
    private val tibiaDataRepository: TibiaDataRepository
) : ViewModel() {

    val actionError = MutableLiveData<String>()
    val CharPorNomeResponse = MutableLiveData<CharPorNomeResponse>()


    fun getCharPorNome(charNome: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = tibiaDataRepository.getCharPorNome(charNome)
            if (request.isSuccessful) {
                Log.e(TAG, "getCharPorNome: "+request.body().toString())
                CharPorNomeResponse.postValue((request.body()!!))
            }else{
                //tratar falha
            }
        }
    }


    class MainViewModelFactory(
        private val tibiaDataRepository: TibiaDataRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TibiaDataViewModel(tibiaDataRepository) as T
        }
    }


}