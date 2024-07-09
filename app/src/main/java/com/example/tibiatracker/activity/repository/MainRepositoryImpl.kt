package com.example.tibiatracker.activity.repository
import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.service.MainService
import com.example.tibiatracker.activity.model.CharPorNomeResponse
import retrofit2.Response

class MainRepositoryImpl(
    private val mainService: MainService
): MainRepository {

    override suspend fun getAccount(id: Int): Response<AccountResponse> = mainService.getAccount(id)
//
//    override suspend fun postCadastro(request: RequalificacaoCadastroRequest): Response<CadastroRequalificacaoResponse> = requalificacaoService.postCadastro(request)
//    override suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse> = requalificacaoService.getRequalificacaoNome(request)

}