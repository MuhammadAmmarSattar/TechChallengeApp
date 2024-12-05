package com.muhammad.myapplication.forvia.core.extensions

fun String.toDateOnly(): String {
    return this.split(" ")[0]
}