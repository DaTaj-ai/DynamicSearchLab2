package com.example.dynamicsearchlab2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dynamicsearchlab2.ui.theme.DynamicSearchLab2Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchBar() {

    val nameList = listOf("Tag"  , "Taj",
        "Ahmed", "Mohammed", "Ali", "Fatima", "Amina",
        "Hassan", "Khalid", "Layla", "Yasmin", "Omar",
        "Zaynab", "Huda", "Samira", "Rami", "Jamil",
        "Nadia", "Rasha", "Mona", "Yousef", "Tariq",
        "Salma", "Ibrahim", "Zayd", "Noura", "Karim",
        "Ahlam", "Sami", "Nabil", "Mariam", "Zahra"
    )
    var query_shearedFlow = remember { MutableSharedFlow<String>() }
    var observerList = remember { mutableStateOf<List<String>>(listOf()) }
    var isActive = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        query_shearedFlow.collect { q ->
            observerList.value = nameList.filter {
                it.contains(q, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = isActive.value,
                placeholder = { Text("Search") },
                onQueryChange = {
                    isActive.value = it

                    GlobalScope.launch { query_shearedFlow.emit(it) }
                },
                onSearch = { },
                active = false,
                onActiveChange = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            }
        }
    ) {

        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(observerList.value.size) {
                    Text(text = observerList.value[it] ,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}


