package com.example.testcompose2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.testcompose2.ui.theme.TestCompose2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestCompose2Theme {
                // A surface container using the 'background' color from the theme

                var isVoiceControlSupported by remember {
                    mutableStateOf(false)
                }

                var test = remember {
                    0
                }

                LaunchedEffect(key1 = true) {
                    delay(10000)
                    isVoiceControlSupported = !isVoiceControlSupported
                }



                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Button(onClick = { isVoiceControlSupported = !isVoiceControlSupported }) {
                            Text(text = "change support $isVoiceControlSupported")
                        }

                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .gestureDetector(
                                    onClick = {
                                        Log.d("RemoteVoiceButton", "click $isVoiceControlSupported")
                                    },
                                    role = Role.Button,
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() },
                                )
                        ) {
                            Text(text = "test modifier $isVoiceControlSupported")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.gestureDetector(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {

    LogCompositions("DebugRecomposition", "gestureDetector modifier scope")

    var count = remember {
        0
    }

    LaunchedEffect(key1 = interactionSource) {
        while (true) {
            delay(1000)
            Log.d("gestureDetector", "count: $count")
            count++
        }
    }

    this then (
        combinedClickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = {
                Log.d("gestureDetector", "click count: $count")
                onClick()
                count = 1
            }
        )
    )
}

class RecompositionCounter(var value: Int)

@Composable
inline fun LogCompositions(tag: String, msg: String) {
    val recompositionCounter = remember { RecompositionCounter(0) }

    Log.d(tag, "$msg ${recompositionCounter.value} $currentRecomposeScope")
    recompositionCounter.value++
}