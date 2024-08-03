package teka.android.organiks_platform_android.presentation.feature_ai_assistant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.AnalystMessage
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.data.Message
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.utils.ApiType
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.viewmodels.GeminiAnalystViewModel
import teka.android.organiks_platform_android.ui.theme.SecondaryColor
import teka.android.organiks_platform_android.ui.theme.quicksand

@Composable
fun AnalystConversationArea(
    viewModel: GeminiAnalystViewModel,
    apiType: ApiType
) {
    val response: List<AnalystMessage>? = when (apiType) {
        ApiType.MULTI_CHAT -> viewModel.conversationList.observeAsState().value?.toList()
        ApiType.SINGLE_CHAT -> TODO()
        ApiType.IMAGE_CHAT -> TODO()
        ApiType.DOCUMENT_CHAT -> TODO()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if ((response != null) && response.isEmpty()) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.organiks_analytics_chat),
                contentDescription = "no analytical message"
            )
            Text(
                color = SecondaryColor,
                text = "Ask and Gemini Analyst shall answer!",
                fontWeight = FontWeight.W500,
                fontFamily = quicksand,
                fontSize = 15.sp
            )
        }
    }
    LazyColumn(
        reverseLayout = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(response!!.reversed()) { message ->
            MessageItem(
                text = message.text,
                mode = message.mode
            )
        }
    }
}
