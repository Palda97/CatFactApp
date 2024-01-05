package cz.dpalecek.catfact.core.design.layout

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun CardCAT(
    modifier: Modifier = Modifier,
    cardAction: CardAction? = null,
    content: @Composable () -> Unit,
) {
    Card(
        backgroundColor = CatFactTheme.colors.background,
        shape = RoundedCornerShape(CatFactTheme.spaces.padding2),
        modifier = Modifier
            .animateContentSize()
            .then(modifier)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(
                        top = CatFactTheme.spaces.padding1_5,
                        bottom = CatFactTheme.spaces.padding1_5,
                        start = CatFactTheme.spaces.padding2,
                        end = if (cardAction == null) CatFactTheme.spaces.padding2 else 0.dp
                    )
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                content()
            }
            cardAction?.let {
                IconButton(
                    onClick = it.onClick,
                    modifier = Modifier
                        .padding(
                            top = CatFactTheme.spaces.padding1_5,
                            bottom = CatFactTheme.spaces.padding1_5,
                            end = CatFactTheme.spaces.padding0_5
                        )
                ) {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = it.contentDescription,
                        modifier = Modifier,
                        tint = CatFactTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

data class CardAction(
    val imageVector: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit,
)

@Preview(name = "Day")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun CardCATPreview() {
    CatFactTheme {
        Column(
            modifier = Modifier
                .background(CatFactTheme.colors.surface)
        ) {
            Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding1_5))
            CardCAT(
                modifier = Modifier.previewCardPadding()
            ) {
                PreviewText(PREVIEW_SHORT_TEXT)
            }
            CardCAT(
                modifier = Modifier.previewCardPadding()
            ) {
                PreviewText(PREVIEW_LONG_TEXT)
            }
            CardCAT(
                cardAction = PREVIEW_CARD_ACTION,
                modifier = Modifier.previewCardPadding()
            ) {
                PreviewText(PREVIEW_SHORT_TEXT)
            }
            CardCAT(
                cardAction = PREVIEW_CARD_ACTION,
                modifier = Modifier.previewCardPadding()
            ) {
                PreviewText(PREVIEW_LONG_TEXT)
            }
            Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding1_5))
        }
    }
}

private fun Modifier.previewCardPadding(): Modifier = composed {
    padding(
        horizontal = CatFactTheme.spaces.padding3,
        vertical = CatFactTheme.spaces.padding1_5,
    )
}

@Composable
private fun PreviewText(text: String) {
    TextBody1CAT(
        text = text,
        color = CatFactTheme.colors.onBackground,
    )
}

private val PREVIEW_CARD_ACTION = CardAction(
    imageVector = Icons.Default.Share,
    contentDescription = "",
    onClick = {}
)

private const val PREVIEW_SHORT_TEXT = "Short text"
private const val PREVIEW_LONG_TEXT =
    "Very, very long peace of text that for sure can not fit on a single" +
            " line, maybe if will need 3 lines, maybe 4 if I make it long enough"
