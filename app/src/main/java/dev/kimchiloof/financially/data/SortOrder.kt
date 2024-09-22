package dev.kimchiloof.financially.data

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SortAmountDown
import compose.icons.fontawesomeicons.solid.SortAmountDownAlt

enum class SortOrder (
    val icon: ImageVector
) {
    ASCENDING(FontAwesomeIcons.Solid.SortAmountDownAlt),
    DESCENDING(FontAwesomeIcons.Solid.SortAmountDown);

    fun toggle(): SortOrder {
        return when (this) {
            ASCENDING -> DESCENDING
            DESCENDING -> ASCENDING
        }
    }
}
