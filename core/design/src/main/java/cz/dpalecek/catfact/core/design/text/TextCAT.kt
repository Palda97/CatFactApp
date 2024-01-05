package cz.dpalecek.catfact.core.design.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun TextHeadline1CAT(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        style = CatFactTheme.typography.headline1,
        textAlign = TextAlign.Justify,
        modifier = modifier,
    )
}

@Composable
fun TextHeadline2CAT(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        style = CatFactTheme.typography.headline2,
        textAlign = TextAlign.Justify,
        modifier = modifier,
    )
}

@Composable
fun TextTitle1CAT(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        style = CatFactTheme.typography.title1,
        modifier = modifier,
    )
}

@Composable
fun TextBody1CAT(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        style = CatFactTheme.typography.body1,
        textAlign = TextAlign.Justify,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
internal fun TextCATPreview() {
    CatFactTheme {
        Column(
            modifier = Modifier.background(CatFactTheme.colors.background)
        ) {
            TextHeadline1CAT(
                text = "Headline 1",
                color = CatFactTheme.colors.onBackground
            )
            TextHeadline2CAT(
                text = "Headline 1",
                color = CatFactTheme.colors.onBackground
            )
            TextBody1CAT(
                text = "Body 1",
                color = CatFactTheme.colors.onBackground
            )
        }
    }
}
