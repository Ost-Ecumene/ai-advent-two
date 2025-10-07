package com.povush.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.povush.chat.ChatConfig
import com.povush.chat.ui.components.MessagesList
import com.povush.navigation.navigator.Navigator
import com.povush.navigation.navigator.impl.MockNavigatorImpl
import com.povush.ui.components.UiTextField
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    navigator: Navigator,
    viewModel: ChatViewModel
) {

    val input by viewModel.input.collectAsState()
    val isStreaming by viewModel.isStreaming.collectAsState()
    val error by viewModel.error.collectAsState()
    val chatHistory by viewModel.chatHistory.collectAsState()
    val temperature by viewModel.temperature.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("AI Advent Chat")
                        Text(
                            getModelDisplayName(selectedModel),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 3.dp
            ) {
                Column {
                    // Выбор модели
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ModelChip(
                            label = "Бюджет",
                            model = ChatConfig.Models.BUDGET,
                            selected = selectedModel == ChatConfig.Models.BUDGET,
                            onClick = { viewModel.onModelChange(ChatConfig.Models.BUDGET) },
                            enabled = !isStreaming,
                            modifier = Modifier.weight(1f)
                        )
                        ModelChip(
                            label = "Средняя",
                            model = ChatConfig.Models.MEDIUM,
                            selected = selectedModel == ChatConfig.Models.MEDIUM,
                            onClick = { viewModel.onModelChange(ChatConfig.Models.MEDIUM) },
                            enabled = !isStreaming,
                            modifier = Modifier.weight(1f)
                        )
                        ModelChip(
                            label = "Продвинутая",
                            model = ChatConfig.Models.ADVANCED,
                            selected = selectedModel == ChatConfig.Models.ADVANCED,
                            onClick = { viewModel.onModelChange(ChatConfig.Models.ADVANCED) },
                            enabled = !isStreaming,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    HorizontalDivider()
                    
                    // Температура
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Температура:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Slider(
                            value = temperature.toFloat(),
                            onValueChange = { viewModel.onTemperatureChange(it) },
                            valueRange = 0f..2f,
                            steps = 19,
                            modifier = Modifier.weight(1f),
                            enabled = !isStreaming
                        )
                        Text(
                            String.format("%.1f", temperature),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.width(32.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding()
                            .navigationBarsPadding()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UiTextField(
                            modifier = Modifier.fillMaxWidth(),
                            text = input,
                            placeholderText = "Спроси меня!",
                            onTextChange = viewModel::onInputChange,
                            onClickSend = viewModel::send
                        )
                    }
                }
            }
        }
    ) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            MessagesList(
                messages = chatHistory,
                isStreaming = isStreaming,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ModelChip(
    label: String,
    model: String,
    selected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            val pricing = ChatConfig.ModelPricing.pricing[model]
            if (pricing != null && pricing.first == 0.0 && pricing.second == 0.0) {
                Text(
                    text = "Бесплатно",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

private fun getModelDisplayName(model: String): String {
    return when (model) {
        ChatConfig.Models.BUDGET -> "Llama 3.2 3B (Free)"
        ChatConfig.Models.MEDIUM -> "GPT-4o Mini"
        ChatConfig.Models.ADVANCED -> "Claude 3.5 Sonnet"
        else -> model
    }
}

//@Preview
//@Composable
//private fun ChatScreenPreview() {
//    ChatScreen(
//        navigator = MockNavigatorImpl()
//    )
//}