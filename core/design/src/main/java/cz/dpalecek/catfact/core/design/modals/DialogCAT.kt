package cz.dpalecek.catfact.core.design.modals

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import cz.dpalecek.catfact.core.design.buttons.ButtonCAT
import cz.dpalecek.catfact.core.design.buttons.TextButtonCAT
import cz.dpalecek.catfact.core.design.layout.CardCAT
import cz.dpalecek.catfact.core.design.layout.ScaffoldCAT
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.text.TextTitle1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun DialogCAT(
    isVisible: Boolean,
    onClose: () -> Unit,
    title: String,
    body: String,
    dismissButtonLabel: String,
    dismissButtonOnClick: () -> Unit,
    confirmButtonLabel: String,
    confirmButtonOnClick: () -> Unit,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onClose
        ) {
            CardCAT {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = CatFactTheme.spaces.padding1,
                            vertical = CatFactTheme.spaces.padding1_5,
                        )
                ) {
                    TextTitle1CAT(text = title, color = CatFactTheme.colors.onBackground)
                    Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding2))
                    TextBody1CAT(text = body, color = CatFactTheme.colors.onBackground)
                    Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding3))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButtonCAT(
                            text = dismissButtonLabel,
                            onClick = dismissButtonOnClick,
                        )
                        Spacer(modifier = Modifier.width(CatFactTheme.spaces.padding3))
                        ButtonCAT(
                            text = confirmButtonLabel,
                            onClick = confirmButtonOnClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun DialogCATPreview() {
    CatFactTheme {
        ScaffoldCAT {}
        DialogCAT(
            isVisible = true,
            onClose = {},
            title = "Title",
            body = "Body",
            dismissButtonLabel = "Dismiss",
            dismissButtonOnClick = {},
            confirmButtonLabel = "Confirm",
            confirmButtonOnClick = {},
        )
    }
}
