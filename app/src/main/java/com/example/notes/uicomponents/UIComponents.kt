package com.example.notes.uicomponents

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.R
import com.example.notes.models.NotesResponse
import com.example.notes.utlls.Constants.TAG

@Composable
fun BoldText(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun NormalText(
    text: String,
    color: Color = colorResource(id = R.color.lightGray)
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 16.sp,
            color = color,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun Spacer(
    value: Dp = 10.dp
) {
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier.height(value)
    )
}

@Composable
fun HeadingText(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    onValueChanged: (String) -> Unit,
    errorStatus: Boolean = false
) {
    var textState by remember {
        mutableStateOf("")
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        value = textState,
        onValueChange = {
            onValueChanged(it)
            textState = it
        },
        isError = if (textState != "") !errorStatus else false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    onValueChanged: (String) -> Unit,
    errorStatus: Boolean = false,
) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    var textState by remember {
        mutableStateOf("")
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = textState,
        onValueChange = {
            onValueChanged(it)
            textState = it
        },
        isError = if (textState != "") !errorStatus else false,
        trailingIcon = {
            val iconImg = if (isVisible) R.drawable.visibility else R.drawable.visible_off
            val description =
                if (isVisible) stringResource(id = R.string.hide_password) else stringResource(
                    id = R.string.show_password
                )
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(painter = painterResource(id = iconImg), contentDescription = description)
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(
            mask = '*'
        )
    )
}

@Composable
fun ButtonComp(
    text: String,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        enabled = isEnabled,
        onClick = {
            onButtonClicked()
        }) {
        Text(text = text.uppercase())
    }
}

@Composable
fun AnnotatedText(
    normalText: String,
    hyperText: String,
    onHyperTextClicked: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append(normalText)
        append(" ")
        withStyle(style = SpanStyle(color = colorResource(id = R.color.hyperText))) {
            pushStringAnnotation(tag = hyperText, annotation = hyperText)
            append(hyperText)
        }
    }
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if (span.item == hyperText) {
                        onHyperTextClicked(hyperText)
                    }
                }
        },
        style = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    search: String,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit
) {
    TextField(
        value = search,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(id = R.color.lightGray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (search.isNotBlank()) {
                IconButton(onClick = {
                    onValueChanged("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close search"
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Search Notes..",
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.5f)
                )
            )
        }
    )
}

@Composable
fun EachRowComposable(
    notesResponse: NotesResponse,
    modifier: Modifier = Modifier,
    onDeleteBtnClicked: (noteId: String) -> Unit,
    onNoteClicked: (noteResponse: NotesResponse) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.lightGray),
                shape = RoundedCornerShape(10.dp)
            ).clickable {
                onNoteClicked(notesResponse)
            }
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notesResponse.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier.weight(.7f)
                )
                IconButton(onClick = {
                    onDeleteBtnClicked(notesResponse._id)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Button",
                        tint = Color.Gray
                    )
                }
            }
            Spacer()
            Text(
                text = notesResponse.description, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            )
            Spacer(value = 5.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = notesResponse.createdAt.split("T")[0],
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.4f),
                    fontSize = 9.sp,
                    textAlign = TextAlign.End
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String,
    onValueChanged: (String) -> Unit
) {
    androidx.compose.material3.OutlinedTextField(
        value = text,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),
        label = {
            Text(text = placeholder)
        }
    )
}

@Composable
fun ShowDialogBox(
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onButtonClicked: () -> Unit,
    onClose: () -> Unit,
    isSaveBtnEnabled: Boolean = false
) {
    AlertDialog(onDismissRequest = {

    },
        confirmButton = {
            Button(
                enabled = isSaveBtnEnabled,
                onClick = {
                    onButtonClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = .6f),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                Text(text = "Save")
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onClose()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close dialog",
                        tint = Color.Gray
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    text = title,
                    placeholder = "Title",
                    onValueChanged = onTitleChanged
                )
                Spacer()
                OutlinedTextField(
                    text = description,
                    placeholder = "Description",
                    onValueChanged = onDescriptionChanged,
                    modifier = Modifier.height(300.dp)
                )
            }
        }
    )
}