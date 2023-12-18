package az.lahza.iamrich.model

class Crypto : ArrayList<CryptoItem>()

data class CryptoItem(
    val id: String,
    val logo_url: String,
    val name: String
)
