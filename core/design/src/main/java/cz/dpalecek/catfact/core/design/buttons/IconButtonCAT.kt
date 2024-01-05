package cz.dpalecek.catfact.core.design.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.R
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun IconButtonCAT(
    @DrawableRes icon: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = CatFactTheme.colors.secondary,
            contentColor = CatFactTheme.colors.onSecondary,
        ),
        contentPadding = PaddingValues(),
        enabled = isEnabled,
        modifier = modifier.size(CatFactTheme.spaces.padding6)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun IconButtonCATPreview() {
    CatFactTheme {
        IconButtonCAT(
            icon = R.drawable.outline_image_24,
            contentDescription = "",
            onClick = {},
            modifier = Modifier.padding(CatFactTheme.spaces.padding1)
        )
    }
}
