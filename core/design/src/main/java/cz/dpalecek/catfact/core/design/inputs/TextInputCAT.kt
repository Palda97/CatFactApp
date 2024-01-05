package cz.dpalecek.catfact.core.design.inputs

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.dpalecek.catfact.core.design.domain.KeyboardState
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TextInputCAT(
    text: String,
    onChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = text))
    }
    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            onChange(it.text)
            textFieldValue = it
        },
        textStyle = CatFactTheme.typography.body1.copy(
            color = CatFactTheme.colors.onBackground,
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = true,
        decorationBox = decorationBox(
            isFocused = isFocused,
            text = text,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
        ),
        modifier = modifier
            .rememberFocus(isFocused)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.rememberFocus(
    isFocused: Boolean,
): Modifier = composed {
    var isFocusedSaveable by rememberSaveable { mutableStateOf(false) }
    var isKeyboardOpenSaveable by rememberSaveable { mutableStateOf(false) }
    val isFocusedUpdated by rememberUpdatedState(newValue = isFocused)
    val keyboardState by rememberUpdatedState(newValue = KeyboardState.current)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    DisposableEffect(key1 = Unit) {
        if (isFocusedSaveable) {
            focusRequester.requestFocus()
            if (isKeyboardOpenSaveable) {
                keyboardController?.let {
                    scope.launch {
                        delay(OPEN_KEYBOARD_DELAY)
                        it.show()
                    }
                }
            }
        }
        onDispose {
            isFocusedSaveable = isFocusedUpdated
            isKeyboardOpenSaveable = keyboardState.isOpened
        }
    }
    focusRequester(focusRequester)
}

private const val OPEN_KEYBOARD_DELAY = 250L

private fun decorationBox(
    isFocused: Boolean,
    text: String,
    placeholder: String,
    leadingIcon: ImageVector?,
): @Composable (@Composable () -> Unit) -> Unit = { innerTextField ->
    val shape = RoundedCornerShape(CatFactTheme.spaces.padding0_5)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape)
            .border(
                width = 1.dp,
                color = if (isFocused) CatFactTheme.colors.primary else Color.Unspecified,
                shape = shape
            )
            .background(CatFactTheme.colors.background)
            .defaultMinSize(
                minHeight = CatFactTheme.spaces.padding3
            )
            .width(TextFieldDefaults.MinWidth)
    ) {
        Spacer(modifier = Modifier.width(CatFactTheme.spaces.padding1))
        leadingIcon?.let {
            Spacer(modifier = Modifier.width(CatFactTheme.spaces.padding0_5))
            Icon(
                imageVector = it,
                contentDescription = placeholder,
                tint = CatFactTheme.colors.onBackground,
            )
        }
        Box(
            modifier = Modifier
                .padding(
                    start = CatFactTheme.spaces.padding1,
                    top = CatFactTheme.spaces.padding2,
                    end = CatFactTheme.spaces.padding2,
                    bottom = CatFactTheme.spaces.padding2,
                )
        ) {
            if (text.isEmpty()) {
                TextBody1CAT(
                    text = placeholder,
                    color = CatFactTheme.colors.onBackground.copy(alpha = PLACEHOLDER_ALPHA),
                )
            }
            innerTextField()
        }
    }
}

private const val PLACEHOLDER_ALPHA = 0.2f

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun TextInputCATPreview() {
    CatFactTheme {
        Column {
            TextInputCAT(
                text = "John Doe",
                onChange = {},
                placeholder = "Placeholder",
                leadingIcon = Icons.Outlined.AccountCircle,
                modifier = Modifier.padding(CatFactTheme.spaces.padding1)
            )
            TextInputCAT(
                text = "",
                onChange = {},
                placeholder = "Placeholder",
                modifier = Modifier.padding(CatFactTheme.spaces.padding1)
            )
        }
    }
}
