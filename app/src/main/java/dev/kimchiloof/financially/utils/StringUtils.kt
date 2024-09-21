package dev.kimchiloof.financially.utils

fun String.toTitleCase(): String {
    return this.split(" _-").joinToString { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}
