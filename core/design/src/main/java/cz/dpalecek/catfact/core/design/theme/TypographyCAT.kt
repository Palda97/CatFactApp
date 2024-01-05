package cz.dpalecek.catfact.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class TypographyCAT(
    val body1: TextStyle,
    val headline1: TextStyle,
    val headline2: TextStyle,
    val title1: TextStyle,
)

private val debugStyle = TextStyle(
    fontSize = 60.sp,
    fontWeight = FontWeight.Bold,
)

val LocalTypographyCAT = staticCompositionLocalOf {
    TypographyCAT(
        body1 = debugStyle,
        headline1 = debugStyle,
        headline2 = debugStyle,
        title1 = debugStyle,
    )
}

val DefaultTypographyCAT = TypographyCAT(
    body1 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    headline1 = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
    ),
    headline2 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp,
    ),
    title1 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp,
    )
)
