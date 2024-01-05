package cz.dpalecek.catfact.core.design.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.dpalecek.catfact.core.design.domain.FlashMessage
import cz.dpalecek.catfact.core.design.indication.FlashMessageCAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme

@Composable
fun ScaffoldCAT(
    flashMessage: FlashMessage? = null,
    footer: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CatFactTheme.colors.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                content()
            }
            Box {
                footer()
            }
        }
        flashMessage?.let {
            FlashMessageCAT(flashMessage = it)
        }
    }
}
