package com.example.youtubemusicpremium.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.chaquo.python.Python
import com.example.youtubemusicpremium.data.api.HomeResponse
import com.example.youtubemusicpremium.data.api.SelfResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _homeUiState = mutableStateOf<List<HomeResponse>>(emptyList())
    private val _selfUiState = mutableStateOf<SelfResponse?>(null)
    val homeUiState: State<List<HomeResponse>> = _homeUiState
    val selfUiState: State<SelfResponse?> = _selfUiState

    init {
        getSelf()
        getHomeContent()
    }

    private fun getSelf() {
        viewModelScope.launch {
            try {
                val py = Python.getInstance()
                val module = py.getModule("main")
                val result = module.callAttr("get_self")
                    .toString()

                val selfType = object : TypeToken<SelfResponse>() {}.type
                _selfUiState.value = Gson().fromJson(result, selfType)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun getHomeContent() {
        viewModelScope.launch {
            try {
                val py = Python.getInstance()
                val module = py.getModule("main")
                val result = module.callAttr("get_home")
                    .toString()

                val listType = object : TypeToken<List<HomeResponse>>() {}.type
                _homeUiState.value = Gson().fromJson(result, listType)

                println(_homeUiState.value)
                println("Python test result: $result")
            } catch (e: Exception) {
                println("Ошибка запроса: ${e.message}")
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val chunkedItems = viewModel.homeUiState.value.chunked(9);

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp, top = 40.dp).background(Color.Black)) {
            Row(modifier = Modifier.padding(10.dp)) {
                AsyncImage(
                    model = viewModel.selfUiState.value?.accountPhotoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
                Column {
                    Text("Welcome back,", color = Color.White)
                    viewModel.selfUiState.value?.accountName?.let { Text(text = it, color = Color.LightGray) }
                }
            }
            chunkedItems.forEach { item -> SectionRow(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SectionRow(items: List<HomeResponse>) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (items.isNotEmpty()) {
            Text(
                text = items[0].title,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items[0].contents.forEach { content ->
                content.thumbnails.filter { thumbnail -> thumbnail.width < 500 }
                .forEach { thumbnail ->
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = thumbnail.url,
                            contentDescription = content.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                        Box(modifier = Modifier.fillMaxSize().background(
                            Brush.linearGradient(
                                0f to Color.Transparent,
                                70f to Color.Black,
                                start = Offset(50f, 0f),
                                end = Offset(50f, 300f)
                            )
                        ))
                        Text(
                            text = content.title,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp),
                            softWrap = false,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
