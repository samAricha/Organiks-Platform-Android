package teka.android.organiks_platform_android.presentation.aiadvice.chat_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import teka.android.organiks_platform_android.domain.models.ChatStatusModel
import teka.android.organiks_platform_android.ui.theme.Cream1
import teka.android.organiks_platform_android.ui.theme.Cream2
import teka.android.organiks_platform_android.ui.theme.Gray700
import teka.android.organiks_platform_android.ui.theme.SecondaryLightColor
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException
import java.io.InputStream


@Composable
fun CustomBottomSearchBar(
    modifier: Modifier = Modifier,
    status: ChatStatusModel,
    onSendClick: (String, List<ByteArray>) -> Unit
) {
    val textState = remember { mutableStateOf("") }
    val images = remember { mutableStateOf(listOf<Bitmap>()) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val pickMultipleMedia =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(5)
        ) { uris ->
            scope.launch(Dispatchers.IO) {
                images.value = uris.mapNotNull { uri ->
                    Glide.with(context).asBitmap().load(uri).submit().get()
                }
            }
        }


    Column {
        LazyRow {
            items(images.value.size) { index ->
                val bitmap: ImageBitmap = images.value[index].asImageBitmap()
                ImageAttachment(
                    bitmap = bitmap,
                    onCloseClick = {
                        val mutableImages = images.value.toMutableList()
                        mutableImages.removeAt(index)
                        images.value = mutableImages
                    }
                )
            }
        }
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f),
                value = textState.value,
                onValueChange = { textState.value = it },
                maxLines = 3,
                placeholder = {
                    Text(
                        text = "Message...",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Gray700,
                        ),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Cream2,
                    unfocusedContainerColor = Cream2,
                    disabledContainerColor = Cream2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = SecondaryLightColor
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            pickMultipleMedia.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        content = {
                            Icon(
                                Icons.Outlined.ImageSearch,
                                contentDescription = null,
                                tint = SecondaryLightColor
                            )
                        },
                    )
                },
                shape = RoundedCornerShape(50),
            )

            IconButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    onSendClick(textState.value, images.value.map { imageBitMap -> bitmapToByteArray(imageBitMap) })
                    images.value = emptyList()
                    textState.value = ""
                },
                enabled = textState.value.isNotBlank() && status != ChatStatusModel.Loading,
                content = {
                    var tintColor = SecondaryLightColor
                    var iconRotation = 0F

                    if (status is ChatStatusModel.Loading) {
                        tintColor = Color.LightGray
                        iconRotation = -90.0F
                    } else {
                        tintColor = SecondaryLightColor
                        iconRotation = 0F
                    }

                    Icon(
                        Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.rotate(iconRotation),
                        tint = tintColor,
                    )
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Cream1,
                    containerColor = Cream1,
                    disabledContainerColor = Cream2
                ),
            )

        }

    }
}

@Composable
fun ImageAttachment(bitmap: ImageBitmap, onCloseClick: () -> Unit = {}) {

    val iconSize = 20.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    Box(modifier = Modifier.padding((iconSize / 3))) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(80.dp)
                .wrapContentWidth()
                .shadow(1.dp, RoundedCornerShape(12.dp)),
        )

        IconButton(
            onClick = {
                onCloseClick()
            },
            modifier = Modifier
                .offset {
                    IntOffset(x = +offsetInPx, y = -offsetInPx)
                }
                .padding(5.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .size(iconSize)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Throws(IOException::class)
fun getImageBytes(inputStream: InputStream): ByteArray? {
    val byteBuffer = ByteArrayOutputStream()
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)
    var len = 0
    while (inputStream.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }
    return byteBuffer.toByteArray()
}

//TODO takes URI of the image and returns bitmap
private fun uriToBitmap(selectedFileUri: Uri, context: Context): Bitmap? {
    try {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
