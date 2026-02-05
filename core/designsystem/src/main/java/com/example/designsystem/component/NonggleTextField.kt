package com.example.core.designsystem.component

import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R
import com.example.core.designsystem.theme.NonggleTheme

@Composable
fun NonggleTextField(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = NonggleTheme.typography.b1_main,
    textColor: Color = NonggleTheme.colorScheme.black,
    focusedColor: Color = NonggleTheme.colorScheme.m1,
    errorColor: Color = NonggleTheme.colorScheme.error,
    successColor: Color = NonggleTheme.colorScheme.m1,
    disabledColor: Color = NonggleTheme.colorScheme.g3,
    enabledColor: Color = NonggleTheme.colorScheme.g4,
    containerColor: Color = NonggleTheme.colorScheme.g4,
    trailingIcon: @Composable (() -> Unit)? = null,
    hintText: String,
    supportText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    maxLines: Int = 1,
    maxLength: Int? = null,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    label: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        label?.let {
            it()
            Spacer(modifier = Modifier.height(4.dp))
        }
        when(textFieldType) {
            TextFieldType.Standard -> TextField(
                value = value,
                supportingText = supportText,
                enabled = enabled,
                isError = isError,
                onValueChange = {newText ->
                    if(maxLength != null) {
                        if(newText.length <= maxLength) {
                            onValueChange(newText)
                        }
                    } else {
                        onValueChange(newText)
                    }
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                placeholder = {
                    Text(
                        text = hintText,
                        style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colorScheme.g3),
                    )
                },
                maxLines = maxLines,
                textStyle = textStyle.copy(color = textColor),
                readOnly = readOnly,
                trailingIcon = trailingIcon,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = focusedColor,
                    unfocusedIndicatorColor = if(isSuccess) successColor else enabledColor,
                    disabledIndicatorColor = disabledColor,
                    errorIndicatorColor = errorColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = NonggleTheme.colorScheme.m1
                ),
            )
            TextFieldType.Outlined -> OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                value = value,
                onValueChange = {newText ->
                    if(maxLength != null) {
                        if(newText.length <= maxLength) {
                            onValueChange(newText)
                        }
                    } else {
                        onValueChange(newText)
                    }
                },
                containerColor = NonggleTheme.colorScheme.white,
                supportingText = supportText,
                textStyle = textStyle.copy(color = textColor),
                shape = shape,
                placeholder = {
                    Text(
                        text = hintText,
                        style = NonggleTheme.typography.b1_main,
                        color = NonggleTheme.colorScheme.g3,
                    )
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                maxLines = maxLines,
                trailingIcon = trailingIcon,
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = focusedColor,
                    unfocusedBorderColor = if(isSuccess) successColor else enabledColor,
                    disabledBorderColor = disabledColor,
                    errorBorderColor = errorColor,
                    focusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    errorContainerColor = containerColor,
                    cursorColor = NonggleTheme.colorScheme.m1
                )
            )
        }
    }
}

enum class TextFieldType {
    Standard, Outlined
}

@Preview(showBackground = true)
@Composable
fun NonggleStandardTextFieldPreview() {
    NonggleTextField(
        label = {
            Text(
                text = "레이블",
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colorScheme.g1,
            )
        },
        textFieldType = TextFieldType.Standard,
        value = "이름 입력",
        onValueChange = { },
        trailingIcon = {
            NonggleIconButton(
                image = painterResource(R.drawable.xcircle),
                onClick = { }
            )
        },
        hintText = "힌트",
    )
}

@Preview(showBackground = true)
@Composable
fun NonggleOutlinedTextFieldPreview() {
    NonggleTextField(
        textFieldType = TextFieldType.Outlined,
        value = "이름 입력",
        onValueChange = { },
        trailingIcon = {
            NonggleIconButton(
                image = painterResource(R.drawable.xcircle),
                onClick = { }
            )
        },
        hintText = "힌트",
    )
}