package com.example.tibiatracker.activity.service

import com.example.tibiatracker.activity.model.CharPorNomeResponse
import com.example.tibiatracker.activity.utils.UrlApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {
    @GET(UrlApi.CHAR_POR_NOME)
    suspend fun getCharPorNome(@Path("name") request: String): Response<CharPorNomeResponse>
//
//    @POST(UrlApi.REQUALIFICACAO_CADASTRO)
//    suspend fun postCadastro(@Body request: RequalificacaoCadastroRequest): Response<CadastroRequalificacaoResponse>
//
//    @POST(UrlApi.REQUALIFICACAO_BUSCA_NOME)
//    suspend fun getRequalificacaoNome(@Body request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse>
}