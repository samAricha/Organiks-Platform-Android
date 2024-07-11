package teka.android.organiks_platform_android.util.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    hasBackNavigation: Boolean = false,
    backNavigationIcon: ImageVector = Icons.Filled.ArrowBack,
    onBackNavigationClick: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
    ),
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                textAlign = TextAlign.Center,
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {  },
        navigationIcon = {
            if (hasBackNavigation) {
                IconButton(onClick = onBackNavigationClick) {
                    Icon(
                        imageVector = backNavigationIcon,
                        contentDescription = "Back"
                    )
                }
            } else {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle drawer",
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = colors,
    )
}