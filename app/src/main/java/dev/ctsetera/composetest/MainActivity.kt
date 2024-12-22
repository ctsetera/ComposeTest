package dev.ctsetera.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import dev.ctsetera.composetest.ui.screen.OnboardingScreen
import dev.ctsetera.composetest.ui.screen.ProfileListScreen
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
fun MainActivityContent(
    isShouldShowOnboarding: Boolean = true,
) {
    /*
    コンポーズ可能な関数では、複数の関数によって読み取られるか変更される状態は、共通の祖先に配置される必要があります。
    そのためのプロセスを状態ホイスティングと呼びます。「ホイストする」とは、「持ち上げる」「昇格させる」といった意味です。

    remember の代わりに rememberSaveable を使用できます
    そうすれば、個々の状態が保存され、構成の変更（回転など）やプロセスの終了後も保持されます。
    */
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(isShouldShowOnboarding) }

    // Jetpack Composeのrememberは、コンポーザブル関数内で状態を保持するために非常に重要な関数です
    //var shouldShowOnboarding by remember { mutableStateOf(isShouldShowOnboarding) }

    // ComposeTestThemeはTheme.ktで定義しているみたいだよ
    ComposeTestTheme {
        // Scaffold -> 土台
        Scaffold(
            topBar = { TopBar() },
            floatingActionButton = {
                if (shouldShowOnboarding) {
                    //
                } else {
                    FloatingActionButton(
                        onClick = { },
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            },
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
                    ProfileListScreen()
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

@PreviewLightDark
@Composable
fun MainActivityPreviewShouldShowOnboarding() {
    MainActivityContent(isShouldShowOnboarding = true)
}

@PreviewLightDark
@Composable
fun MainActivityPreview() {
    MainActivityContent(isShouldShowOnboarding = false)
}