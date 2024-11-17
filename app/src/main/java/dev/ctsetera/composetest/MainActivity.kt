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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ctsetera.composetest.ui.screen.OnboardingScreen
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
    /*
    コンポーズ可能な関数では、複数の関数によって読み取られるか変更される状態は、共通の祖先に配置される必要があります。
    そのためのプロセスを状態ホイスティングと呼びます。「ホイストする」とは、「持ち上げる」「昇格させる」といった意味です。
     */
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    // ComposeTestThemeはTheme.ktで定義しているみたいだよ
    ComposeTestTheme {
        // Scaffold -> 土台
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding)
            ) {
                if (shouldShowOnboarding) {
                    OnboardingScreen(onContinueClicked = {
                        shouldShowOnboarding = false
                    })
                } else {
                    ProfileContent()
                }
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
    var expandedState by remember { mutableStateOf(isExpanded) }

    // Row -> 横方向に整列できるよ
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
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = "@your_id",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        expandedState = !expandedState
                    }) {
                        Text(text = if (expandedState) "Show less" else "Show more")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (expandedState) {
                Text(
                    text = "Your description.\n".repeat(4),
                    modifier = Modifier.fillMaxWidth(),
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