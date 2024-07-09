package com.example.tibiatracker.activity.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    val actionError = MutableLiveData<String>()
    val AccountResponse = MutableLiveData<AccountResponse>()


    fun getAccount(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = mainRepository.getAccount(id)
            if (request.isSuccessful) {
                Log.e(TAG, "getAccount: "+request.body().toString())
                AccountResponse.postValue((request.body()!!))
            }else{
                //tratar falha
            }
        }
    }
    fun postAccount(conta: AccountResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = mainRepository.postAccount(conta)
            if (request.isSuccessful) {
                Log.e(TAG, "postAccount: "+request.body().toString())
                AccountResponse.postValue((request.body()!!))
            }else{
                //tratar falha
            }
        }
    }


    class MainViewModelFactory(
        private val mainRepository: MainRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(mainRepository) as T
        }
    }


}