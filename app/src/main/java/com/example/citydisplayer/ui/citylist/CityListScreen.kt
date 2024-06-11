package com.example.citydisplayer.ui.citylist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citydisplayer.ui.ErrorScreen
import com.example.citydisplayer.ui.LoadingIndicator
import com.example.citydisplayer.ui.theme.CityDisplayerTheme
import com.example.citydisplayer.ui.theme.robotoFontFamily

@Composable
fun CityListModelScreen(
    navController: NavController,
    viewModel: CityListViewModel = remember{ mutableStateOf(CityListViewModel(navController))}.value) {
    // делаю через remember, чтобы при пересобирании composable не вызывался заново init

    val state by viewModel.uiState.collectAsState()
    when (state) {
        is CityListUiState.Success -> {
            OneCityList(
                (state as CityListUiState.Success).data.map {it.city},
                onClick = viewModel::gotoForecastScreen,
            )
        }
        is CityListUiState.Loading -> {
            LoadingIndicator()
        }
        is CityListUiState.Error -> {
            ErrorScreen(
                errorText = (state as CityListUiState.Error).throwable.message ?: "",
                onClick = viewModel::loadCityList
            )
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OneCityList(cityNames: List<String>, onClick: (cityId: Int) -> Unit) {
    LazyColumn {
        for (i in cityNames.indices) {
            if (i == 0 || cityNames[i - 1][0] != cityNames[i][0]) { //нет проблем от пустых строк, т к отсортировано
                stickyHeader {
                    StickyLabel(cityNames[i].substring(0..0))
                }
            }
            item {
                CityName(cityNames[i]) { onClick(i) }
            }
        }
    }
}


@Composable
fun StickyLabel(header: String) {
    Box(
        modifier = Modifier
            //.fol
            .size(56.dp, 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = header,
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CityName(city: String, onClick: () -> Unit = {}) {
    Row(
        Modifier.height(56.dp)
    ) {
        Spacer(modifier = Modifier.width((56).dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
                .height(40.dp),
            shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x00FFFFFF),
                contentColor = contentColorFor(MaterialTheme.colorScheme.background)
            ),
            contentPadding = PaddingValues(16.dp, 0.dp),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = city,
                    maxLines = 1,
                    fontSize = 16.sp,
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ItemPreview(city: String="some very long name, btw i love pancakes and chocolate", onClick: () -> Unit = {}) {
    CityDisplayerTheme {
        Row(
            Modifier.height(56.dp)
        ) {
            Spacer(modifier = Modifier.width((56).dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x00FFFFFF),
                    contentColor = contentColorFor(MaterialTheme.colorScheme.background)
                ),
                contentPadding = PaddingValues(16.dp, 0.dp),
                onClick = onClick
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = city,
                        maxLines = 1,
                        fontSize = 16.sp,
                        fontFamily = robotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF000000),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OneCityListPreview(cityNames: List<String> = List(30){i -> if (i%5==0) "stuff$i" else "i"}, onClick: (cityId: Int) -> Unit = {}) {
    LazyColumn {
        for (i in cityNames.indices) {
            if (i == 0 || cityNames[i - 1][0] != cityNames[i][0]) {
                stickyHeader {
                    StickyLabel(cityNames[i].substring(0..0))
                }
            }
            item {
                CityName(cityNames[i]) { onClick(i) }
            }
        }
    }
}

//альтернативный вариант, где лейбл на том же уровне, что и название города;
//отказался от этого варианта из-за того, что так оно немного тормозит, а прикрутить два листа к одному lazyListState не получилось
@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TwinCityLists(stuff: List<String> = List(30){i -> if (i%5==0) "stuff$i" else "i"}) {
    val scrollSt1 = rememberLazyListState()
    val scrollSt2 = rememberLazyListState()

    LaunchedEffect(scrollSt2.firstVisibleItemScrollOffset) {
        scrollSt1.scrollToItem(
            scrollSt2.firstVisibleItemIndex,
            scrollSt2.firstVisibleItemScrollOffset
        )
    }

    Box {
        LazyColumn(
            state = scrollSt1

        ) {
            for(i in stuff.indices) {
                if (i == 0 || stuff[i - 1][0] != stuff[i][0]) {
                    stickyHeader {
                        StickyLabel(stuff[i].substring(0..0))
                    }
                } else {
                    item {
                        Spacer(modifier = Modifier.height((56).dp))
                    }
                }
            }
        }

        LazyColumn(
            state = scrollSt2
        ) {
            items(stuff){
                CityName(it)
            }
        }
    }
}