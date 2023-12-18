package az.lahza.iamrich.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import az.lahza.iamrich.model.Crypto
import az.lahza.iamrich.model.CryptoListItem
import az.lahza.iamrich.viewmodel.CryptoListViewModel

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {


    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            //=========================Header=========================
            Text(
                text = "Category Man",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                color = Black,
                fontSize = 44.sp,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(10.dp))

            //=========================Search=========================
            SearchBox(
                hint = "Search ...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchCryptoList(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            //=========================Crypto List=========================
            CryptoList(navController = navController)
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(
                    White,
                    CircleShape
                )
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged { isHintDisplayed = it.isFocused != true && text.isEmpty() },
            textStyle = TextStyle(color = Black),
            singleLine = true,
            maxLines = 1,
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Black,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun CryptoList(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {
    val cryptoList by remember { viewModel.cryptoList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    CryptoListView(navController = navController, cryptos = cryptoList)

    Box(modifier = Modifier.fillMaxSize()){
        if (isLoading){
            CircularProgressIndicator(color = Black)
        }
        if (errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.loadCryptos()
            }
        }
    }
}

@Composable
fun CryptoListView(
    navController: NavController,
    cryptos:List<CryptoListItem>
) {
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cryptos){item: CryptoListItem ->
            CryptoRow(navController = navController, crypto = item)
        }
    }
}

@Composable
fun CryptoRow(
    navController: NavController,
    crypto: CryptoListItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp,0.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
            .clickable {
                navController.navigate(
                    "crypto_detail_screen/${crypto.currency}/${crypto.price}"
                )
            }
    ) {
        Text(
            text = crypto.currency,
            modifier = Modifier.padding(2.dp),
            color = Color.DarkGray,
            fontSize = 48.sp,
            fontWeight = Bold
        )
        Text(
            text = crypto.price,
            modifier = Modifier.padding(2.dp),
            color = Color.Gray,
            fontSize = 32.sp,
            fontWeight = Bold
        )
    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(
            text = error,
            color = Red,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCryptoListScreen() {
    val navController = rememberNavController()


    CryptoListView(navController, listOf(
        CryptoListItem("salam","867889"),
        CryptoListItem("salam","867889"),
        CryptoListItem("salam","867889"),
        CryptoListItem("salam","867889"),
        CryptoListItem("salam","867889"),
        CryptoListItem("salam","867889"),
    ))
}
