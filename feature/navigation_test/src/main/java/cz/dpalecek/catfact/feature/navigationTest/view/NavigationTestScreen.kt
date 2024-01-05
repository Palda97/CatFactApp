package cz.dpalecek.catfact.feature.navigationTest.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.buttons.ButtonCAT
import cz.dpalecek.catfact.core.design.layout.ScaffoldCAT
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme
import cz.dpalecek.catfact.feature.navigationTest.viewmodel.NavigationTestButtonState
import cz.dpalecek.catfact.feature.navigationTest.viewmodel.NavigationTestViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun NavigationTestScreen(
    viewModel: NavigationTestViewModel = getViewModel()
) {
    val screenName by viewModel.screenName.collectAsState()
    val buttonStates by viewModel.navigateNextButtonStates.collectAsState()

    NavigationTestScreen(
        screenName = screenName,
        buttonStates = buttonStates,
    )
}

@Composable
private fun NavigationTestScreen(
    screenName: String,
    buttonStates: List<NavigationTestButtonState>,
) {
    ScaffoldCAT {
        Column(
            modifier = Modifier
                .padding(CatFactTheme.spaces.padding3)
        ) {
            TextBody1CAT(
                text = screenName,
                color = CatFactTheme.colors.onBackground,
            )
            Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding3))
            Row {
                buttonStates.forEach {
                    NavigationTestButton(state = it)
                }
            }
        }
    }
}

@Composable
private fun NavigationTestButton(
    state: NavigationTestButtonState,
) {
    ButtonCAT(
        text = state.label,
        onClick = state.action,
        modifier = Modifier
            .padding(CatFactTheme.spaces.padding1)
    )
}

@Preview
@Composable
internal fun NavigationTestScreenPreview() {
    NavigationTestScreen(
        screenName = "Test Screen 1",
        buttonStates = listOf(
            "Test screen 2".previewButton,
            "Test screen 3".previewButton,
        ),
    )
}

private val String.previewButton: NavigationTestButtonState
    get() = NavigationTestButtonState(this) {}
