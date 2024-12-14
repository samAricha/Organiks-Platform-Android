package teka.android.organiks_platform_android.util.components.bottom_sheet;

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import teka.android.organiks_platform_android.ui.theme.ToEdgeHorzPaddingLarge
import teka.android.organiks_platform_android.util.widgets.SimpleSearchInputWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> BottomSheetSelection(
    visible: Boolean,
    title: String,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    items: List<T>,
    onDismissRequest: () -> Unit,
    onItemSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
    isSearchEnabled: Boolean = true,
    ) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300),
            targetOffsetY = { it }
        ),
    ) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = ToEdgeHorzPaddingLarge)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                BoxWithConstraints (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                max = this@BoxWithConstraints.maxHeight - 72.dp
                            )
                    ) {
                        items(items) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemSelected(item) }
                                    .padding(vertical = 8.dp)
                            ) {
                                itemContent(item)
                            }
                        }
                    }

                }

                if (isSearchEnabled) {
                    SimpleSearchInputWidget(
                        value = searchValue,
                        onValueChange = { query ->
                            onSearchValueChange(query)
                        },
                        placeholderText = "Search ...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}
