package com.muhammad.myapplication.forvia.core.presentation.extensions

fun String.toDateOnly(): String {
    return this.split(" ")[0]
}