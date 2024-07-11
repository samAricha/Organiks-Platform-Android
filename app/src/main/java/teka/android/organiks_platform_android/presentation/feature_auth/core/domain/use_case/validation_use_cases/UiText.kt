package teka.android.organiks_platform_android.presentation.feature_auth.core.domain.use_case.validation_use_cases

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class PureStringResource(val format: String, val args: List<Any> = emptyList()) : UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)

            else -> throw IllegalArgumentException("Unsupported UiText type: $this")
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)

            else -> throw IllegalArgumentException("Unsupported UiText type: $this")
        }
    }

    fun asPureString(): String {
        return when (this) {
            is DynamicString -> value
            is PureStringResource -> {
                if (args.isNotEmpty()) {
                    String.format(format, *args.toTypedArray())
                } else {
                    format
                }
            }
            else -> throw IllegalArgumentException("Unsupported UiText type: $this")
        }
    }

}