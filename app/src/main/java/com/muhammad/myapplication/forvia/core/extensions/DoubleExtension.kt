package com.muhammad.myapplication.forvia.core.extensions


fun Double.toFormattedRating(): String {
    return if (this == 0.0) "- -" else this.toString()
}