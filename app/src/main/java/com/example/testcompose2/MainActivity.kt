package com.example.testcompose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose2.ui.theme.TestCompose2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCompose2Theme {
                // A surface container using the 'background' color from the theme

                var state by remember {
                    mutableStateOf(false)
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(state) {
                        state = !state
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(isTextVisible: Boolean, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .wrapContentSize()
            .defaultMinSize(48.dp, 48.dp),
        shape = if(isTextVisible) RoundedCornerShape(24.dp) else CircleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xED0381FE)
        ),
        border = BorderStroke(0.5.dp, Color.White),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 3.dp),
        contentPadding = if(isTextVisible) PaddingValues(horizontal = 16.dp) else PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = isTextVisible) {
                Text(modifier = Modifier.padding(end = 8.dp), text = "TV remote", color = Color.White, fontSize = 13.sp)
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_remote_entry),
                tint = Color.White,
                contentDescription = ""
            )
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