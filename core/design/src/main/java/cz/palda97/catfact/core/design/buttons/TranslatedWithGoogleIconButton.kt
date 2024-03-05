package cz.palda97.catfact.core.design.buttons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cz.palda97.catfact.core.design.R
import cz.palda97.catfact.core.design.theme.CatFactTheme
import cz.palda97.catfact.core.design.utils.openWebsite

@Composable
fun TranslatedWithGoogleIconButton(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    IconButton(
        onClick = { context.openWebsite("http://translate.google.com") },
        modifier = modifier
            .height(CatFactTheme.spaces.padding4)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_translated_by_google),
            contentDescription = stringResource(id = R.string.translator_translated_with_google),
            tint = Color.Unspecified,
        )
    }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun TranslatedWithGoogleIconButtonPreview() {
    CatFactTheme {
        Box(modifier = Modifier.background(CatFactTheme.colors.background)) {
            TranslatedWithGoogleIconButton()
        }
    }
}
