package teka.android.organiks_platform_android.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment

@Composable
fun SearchComposable(onSearch: (query: String) -> Unit) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it.text)
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchText.text)
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchText.text.isEmpty()) {
                        Text(
                            text = "Search here...",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)) // Here's the change for rounded corners
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp)) // Apply the same shape to border
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun SearchPreview() {
    SearchComposable { }
}
