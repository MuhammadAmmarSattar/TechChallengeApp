package com.muhammad.myapplication.forvia.presentation.custom_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

/**
 * this creates a customizable Text composable that can be reused throughout the app.
 * we can easily change text properties from a single place, making your code more maintainable and consistent across the app.
 * */
@Composable
    fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 16,
    lineHeight : Int = 20,
    fontWeight: FontWeight = FontWeight.Bold,
    maxLines : Int = 1,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    overflow: TextOverflow = TextOverflow.Ellipsis
    ) {
        Text(
            modifier = modifier,
            text = text,
            maxLines = maxLines,
            overflow = overflow,
            style = textStyle.copy(
                fontWeight = fontWeight,
                fontSize = fontSize.sp,
                lineHeight = lineHeight.sp

            )
        )
    }