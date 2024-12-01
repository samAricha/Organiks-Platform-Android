package teka.android.organiks_platform_android.presentation.module_invoice.pdf_viewer

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import teka.android.organiks_platform_android.domain.services.shareFileService
import java.io.File

@Composable
fun PDFViewerScreen(
    filePath: String,
    navController: NavController
) {
    val appContext = LocalContext.current
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Local(
            uri = Uri.fromFile(File(filePath))
        ),
        isZoomEnable = true
    )

    // Screen content
    Box(modifier = Modifier.fillMaxSize()) {
        // PDF Reader
        VerticalPDFReader(
            state = pdfState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        )

        // Floating Action Button to share the file
        pdfState.file?.let { file ->
            FloatingActionButton(
                onClick = {
                    shareFileService(appContext, file)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_share),
                    contentDescription = "Share"
                )
            }
        }
    }
}

