package com.povush.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.povush.ui.ext.rememberInteractionSource
import com.povush.ui.ext.toFilter

@Composable
fun UiTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholderText: String? = null,
    onTextChange: (text: String) -> Unit = {},
    onClickSend: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .animateContentSize()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
                .weight(1f),
            value = text,
            onValueChange = onTextChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 20.sp
            )
        ) { innerTextField ->
            Box {
                innerTextField()
                if (text.isEmpty() && placeholderText != null) {
                    Text(
                        text = placeholderText,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Image(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onBackground)
                .clickable(
                    indication = ripple(),
                    interactionSource = rememberInteractionSource(),
                    onClick = onClickSend
                )
                .padding(8.dp)
                .size(24.dp),
            imageVector = Icons.AutoMirrored.Default.Send,
            contentDescription = null,
            colorFilter = MaterialTheme.colorScheme.background.toFilter()
        )
    }
}

@Preview
@Composable
private fun UiTextFieldPreview() {
    UiTextField(
        text = "Hello, help me create MEGAPOV with modern AI tools",
        placeholderText = "Ask me!"
    )
}

@Preview
@Composable
private fun UiTextFieldPlaceholderPreview() {
    UiTextField(
        text = "",
        placeholderText = "Ask me!"
    )
}