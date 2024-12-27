package teka.android.organiks_platform_android.presentation.feature_records.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.Category

@Composable
fun CategorySelection(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyRow(
        Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(categories) { category ->
            CategoryItem(
                iconRes = category.resId,
                title = category.title,
                selected = category == selectedCategory
            ) {
                onCategorySelected(category)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}