package cz.palda97.catfact.core.design.buttons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.palda97.catfact.core.design.text.TextBody1CAT
import cz.palda97.catfact.core.design.theme.CatFactTheme

@Composable
fun TextButtonCAT(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        TextBody1CAT(text = text, color = CatFactTheme.colors.primary)
    }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun TextButtonCATPreview() {
    CatFactTheme {
        Box(modifier = Modifier.background(CatFactTheme.colors.background)) {
            TextButtonCAT(
                text = "Text Button",
                onClick = {}
            )
        }
    }
}
