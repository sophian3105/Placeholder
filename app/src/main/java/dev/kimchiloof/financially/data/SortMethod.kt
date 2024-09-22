package dev.kimchiloof.financially.data

enum class SortMethod(
    val label: String
) {
    AMOUNT("Amount"),
    CATEGORY("Category"),
    DATE("Date"),
    DATE_CREATED("Date Created"),
}
