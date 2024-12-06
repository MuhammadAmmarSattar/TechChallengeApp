package com.muhammad.myapplication.forvia.core.presentation.extensions

fun Long.toFormattedSize(): String {
    return when {
        this >= 1_048_576 -> String.format("%.2f MB", this / 1_048_576.0)
        this >= 1_024 -> String.format("%.2f KB", this / 1_024.0)
        else -> "$this bytes"
    }
}