package teka.android.organiks_platform_android.presentation.module_invoice.pdf_viewer

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File

@Composable
fun PDFViewerScreen(
    filePath: String,
    navController: NavController
) {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Local(
            uri = Uri.fromFile(File(filePath))
        ),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
