package az.lahza.iamrich.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.lahza.iamrich.model.CryptoListItem
import az.lahza.iamrich.repository.CryptoRepository
import az.lahza.iamrich.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadCryptos()
    }


    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }
            cryptoList.value = results
        }
    }

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getCryptoList()) {
                is Resource.Success -> {
                    Log.d("ILikeItMoveIt", "loadCryptos:1 ")
                    val cryptoItems = result.data?.cryptos?.mapIndexed { _, item ->
                        Log.d("ILikeItMoveIt", "loadCryptos: ${item.currency}")
                        CryptoListItem(
                            item.currency,
                            item.price
                        )
                    } as List<CryptoListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error -> {
                    Log.d("ILikeItMoveIt", "loadCryptos:2 ")
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    errorMessage.value = ""
                }
            }
        }
    }
}