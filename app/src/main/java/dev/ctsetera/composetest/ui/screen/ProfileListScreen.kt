package dev.ctsetera.composetest.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ctsetera.composetest.R
import dev.ctsetera.composetest.model.UserModel
import dev.ctsetera.composetest.ui.theme.ComposeTestTheme
import dev.ctsetera.composetest.viewmodel.ProfileListViewModel

@Composable
fun ProfileListScreen() {
    var isOpened by rememberSaveable { mutableStateOf(false) }

    val viewModel = viewModel<ProfileListViewModel>(factory = ProfileListViewModel.Factory)
    val userList by viewModel.userList.collectAsState()

    ProfileList(profileList = userList)

    if (!isOpened) {
        viewModel.getUserList()
    }

    isOpened = true
}

// Profile List
@Composable
fun ProfileList(profileList: List<UserModel>) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = profileList) { item ->
            ProfileContent(user = item)
        }
    }
}

// Profile Content
@Composable
fun ProfileContent(
    user: UserModel,
    isExpanded: Boolean = false,
) {
    /*
    remember の代わりに rememberSaveable を使用できます
    そうすれば、個々の状態が保存され、構成の変更（回転など）やプロセスの終了後も保持されます。
     */
    var expandedState by rememberSaveable { mutableStateOf(isExpanded) }

    // Jetpack Composeのrememberは、コンポーザブル関数内で状態を保持するために非常に重要な関数です
    // var expandedState by remember { mutableStateOf(isExpanded) }

    // Surface -> 色を決めるよ
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {

        // Row -> 横方向に整列できるよ
        Row(
            modifier = Modifier
                .padding(8.dp)
                // このコンテンツのサイズが変更されたときのアニメーションを設定する
                // 以下の場合、バネで跳ねたかのようなアニメーションとなる
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy, // バネの減衰比
                        stiffness = Spring.StiffnessMedium, // バネの硬さ
                    )
                ),
        ) {
            Column {
                // 画像を表示する
                if (user.icon == null) {
                    Image(
                        painter = painterResource(R.drawable.icon_default),
                        contentDescription = "Contact profile picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        bitmap = user.icon.asImageBitmap(),
                        contentDescription = "Contact profile picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
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
                                text = user.name,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Text(
                                text = user.id,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = { expandedState = !expandedState }) {
                            Icon(
                                imageVector = if (expandedState) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                contentDescription = if (expandedState) {
                                    stringResource(R.string.show_less)
                                } else {
                                    stringResource(R.string.show_more)
                                }
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (expandedState) {
                    Text(
                        text = user.description ?: "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileListPreview() {
    val userList: ArrayList<UserModel> = arrayListOf()
    for (i in 1..3) {
        userList.add(
            UserModel(
                uid = 0,
                id = "@dummy_user_$i",
                name = "Dummy User $i",
                description = buildString {
                    append("I'm Dummy $i!\n".repeat(4))
                    append("I'm Dummy $i!")
                },
                icon = null,
            ),
        )
    }

    ComposeTestTheme {
        ProfileList(
            profileList = userList
        )
    }
}