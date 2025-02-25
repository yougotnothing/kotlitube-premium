package com.example.youtubemusicpremium.ui.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.chaquo.python.Python
import com.example.youtubemusicpremium.data.api.HomeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _homeUiState = mutableStateOf<List<HomeResponse>>(emptyList())
    val homeUiState: State<List<HomeResponse>> = _homeUiState

    init {
        getHomeContent()
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
    val listenAgainScrollState = rememberScrollState()

    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            if (viewModel.homeUiState.value.isNotEmpty()) {
                Text(
                    text = viewModel.homeUiState.value[0].title,
                    color = Color.White,
                    fontSize = TextUnit(value = 20f, type = TextUnitType(12))
                )
                Row(
                    modifier = Modifier.horizontalScroll(listenAgainScrollState),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    viewModel.homeUiState.value[0].contents.forEach { content ->
                        content.thumbnails
                            .filter { thumbnail -> thumbnail.width < 500 }
                            .forEach { thumbnail ->
                            AsyncImage(
                                model = thumbnail.url,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun pxToDp(px: Int): Float {
    return (px) / Resources.getSystem().displayMetrics.density
}