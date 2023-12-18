package az.lahza.iamrich.viewmodel

import androidx.lifecycle.ViewModel
import az.lahza.iamrich.model.Crypto
import az.lahza.iamrich.repository.CryptoRepository
import az.lahza.iamrich.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) :ViewModel(){
    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto(id)
    }
}