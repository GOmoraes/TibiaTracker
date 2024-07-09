package com.example.tibiatracker.activity.repository

import com.example.tibiatracker.activity.model.CharPorNomeResponse
import retrofit2.Response

interface TibiaDataRepository {
    suspend fun getCharPorNome(request: String): Response<CharPorNomeResponse>
//
//    suspend fun postCadastro(request: RequalificacaoCadastroRequest): Response<CadastroRequalificacaoResponse>
//
//    suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse>

}