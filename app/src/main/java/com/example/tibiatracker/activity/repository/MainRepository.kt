package com.example.tibiatracker.activity.repository

import com.example.tibiatracker.activity.model.AccountResponse
import retrofit2.Response

interface MainRepository {
    suspend fun getAccount(id: String): Response<AccountResponse>
//
    suspend fun postAccount(request: AccountResponse): Response<AccountResponse>
//
//    suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse>

}