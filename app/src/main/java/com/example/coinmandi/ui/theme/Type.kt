package com.example.coinmandi.ui.theme

import android.R.attr.fontFamily
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coinmandi.R

val HeadingFont = FontFamily(
    Font(R.font.inter_bold  , weight = FontWeight.ExtraBold),
    Font(R.font.inter_medium  , weight = FontWeight.Medium),
    Font(R.font.inter_regular  , weight = FontWeight.Normal)
)
val BodyFont = FontFamily(
    Font(R.font.nunito , weight = FontWeight.Normal) ,
    Font(R.font.nunito , weight = FontWeight.Medium) ,
    Font(R.font.nunito , weight = FontWeight.Bold)
)
val creativefont3 = FontFamily(
    Font(R.font.roses , weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp ,
        color = Color.White
    ),
    titleMedium = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp ,
        color = Color.White
    ),
    titleSmall = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp ,
        color = Color.White
    ),
    bodyLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp ,
        color = Color.White
    ),
    bodyMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp ,
        color = Color.White
    ),
    bodySmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp ,
        color = Color.White
    )
)

