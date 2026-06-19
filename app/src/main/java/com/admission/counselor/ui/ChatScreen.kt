package com.admission.counselor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.admission.counselor.ui.theme.RevaGrey
import com.admission.counselor.ui.theme.RevaOrange
import com.admission.counselor.ui.theme.LightGrey
import com.admission.counselor.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: CounselorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val streamingText by viewModel.streamingText.collectAsState()
    
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Automatically scroll to the bottom when messages size increases or streaming text updates
    LaunchedEffect(messages.size, streamingText) {
        if (messages.isNotEmpty() || streamingText.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(
                    index = if (streamingText.isNotEmpty()) messages.size else messages.size - 1
                )
            }
        }
    }

    Scaffold(
        topBar = {
            HeaderBar()
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White)
        ) {
            // Main Message list
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message = message)
                }

                // Streaming message placeholder
                if (streamingText.isNotEmpty()) {
                    item {
                        ChatBubble(
                            message = ChatMessage(
                                content = streamingText,
                                isUser = false
                            ),
                            isStreaming = true
                        )
                    }
                }
            }

            // Error banner if any
            if (uiState is CounselorUiState.Error) {
                val errorMsg = (uiState as CounselorUiState.Error).message
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(1.dp, Color(0xFFEF5350), RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = errorMsg,
                            color = Color(0xFFC62828),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { viewModel.reloadModel() }) {
                            Text("Retry", color = RevaOrange, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Model status overlays (Loading or Unloaded)
            when (uiState) {
                is CounselorUiState.LoadingModel -> {
                    LoadingOverlay()
                }
                is CounselorUiState.ModelUnloaded -> {
                    val reason = (uiState as CounselorUiState.ModelUnloaded).reason
                    UnloadedBanner(reason = reason, onReload = { viewModel.reloadModel() })
                }
                else -> {}
            }

            // Input Bar
            InputBar(
                inputText = inputText,
                onTextChanged = { inputText = it },
                onSendClicked = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                        keyboardController?.hide()
                    }
                },
                isEnabled = uiState !is CounselorUiState.LoadingModel,
                isGenerating = uiState is CounselorUiState.Generating,
                onStopClicked = { viewModel.stopGeneration() }
            )
        }
    }
}

@Composable
fun HeaderBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RevaGrey)
            .padding(horizontal = 16.dp, vertical = 16.dp), // Spacing matches clear space criteria
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left brand unit - NAAC A+
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(White.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "NAAC A+",
                color = White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Center Title
        Text(
            text = "REVA Admission Counselor",
            color = White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // Right brand wordmark symbol unit
        Text(
            text = "REVA",
            color = RevaOrange,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    isStreaming: Boolean = false
) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp)
    } else {
        RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)
    }
    
    val bubbleModifier = Modifier
        .clip(bubbleShape)
        .let {
            if (message.isUser) {
                it.background(RevaGrey)
            } else {
                it
                    .background(White)
                    .border(
                        width = 1.dp,
                        color = if (isStreaming) RevaOrange.copy(alpha = 0.5f) else RevaOrange.copy(alpha = 0.2f),
                        shape = bubbleShape
                    )
            }
        }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
        ) {
            if (!message.isUser) {
                // Orange left-accent border column for Counselor
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(IntrinsicSize.Min)
                        .background(RevaOrange)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            Box(
                modifier = bubbleModifier.padding(12.dp)
            ) {
                Column {
                    Text(
                        text = message.content,
                        color = if (message.isUser) White else RevaGrey,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (isStreaming) {
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            color = RevaOrange,
                            trackColor = RevaOrange.copy(alpha = 0.2f),
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrey)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = RevaOrange,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Loading Counselor Model...",
            color = RevaGrey,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun UnloadedBanner(
    reason: String,
    onReload: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RevaGrey.copy(alpha = 0.08f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Counselor memory released ($reason)",
            color = RevaGrey,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onReload,
            colors = ButtonDefaults.buttonColors(containerColor = RevaOrange)
        ) {
            Text("Reload", color = White)
        }
    }
}

@Composable
fun InputBar(
    inputText: String,
    onTextChanged: (String) -> Unit,
    onSendClicked: () -> Unit,
    isEnabled: Boolean,
    isGenerating: Boolean,
    onStopClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrey)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = onTextChanged,
            placeholder = { Text("Enter message here...", color = RevaGrey.copy(alpha = 0.5f)) },
            modifier = Modifier
                .weight(1f)
                .background(White, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            enabled = isEnabled && !isGenerating,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RevaOrange,
                unfocusedBorderColor = RevaGrey.copy(alpha = 0.3f)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSendClicked() })
        )
        
        Spacer(modifier = Modifier.width(12.dp))

        if (isGenerating) {
            Button(
                onClick = onStopClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Stop", color = White)
            }
        } else {
            IconButton(
                onClick = onSendClicked,
                enabled = isEnabled && inputText.isNotBlank(),
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (inputText.isNotBlank() && isEnabled) RevaOrange else RevaGrey.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send Message",
                    tint = White
                )
            }
        }
    }
}
