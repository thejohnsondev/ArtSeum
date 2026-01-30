package com.thejohonsondev.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.thejohnsondev.common.base.DisplayableMessageValue
import com.thejohnsondev.ui.generated.resources.Res
import com.thejohnsondev.ui.generated.resources.done
import com.thejohnsondev.ui.generated.resources.error_title
import com.thejohnsondev.ui.generated.resources.generic_error_message
import com.thejohnsondev.ui.generated.resources.no_internet_error_message
import com.thejohonsondev.ui.designsystem.Colors
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorDialog(
    error: DisplayableMessageValue,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(Res.string.error_title))
        },
        text = {
            Text(text = error.asString())
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.string.done), color = Colors.colorScheme.primary)
            }
        },
        containerColor = Colors.colorScheme.surface
    )
}

@Composable
fun DisplayableMessageValue.asString(): String {
    return when (this) {
        is DisplayableMessageValue.NoInternetError -> stringResource(Res.string.no_internet_error_message)
        is DisplayableMessageValue.UnknownError -> stringResource(Res.string.generic_error_message)
        is DisplayableMessageValue.StringValue -> this.value
    }
}
