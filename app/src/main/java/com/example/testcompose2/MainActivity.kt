package com.example.testcompose2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose2.ui.theme.TestCompose2Theme
import com.example.testcompose2.viewmodel.ViewModel1
import com.example.testcompose2.viewmodel.ViewModel2
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Oval
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.nikhilchaudhari.library.neumorphic

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestCompose2Theme {
                // A surface container using the 'background' color from the theme

                var state by remember {
                    mutableStateOf("60")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        TextFieldSearchBarSample()
//                        UnitTextField(value = state, onValueChange = { state = it }, unit = "cm")
//                        SearchBarSample()
//                    }

                    val navController = rememberNavController()

                    val viewModel1: ViewModel1 = hiltViewModel()
                    val viewModel2: ViewModel2 = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "screen1",
                    ) {

                        composable("screen1") {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                val interactionSource1 = remember { MutableInteractionSource() }
                                val interactions =
                                    remember { mutableStateListOf<Interaction>() }
                                var isPressed by remember {
                                    mutableStateOf(false)
                                }
//                                LaunchedEffect(key1 = isPressed) {
//                                    Log.d("interactionSource1", "isPressed: $isPressed")
//                                }
//                                LaunchedEffect(interactionSource1) {
//                                    interactionSource1.interactions.collect { interaction ->
//                                        when (interaction) {
//                                            is PressInteraction.Press -> {
//                                                isPressed = true
//                                                Log.d(
//                                                    "interactionSource1",
//                                                    "Press: $isPressed"
//                                                )
//                                                interactions.add(interaction)
//                                            }
//
//                                            is PressInteraction.Release -> {
//                                                isPressed = false
//                                                Log.d(
//                                                    "interactionSource1",
//                                                    "Release: $isPressed"
//                                                )
//                                                interactions.remove(interaction.press)
//                                            }
//
//                                            is PressInteraction.Cancel -> {
//                                                isPressed = false
//                                                Log.d(
//                                                    "interactionSource1",
//                                                    "Cancel: $isPressed"
//                                                )
//                                                interactions.remove(interaction.press)
//                                            }
//                                        }
//                                    }
//                                }

                                Card(modifier = Modifier
                                    .size(100.dp)
                                    .pointerInput(Unit) {
                                        detectTapAndPress(
                                            onPress = {
                                                isPressed = true
                                                Log.d(
                                                    "detectTapAndPress",
                                                    "onPress"
                                                )
                                            },
                                            onTap = {
                                                isPressed = false
                                                Log.d(
                                                    "detectTapAndPress",
                                                    "onTap"
                                                )
                                            },
                                            onCancel = {
                                                isPressed = false
                                                Log.d(
                                                    "detectTapAndPress",
                                                    "onCancel"
                                                )
                                            }
                                        )
                                    }
                                    .neu(
                                        lightShadowColor = AppColors.lightShadow(),
                                        darkShadowColor = AppColors.darkShadow(),
                                        shadowElevation = 6.dp,
                                        lightSource = LightSource.LEFT_TOP,
                                        shape = if (isPressed) Pressed(RoundedCorner(24.dp)) else Flat(RoundedCorner(24.dp)),
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                                    shape = RoundedCornerShape(24.dp)
                                ) {

                                }

                                Button(
                                    modifier = Modifier.pointerInput(Unit) {
                                              detectTapAndPress(
                                                  onPress = {
                                                      isPressed = true
                                                      Log.d(
                                                          "detectTapAndPress",
                                                          "onPress"
                                                      )
                                                  },
                                                  onTap = {
                                                      isPressed = false
                                                      Log.d(
                                                          "detectTapAndPress",
                                                          "onTap"
                                                      )
                                                  },
                                                  onCancel = {
                                                      isPressed = false
                                                      Log.d(
                                                          "detectTapAndPress",
                                                          "onCancel"
                                                      )
                                                  }
                                              )
                                    },
                                    onClick = {}
                                ) {
                                    Text(text = "Click me $isPressed")
                                }
                            }
                        }

                        composable("screen2") {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(onClick = {
                                    Log.d(
                                        "testViewModel",
                                        "hash code2:, ${viewModel1.hashCode()}"
                                    )
                                }) {
                                    Text(text = "Click me")
                                }
                                UnitTextField(
                                    value = state,
                                    onValueChange = { state = it },
                                    unit = "cm"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UnitTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    textStyle: TextStyle = TextStyle(
        fontSize = 70.sp,
        color = MaterialTheme.colorScheme.primary
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number
    ),
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = 1,
            modifier = Modifier
                .background(Color.Gray)
                .weight(1f, false)
                .width(IntrinsicSize.Max)
                .alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = unit, modifier = Modifier.alignBy(LastBaseline))
    }
}

@Composable
fun Greeting(isTextVisible: Boolean, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .wrapContentSize()
            .defaultMinSize(48.dp, 48.dp),
        shape = if (isTextVisible) RoundedCornerShape(24.dp) else CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xED0381FE)
        ),
        border = BorderStroke(0.5.dp, Color.White),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 3.dp),
        contentPadding = if (isTextVisible) PaddingValues(horizontal = 16.dp) else PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = isTextVisible) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "TV remote",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_remote_entry),
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearchBarSample() {

    var query: String by rememberSaveable { mutableStateOf("") }
    val showClearIcon by rememberSaveable(query) { mutableStateOf(query.isNotEmpty()) }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            query = onQueryChanged
            if (onQueryChanged.isNotEmpty()) {
                // Perform search
            }
        },
        leadingIcon = {
            Box(Modifier.offset(x = 4.dp)) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Search icon"
                    )
                }
            }
        },
        trailingIcon = {
            Box(Modifier.offset(x = (-4).dp)) {
                if (showClearIcon) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Clear icon"
                        )
                    }
                } else {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_voice),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "More icon"
                        )
                    }
                }
            }
        },
        maxLines = 1,
        placeholder = { Text(text = "Search here") },
        textStyle = MaterialTheme.typography.labelLarge,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { /* Perform search */ }),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text("Hinted search text") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Clear icon"
                )
            }
        },
    ) {
    }
}

suspend fun PointerInputScope.detectTapAndPress(
    onPress: (Offset) -> Unit,
    onTap: ((Offset) -> Unit)? = null,
    onCancel: () -> Unit
) {
    coroutineScope {
        awaitEachGesture {
            val down = awaitFirstDown().also { if (it.pressed != it.previousPressed) it.consume() }
            launch { onPress(down.position) }

            val up = waitForUpOrCancellation()

            if (up != null) {
                if (up.pressed != up.previousPressed) up.consume()
                onTap?.invoke(up.position)
            } else onCancel()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestCompose2Theme {
        Greeting(false) {}
    }
}

object AppColors {
    val Purple200 = Color(0xFFBB86FC)
    val Purple500 = Color(0xFF6200EE)

    object Light {
        val Background = Color(0xFFDCDCDC)
        val LightShadow = Color(0xFFFFFFFF)
        val DarkShadow = Color(0xFFA8B5C7)
        val TextColor = Color.Black
    }

    object Dark {
        val Background = Color(0xFF303234)
        val LightShadow = Color(0x66494949)
        val DarkShadow = Color(0x66000000)
        val TextColor = Color.White
    }

    @Composable
    fun lightShadow() = if (!isSystemInDarkTheme()) Light.LightShadow else Dark.LightShadow

    @Composable
    fun darkShadow() = if (isSystemInDarkTheme()) Light.DarkShadow else Dark.DarkShadow
}