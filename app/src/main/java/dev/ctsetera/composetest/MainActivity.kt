package dev.ctsetera.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ctsetera.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    // ComposeTestThemeはTheme.ktで定義しているみたいだよ
    ComposeTestTheme {
        // Scaffold -> 土台
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            // Row -> 横方向に整列できるよ
            Row(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                ProfileContent()
            }
        }
    }
}

// TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Box { Text("Top App Bar") }
        },
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

// Profile Content
@Composable
fun ProfileContent(isExpanded: Boolean = false) {
    // Jetpack Composeのrememberは、コンポーザブル関数内で状態を保持するために非常に重要な関数です
    val expandedState: MutableState<Boolean> = remember { mutableStateOf(isExpanded) }

    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            // 画像を表示する
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }

        // 幅8dpの空白を挿入
        Spacer(modifier = Modifier.width(8.dp))

        // Column -> 縦方向に整列できるよ
        Column {
            Column {
                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        // 備考: Typographyは自分で設定できる 詳細はTypeを参照

                        Text(
                            text = "Your Name",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "@your_id",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        expandedState.value = !expandedState.value
                    }) {
                        Text(text = if (expandedState.value) "Show less" else "Show more")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (expandedState.value) {
                Text(
                    text = "Your description.\n".repeat(4),
                )
            }
        }
    }
}

// `@Preview`アノテーションを使用すると、Android Studio内でコンポーズ可能な関数をプレビューできます
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MainActivityContent()
}

// `@Preview`アノテーションを使用すると、Android Studio内でコンポーズ可能な関数をプレビューできます
@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    ComposeTestTheme {
        ProfileContent(isExpanded = false)
    }
}

// `@Preview`アノテーションを使用すると、Android Studio内でコンポーズ可能な関数をプレビューできます
@Preview(showBackground = true)
@Composable
fun ProfileContentPreviewExpanded() {
    ComposeTestTheme {
        ProfileContent(isExpanded = true)
    }
}