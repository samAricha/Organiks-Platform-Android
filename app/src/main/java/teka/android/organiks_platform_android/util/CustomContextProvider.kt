package teka.android.organiks_platform_android.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


class CustomContextProvider {
    @Composable
    fun getContext(): Context {
        return LocalContext.current
    }
}

