package az.lahza.iamrich.service

import az.lahza.iamrich.model.Crypto
import az.lahza.iamrich.model.CryptoList
import retrofit2.http.GET

interface CryptoApi {

    @GET("cryptolist.json")
    suspend fun getCryptoList() : CryptoList

    @GET("crypto.json")
    suspend fun getCrypto(): Crypto
}