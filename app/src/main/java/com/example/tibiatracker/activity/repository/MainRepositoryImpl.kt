package com.example.tibiatracker.activity.repository
import com.example.tibiatracker.activity.model.AccountResponse
import com.example.tibiatracker.activity.service.MainService
import retrofit2.Response

class MainRepositoryImpl(
    private val mainService: MainService
): MainRepository {

    override suspend fun getAccount(id: String): Response<AccountResponse> = mainService.getAccount(id)
//
    override suspend fun postAccount(request: AccountResponse): Response<AccountResponse> = mainService.postAccount(request)
//    override suspend fun getRequalificacaoNome(request: RequalificacaoNomeRequest): Response<RequalificacaoNomeResponse> = requalificacaoService.getRequalificacaoNome(request)

}