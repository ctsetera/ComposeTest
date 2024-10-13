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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
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
                    .padding(8.dp)
            ) {
                // 画像を表示する
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                // 空白を挿入（Layout XMLでいうところのMargin Start, Margin End）
                Spacer(modifier = Modifier.width(8.dp))

                // Column -> 縦方向に整列できるよ
                Column {
                    Greeting(
                        name = "Android",
                        // modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name = "iOS",
                        // modifier = Modifier.padding(innerPadding)
                    )
                    // 空白を挿入（Layout XMLでいうところのMargin Top, Margin Bottom）
                    Spacer(modifier = Modifier.height(8.dp))
                    Greeting(
                        name = "Windows",
                        // modifier = Modifier.padding(innerPadding)
                    )
                    Greeting(
                        name = "macOS",
                        // modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Box { Text("Top App Bar") }
        },
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = colorResource(R.color.teal_700))
    )
}

// Compose可能な関数は、関数名に`Composable`アノテーションを追加するだけで作成できます
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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
fun GreetingPreview() {
    ComposeTestTheme {
        Greeting("Preview")
    }
}