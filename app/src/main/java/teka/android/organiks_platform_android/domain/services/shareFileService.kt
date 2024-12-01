package teka.android.organiks_platform_android.domain.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun shareFileService(context: Context, file: File) {
    if (!file.exists()) {
        Toast.makeText(context, "File does not exist!", Toast.LENGTH_SHORT).show()
        return
    }

    try {
        val uri = FileProvider.getUriForFile(
            context,
            "teka.android.organiks.fileprovider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permissions to receiving apps
        }

        // Verify intent resolves to an activity
        if (shareIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
        } else {
            Toast.makeText(context, "No app available to share this file.", Toast.LENGTH_SHORT).show()
        }
//        context.startActivity(Intent.createChooser(shareIntent, "Share Invoice"))
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to share file: ${e.message}", Toast.LENGTH_LONG).show()
    }
}


