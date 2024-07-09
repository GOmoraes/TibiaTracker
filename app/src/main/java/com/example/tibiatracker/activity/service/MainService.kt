package com.example.tibiatracker.activity.service

import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.model.CharPorNomeResponse
import com.example.tibiatracker.activity.utils.UrlApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainService {
    @GET(UrlApi.ACCOUNT_BY_ID)
    suspend fun getAccount(@Path("id") id: String): Response<AccountResponse>

    @GET(UrlApi.ACCOUNT_BY_EMAIL)
    suspend fun getAccountByEmail(@Path("emailReq") emailReq: String): Response<AccountResponse>

//
    @POST(UrlApi.ADD_ACCOUNT)
    suspend fun postAccount(@Body request: AccountResponse): Response<AccountResponse>
//
//    @POST(UrlApi.REQUALIFICACAO_BUSCA_NOME)
//    suspend fun getRequalificacaoNome(@Body request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse>
}