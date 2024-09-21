package dev.kimchiloof.financially.navigation.newReceipt

enum class NewReceiptDestination(
    val route: String,                  // Unique route
) {
    NewReceiptCamera("camera"),
    NewReceiptConfirmation("confirmation");
}
