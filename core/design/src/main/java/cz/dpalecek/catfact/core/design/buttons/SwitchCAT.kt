package cz.dpalecek.catfact.core.design.buttons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun SwitchCAT(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    label: String? = null,
    onChanged: (Boolean) -> Unit,
) {
    Row(modifier = modifier) {
        label?.let {
            TextBody1CAT(
                text = it,
                color = CatFactTheme.colors.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(CatFactTheme.spaces.padding2))
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = CatFactTheme.colors.primary,
                uncheckedThumbColor = CatFactTheme.colors.onSurface,
                uncheckedTrackColor = CatFactTheme.colors.onBackground,
            ),
        )
    }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun SwitchCATPreview() {
    CatFactTheme {
        Column(
            modifier = Modifier.background(CatFactTheme.colors.background)
        ) {
            var isChecked by remember {
                mutableStateOf(true)
            }
            val label = "Label"
            SwitchCAT(
                label = label,
                isChecked = isChecked,
                onChanged = { isChecked = it },
            )
            SwitchCAT(
                label = label,
                isChecked = !isChecked,
                onChanged = { isChecked = !it },
            )
        }
    }
}
