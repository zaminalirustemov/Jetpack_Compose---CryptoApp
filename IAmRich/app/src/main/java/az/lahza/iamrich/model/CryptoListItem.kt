package az.lahza.iamrich.model

data class CryptoList(
    val cryptos: List<CryptoListItem>
)

data class CryptoListItem(
    val currency: String,
    val price: String
)