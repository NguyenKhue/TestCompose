package com.example.testcompose2

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.HapticFeedbackConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose2.ui.theme.TestCompose2Theme
import com.example.testcompose2.viewmodel.ViewModel1
import com.example.testcompose2.viewmodel.ViewModel2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.CancellationException
import kotlin.math.abs

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

                LaunchedEffect(key1 = true) {
                    delay(5000)
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
                        RemoteVoiceButton(isVoiceControlSupported = isVoiceControlSupported)
                    }
                }
            }
        }
    }
}

@Composable
fun RemoteVoiceButton(
    modifier: Modifier = Modifier,
    isVoiceControlSupported: Boolean = false,
) {

    var isVoiceRecording by remember {
        mutableStateOf(false)
    }


    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.primary,
        LocalIndication provides rememberRipple()
    ) {
        Box(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .size(56.dp)
                .gestureDetector(
                    role = Role.Button,
                    onClick = {
                        Log.d("RemoteVoiceButton", "click recording $isVoiceControlSupported")
                    },
                    onHold = {
                        Log.d("RemoteVoiceButton", "start recording $isVoiceRecording $isVoiceControlSupported")
                        isVoiceRecording = true
                    },
                    onHoldRelease = {
                        Log.d("RemoteVoiceButton", "stop recording $isVoiceRecording $isVoiceControlSupported")
                        isVoiceRecording = false
                    },
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

fun Modifier.gestureDetector(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
    onHold: () -> Unit,
    onHoldRelease: () -> Unit,
) = composed {
    then(
        Modifier.gestureDetector(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = onClick,
            onHold = onHold,
            onHoldRelease = onHoldRelease,
            role = role,
            indication = LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() },
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.gestureDetector(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
    onHold: () -> Unit,
    onHoldRelease: () -> Unit
) = composed {

    var isHold by rememberSaveable {
        mutableStateOf(false)
    }

    val onClickState by rememberUpdatedState(newValue = onClick)
    val onHoldState by rememberUpdatedState(newValue = onHold)
    val onHoldReleaseState by rememberUpdatedState(newValue = onHoldRelease)

    LaunchedEffect(key1 = interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Release -> {
                    if (isHold) onHoldReleaseState()
                    isHold = false
                }

                is PressInteraction.Cancel -> {
                    if (isHold) onHoldReleaseState()
                    isHold = false
                }
            }
        }
    }

    then(
        combinedClickable(
            interactionSource = interactionSource,
            indication = indication,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = {
                if (!isHold) onClickState()
            },
            onLongClick = {
                isHold = true
                onHoldState()
            }
        )
    )
}