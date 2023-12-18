package az.lahza.iamrich.repository

import az.lahza.iamrich.model.Crypto
import az.lahza.iamrich.model.CryptoList
import az.lahza.iamrich.model.CryptoListItem
import az.lahza.iamrich.service.CryptoApi
import az.lahza.iamrich.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoApi
) {

    suspend fun getCryptoList(): Resource<CryptoList> {
        val response = try {
            CryptoList(
                listOf(
                    CryptoListItem("BBTC", "50066.45817464"),
                    CryptoListItem("AAVE3L", "0.44173183"),
                    CryptoListItem("ABL", "0.03039480"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("ALICE", "18.67500794"),
                    CryptoListItem("PIB", "0.00187843"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("SMON", "0.00017301"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("STOP", "0.00202047"),
                    CryptoListItem("XVS", "37.08737784"),
                    CryptoListItem("XVS", "37.08737784"),
                    CryptoListItem("XVS", "37.08737784"),
                    CryptoListItem("XVS", "37.08737784"),
                )
            )
//            api.getCryptoList()
        } catch (e: Exception) {
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id: String): Resource<Crypto> {
        val response = try {
            api.getCrypto()
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}