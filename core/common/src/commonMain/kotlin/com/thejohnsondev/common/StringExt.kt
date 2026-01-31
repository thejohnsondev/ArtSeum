package com.thejohnsondev.common

fun String.stripHtml(): String {
    return this.replace(Regex("<.*?>"), "")
}
