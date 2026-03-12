package ru.peajack.velocity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.peajack.velocity.ui.theme.Gradient1
import ru.peajack.velocity.ui.theme.Gradient2
import ru.peajack.velocity.ui.theme.Green69
import ru.peajack.velocity.ui.theme.VelocityTheme
import ru.peajack.velocity.ui.theme.surfaceContainerDark
import ru.peajack.velocity.ui.theme.surfaceContainerLight
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VelocityTheme {
                VelocityApp()
            }
        }
    }
}

@Composable
fun VelocityApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.ROUTES) }
    val navSuiteColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = Green69
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                    colors = navSuiteColors,
                )
            }
        }
    ) {
        when (currentDestination) {
            AppDestinations.ROUTES -> RoutesDestination()
            AppDestinations.MAP -> MapDestination()
            AppDestinations.GUIDES -> GuidesDestination()
            AppDestinations.PROFILE -> ProfileDestination()
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector
) {
    ROUTES("Маршруты", Icons.Default.Search),
    MAP("Карта", Icons.Default.Place),
    GUIDES("Гайды", Icons.Default.Build),
    PROFILE("Профиль", Icons.Default.AccountCircle),
}

fun GetWeather(): String {
    return "bro idk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesDestination() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            var isSearch by remember { mutableStateOf(false) }
            var value by remember { mutableStateOf("") }

            Crossfade(
                modifier = Modifier.animateContentSize(),
                targetState = isSearch,
                label = "Поиск маршрутов..."
            ) { target ->
                if (!target) {
                    TopAppBar(
                        title = {
                            Text(
                                "ВелоСити",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.background(
                            brush = Gradient1
                        ),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        actions = { IconButtonX(Icons.Filled.Search) { isSearch = !isSearch } },
                        scrollBehavior = scrollBehavior,
                    )
                } else {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                val height =
                                    placeable.height * (1 - scrollBehavior.state.collapsedFraction)
                                layout(placeable.width, height.roundToInt()) {
                                    placeable.place(0, 0)
                                }
                            },
                        value = value,
                        placeholder = { Text("Поиск маршрутов...") },
                        onValueChange = { value = it },
                        leadingIcon = {
                            IconButtonX(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                MaterialTheme.colorScheme.onSurface
                            ) {
                                isSearch = !isSearch
                            }
                        },
                        trailingIcon = if (value.isNotBlank()) {
                            { IconButtonX(Icons.Filled.Close) { value = "" } }
                        } else {
                            null
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Погода сегодня", style = MaterialTheme.typography.headlineLarge)
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Gradient2
                        )
                ) {
                    Text(
                        text = "Описание погоды",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterStart),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = "-16 C",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterEnd),
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White
                    )
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Популярные маршруты", style = MaterialTheme.typography.headlineLarge)
            }
            RouteCard(
                "По городу",
                "Разумный маршрут для городской прогулки",
                16.4f,
                "3 часа",
                12,
                "Простой",
                4.8f,
                243,
                R.drawable.tomsk
            )
            RouteCard(
                "Буревестник",
                "Поездка по лесопарку",
                13.2f,
                "2,5 часа",
                24,
                "Средний",
                4.9f,
                176,
                R.drawable.burevestnik
            )
            RouteCard(
                "Буревестник",
                "Поездка по лесопарку",
                13.2f,
                "2,5 часа",
                24,
                "Средний",
                4.9f,
                176,
                R.drawable.burevestnik
            )
        }
    }
}

@Composable
fun RouteCard(
    name: String,
    description: String,
    length: Float,
    time: String,
    heightAmplitude: Number,
    hardness: String,
    ratio: Float,
    comments: Number,
    image: Int
) {
    Column {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "none",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = name,
                modifier = Modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = description,
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp)
            )
        }
    }
}

@Composable
fun InfoCard(quantity: String, what: String, icon: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSystemInDarkTheme()) surfaceContainerDark else surfaceContainerLight
        )
    ) {
        Icon(
            painter = painterResource(id = icon),
            "icon",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = quantity,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = what,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
fun GuideCard(
    name: String,
    id: Int,
    tag: String,
    time: String,
    image: Int
) {
    Column {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "none",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            SuggestionChip(
                onClick = {},
                label = { Text(tag) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = name,
                modifier = Modifier
                    .padding(8.dp, 0.dp, 8.dp, 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun IconButtonX(imageVector: ImageVector, color: Color = Color.White, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = color
        )
    }
}

@Composable
fun MapDestination() {
    Text("Здесь будет карта")
}

@Composable
fun FilterChip(tag: String) {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text(tag)
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun GuidesDestination() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            Text(
                text = "Гайды и советы",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Полезная информация для велосипедистов",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                FilterChip("Обслуживание")
                FilterChip("Безопасность")
                FilterChip("Снаряжение")
                FilterChip("Советы")
            }
            GuideCard(
                "Базовое обслуживание велосипеда",
                1,
                "Обслуживание",
                "5 мин.",
                R.drawable.cycle_service
            )
            GuideCard("Безопасность в дороге", 2, "Безопасность", "4 мин.", R.drawable.safe_road)
            GuideCard("Как выбрать велосипед", 3, "Снаряжение", "6 мин.", R.drawable.cycle_bying)
            GuideCard("Советы для дальних поездок", 4, "Советы", "5 мин.", R.drawable.far_ride)
        }
    }
}

@Composable
fun ProfileDestination() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(brush = Gradient1)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "none",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Велосипедист",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Text(
                        text = "cyclistPeajack@ya.ru",
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoCard("342", "пройдено км", R.drawable.person_biking_solid_full)
                InfoCard("12", "маршрутов", R.drawable.route_solid_full)
                InfoCard("28", "часов в пути", R.drawable.clock_solid_full)
            }
        }
    }
}