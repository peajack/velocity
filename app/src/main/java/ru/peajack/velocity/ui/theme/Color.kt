package ru.peajack.velocity.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green69 = Color(0xFF5DBD73)
val Blue69 = Color(0xFF3A86ED)
val Blue67 = Color(0xFF5FA4FA)
val Gray69 = Color(0xFF92A1B6)
val Gray67 = Color(0xFF68788F)
val Yellow69 = Color(0xFFD8A65E)
val Purple69 = Color(0xFF997FEF)
val Purple67 = Color(0xFF8561EC)

val Gradient1 = Brush.linearGradient(
    colors = listOf(
        Green69,
        Blue69
    ),
    start = Offset(0f, 0f),
    end = Offset(800f, 800f)
)
val Gradient2 = Brush.linearGradient(
    colors = listOf(
        Blue67,
        Blue69
    ),
    start = Offset(0f, 0f),
    end = Offset(800f, 800f)
)

val primaryDark = Color(0xFF5DBD73)
val secondaryDark = Color(0xFF4E7FE5)
val tertiaryDark = Color(0xFFD8A65E)
val backgroundDark = Color(0xFF111629)
val surfaceDark = Color(0xFF111629)
val surfaceContainerDark = Color(0xFF20293A)
val primaryContainerDark = Color(0xFF997FEF)
val onBackgroundDark = Color(0xFFF8FAFE)
val onSurfaceDark = Color(0xFFF8FAFE)
val onSurfaceVariantDark = Color(0xFF9099AA)
val onPrimaryDark = Color.White


val primaryLight = Color(0xFF5DBD73)
val secondaryLight = Color(0xFF4E7FE5)
val tertiaryLight = Color(0xFFD8A65E)
val backgroundLight = Color(0xFFF8FAFC)
val surfaceLight = Color(0xFFF8FAFC)
val surfaceContainerLight = Color.White
val primaryContainerLight = Color(0xFF4E7FE5)
val onBackgroundLight = Color(0xFF222A3C)
val onSurfaceLight = Color(0xFF222A3C)
val onSurfaceVariantLight = Color(0xFF9EA8B7)
val onPrimaryLight = Color.White