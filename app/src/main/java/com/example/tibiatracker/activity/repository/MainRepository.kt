package com.example.tibiatracker.activity.repository

import com.example.tibiatracker.activity.model.AccountResponse
import retrofit2.Response

interface MainRepository {
    suspend fun getAccount(id: Int): Response<AccountResponse>
//
//    suspend fun postCadastro(request: RequalificacaoCadastroRequest): Response<CadastroRequalificacaoResponse>
//
//    suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse>

}