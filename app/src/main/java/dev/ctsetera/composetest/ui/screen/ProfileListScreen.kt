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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ctsetera.composetest.R
import dev.ctsetera.composetest.ui.theme.ComposeTestTheme

data class ProfileItem(
    val id: String,
    val name: String,
    val description: String,
    val icon: Painter,
)

@Composable
fun ProfileListScreen() {
    val profileList = listOf(
        ProfileItem(
            id = "@potofu",
            name = "霜月ポトフ",
            description = buildString {
                append("ニコニコ生放送でお絵かき配信をしているpepepeさんのオリジナルキャラクター。")
                append("pepepeさんがフォトショップのフリーズにも負けずに書き上げたpepepeさんの妹である。")
                append("ポトフちゃんはハーフだがpepepeさんはハーフではない。ふしぎ！")
                append("おとなしくてSな照れ屋でひきこもりぎみで、家でお絵かき配信をしたり、同人サークルで稼いでいるらしい。")
                append("ちなみにぱんつはくまさんぱんつ。")
                append("最近お父さんが保護してきたワトラちゃんという友達が増えた。やったね！ポトフちゃん！")
                append("配信ではポトフちゃんが絵を描き、ワトラちゃんはモデル、pepepeさんがおしゃべりを担当との事。")
                append("お父さん（フランス人）も一緒に住んでおり、リストラ朝スーツを着て出かけて公園のベンチでボーっとして夜に帰ってくる仕事をしている。なお、お父さんはシャイで家族間の会話もメールで行っている。")
                append("お母さん（日本人）は物心ついたときには家におらず、生活費はかかさず送られてきている模様。")
                append("pepepeさん曰くどんどん描いてもいいのよ?との事。れっつどろーいんぐ！")
            },
            icon = painterResource(R.drawable.icon_potofu),
        ),
        ProfileItem(
            id = "@watora",
            name = "葉月ワトラ",
            description = buildString {
                append("pepepe（絵師）さんのオリジナルキャラクター。")
                append("設定：")
                append("赤髪のツインテール。アホ毛が生えていて、Ｗ形の髪飾りを付けている。")
                append("性格はぶっきらぼうな感じ。父は鳥取、母は島根県出身。")
                append("リアル草食系。裸パーカーのチャックは壊れている。　一人称はワタイ。")
                append("生放送でワロタをワトラと打ち間違えたコメントがどんどん広がり、とうとうキャラクターになってしまった。ワトラｗｗｗｗｗｗ")
            },
            icon = painterResource(R.drawable.icon_watora)
        ),
    )

    ProfileList(profileList = profileList)
}

// Profile List
@Composable
fun ProfileList(profileList: List<ProfileItem>) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = profileList) { item ->
            ProfileContent(profile = item)
        }
    }
}

// Profile Content
@Composable
fun ProfileContent(
    profile: ProfileItem,
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
                Image(
                    painter = profile.icon,
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
                                text = profile.name,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Text(
                                text = profile.id,
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
                        text = profile.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileListScreenPreview() {
    ComposeTestTheme {
        ProfileListScreen()
    }
}