package com.example.tibiatracker.activity.repository
import com.example.tibiatracker.activity.model.CharPorNomeResponse
import com.example.tibiatracker.activity.service.TibiaDataService
import retrofit2.Response

class TibiaDataRepositoryImpl(
    private val tibiaDataService: TibiaDataService
): TibiaDataRepository {

    override suspend fun getCharPorNome(request: String): Response<CharPorNomeResponse> = tibiaDataService.getCharPorNome(request)
//
//    override suspend fun postCadastro(request: RequalificacaoCadastroRequest): Response<CadastroRequalificacaoResponse> = requalificacaoService.postCadastro(request)
//    override suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse> = requalificacaoService.getRequalificacaoNome(request)

}