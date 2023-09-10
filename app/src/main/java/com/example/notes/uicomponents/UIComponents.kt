package com.example.notes.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.example.notes.R

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
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 16.sp,
            color = colorResource(id = R.color.lightGray),
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