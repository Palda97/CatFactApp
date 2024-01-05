package cz.dpalecek.catfact.feature.catFact.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.R
import cz.dpalecek.catfact.core.design.buttons.ButtonState
import cz.dpalecek.catfact.core.design.buttons.ButtonCAT
import cz.dpalecek.catfact.core.design.buttons.SwitchCAT
import cz.dpalecek.catfact.core.design.buttons.TranslatedWithGoogleIconButton
import cz.dpalecek.catfact.core.design.domain.toFlashMessage
import cz.dpalecek.catfact.core.design.indication.LoadingViewCAT
import cz.dpalecek.catfact.core.design.layout.CardAction
import cz.dpalecek.catfact.core.design.layout.CardCAT
import cz.dpalecek.catfact.core.design.layout.ScaffoldCAT
import cz.dpalecek.catfact.core.design.modals.DialogCAT
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme
import cz.dpalecek.catfact.core.design.utils.ActionsInvocationHandler
import cz.dpalecek.catfact.feature.catFact.viewmodel.CatFactActions
import cz.dpalecek.catfact.feature.catFact.viewmodel.CatFactViewModel
import cz.dpalecek.catfact.shared.core.domain.domain.DataAccessError
import cz.dpalecek.catfact.shared.core.domain.domain.DataResource
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFact
import org.koin.androidx.compose.getViewModel

@Composable
internal fun CatFactScreen(viewModel: CatFactViewModel = getViewModel()) {
    val catFact by viewModel.catFact.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isTranslationEnabled by viewModel.isTranslationEnabled.collectAsState()
    val isWifiDialogVisible by viewModel.isWifiDialogVisible.collectAsState()
    val translationState by viewModel.translationState.collectAsState()

    CatFactScreen(
        catFact = catFact,
        isLoading = isLoading,
        error = error,
        isTranslationEnabled = isTranslationEnabled,
        isWifiDialogVisible = isWifiDialogVisible,
        translationState = translationState,
        actions = viewModel,
    )
}

@Composable
private fun CatFactScreen(
    catFact: CatFact?,
    isLoading: Boolean,
    error: DataAccessError?,
    isTranslationEnabled: Boolean,
    isWifiDialogVisible: Boolean,
    translationState: DataResource<Unit>,
    actions: CatFactActions,
) {
    ScaffoldCAT(
        footer = {
            FooterButton(
                actions = actions,
                isLoading = isLoading,
            )
        },
        flashMessage = error.toFlashMessage(onClose = actions::onClearError)
    ) {
        catFact?.let {
            CatFactLayout(
                catFact = it,
                isTranslationEnabled = isTranslationEnabled,
                translationState = translationState,
                actions = actions,
            )
        }
    }
    WifiDialog(
        isVisible = isWifiDialogVisible,
        actions = actions,
    )
}

@Composable
private fun WifiDialog(
    isVisible: Boolean,
    actions: CatFactActions,
) {
    DialogCAT(
        isVisible = isVisible,
        onClose = actions::onWifiDialogClose,
        title = stringResource(id = R.string.translator_download_language_models),
        body = stringResource(id = R.string.translator_use_wifi_for_download),
        dismissButtonLabel = stringResource(id = R.string.core_allow_cellular),
        dismissButtonOnClick = { actions.onDownloadTranslatorPrerequisites(isWifiRequired = false) },
        confirmButtonLabel = stringResource(id = R.string.core_only_wifi),
        confirmButtonOnClick = { actions.onDownloadTranslatorPrerequisites(isWifiRequired = true) },
    )
}

@Composable
private fun BoxScope.FooterButton(
    actions: CatFactActions,
    isLoading: Boolean,
) {
    ButtonCAT(
        text = stringResource(id = R.string.cat_fact_load_another_cat_fact),
        state = if (isLoading) ButtonState.LOADING else ButtonState.DEFAULT,
        onClick = actions::onShowAnotherCatFact,
        modifier = Modifier
            .padding(CatFactTheme.spaces.padding3)
            .fillMaxWidth()
    )
}

@Composable
private fun BoxScope.CatFactLayout(
    catFact: CatFact,
    isTranslationEnabled: Boolean,
    translationState: DataResource<Unit>,
    actions: CatFactActions,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = CatFactTheme.spaces.padding2)
            .padding(top = CatFactTheme.spaces.padding3)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CatFactCard(
            catFact = catFact,
            actions = actions,
        )
        Spacer(modifier = Modifier.height(CatFactTheme.spaces.padding2))
        TranslationCard(
            isTranslationEnabled = isTranslationEnabled,
            catFact = catFact,
            translationState = translationState,
            actions = actions,
        )
    }
}

@Composable
private fun CatFactCard(
    catFact: CatFact,
    actions: CatFactActions,
) {
    CardCAT(
        cardAction = actions.shareAction(text = catFact.fact),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextBody1CAT(
                text = catFact.fact,
                color = CatFactTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun CatFactActions.shareAction(
    text: String,
): CardAction {
    val context = LocalContext.current
    return CardAction(
        imageVector = Icons.Default.Share,
        contentDescription = stringResource(id = R.string.cat_fact_description_share_cat_fact),
        onClick = { onShare(context, text) },
    )
}

@Composable
private fun TranslationCard(
    isTranslationEnabled: Boolean,
    catFact: CatFact,
    translationState: DataResource<Unit>,
    actions: CatFactActions,
) {
    Column {
        CardCAT(
            cardAction = catFact.translation
                ?.let { actions.shareAction(text = it) }
                .takeIf { isTranslationEnabled }
        ) {
            Column {
                SwitchCAT(
                    isChecked = isTranslationEnabled,
                    label = stringResource(id = R.string.translator_translate_with_google),
                    onChanged = actions::onToggleTranslation,
                )
                if (isTranslationEnabled) {
                    val translation = catFact.translation
                    if (translation != null) {
                        TextBody1CAT(
                            text = translation,
                            color = CatFactTheme.colors.onBackground,
                        )
                    } else {
                        when (translationState) {
                            is DataResource.Error -> {
                                TextBody1CAT(
                                    text = translationState.error.message.orEmpty(),
                                    color = CatFactTheme.colors.error,
                                )
                            }

                            DataResource.Loading -> {
                                LoadingViewCAT(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(CatFactTheme.spaces.padding1)
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
        if (isTranslationEnabled && catFact.translation != null) {
            TranslatedWithGoogleIconButton(
                modifier = Modifier
                    .padding(start = CatFactTheme.spaces.padding1_5)
            )
        }
    }
}

@Preview(name = "Day")
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun CatFactPreview() {
    CatFactTheme {
        CatFactScreen(
            catFact = CatFact(
                "70% of your cat's life is spent asleep.",
                "70 % svého života stráví kočka spánkem.",
            ),
            isLoading = false,
            error = null,
            isTranslationEnabled = true,
            isWifiDialogVisible = false,
            translationState = DataResource.Empty,
            actions = ActionsInvocationHandler.createActionsProxy(),
        )
    }
}
