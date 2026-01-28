package com.example.designsystem.component

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.NonggleTheme

@Composable
fun NonggleTextField(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = NonggleTheme.typography.b1_main,
    textColor: Color = Color(0xFF1E1E1E),
    focusedColor: Color = NonggleTheme.colorScheme.m1,
    errorColor: Color = NonggleTheme.colorScheme.error,
    successColor: Color = NonggleTheme.colorScheme.m1,
    disabledColor: Color = NonggleTheme.colorScheme.g3,
    enabledColor: Color = NonggleTheme.colorScheme.g4,
    containerColor: Color = NonggleTheme.colorScheme.g4,
    trailingIcon: @Composable (() -> Unit)? = null,
    hintTextResId: Int,
    supportText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    maxLines: Int = 1,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    label: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        label?.let {
            Spacer(modifier = Modifier.height(8.dp))
            it()
        }
        when(textFieldType) {
            TextFieldType.Standard -> TextField(
                modifier = Modifier
                    .padding(PaddingValues(start = 4.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)),
                value = value,
                supportingText = supportText,
                enabled = enabled,
                isError = isError,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                placeholder = {
                    Text(
                        text = stringResource(hintTextResId),
                        style = NonggleTheme.typography.b1_main,
                        color = NonggleTheme.colorScheme.g3,
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
                ),
            )
            TextFieldType.Outlined -> OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                value = value,
                onValueChange = onValueChange,
                supportingText = supportText,
                textStyle = textStyle.copy(color = textColor),
                shape = shape,
                placeholder = {
                    Text(
                        text = stringResource(hintTextResId),
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
                )
            )
        }
    }
}

enum class TextFieldType {
    Standard, Outlined
}