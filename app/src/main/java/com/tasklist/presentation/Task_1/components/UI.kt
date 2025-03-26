package com.tasklist.presentation.Task_1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasklist.R
import com.tasklist.presentation.Task_1.model.Task1Intent
import com.tasklist.presentation.Task_1.model.Task1State
import com.tasklist.presentation.ui.theme.DarkBG
import com.tasklist.presentation.ui.theme.DarkBorder
import com.tasklist.presentation.ui.theme.DarkCursor
import com.tasklist.presentation.ui.theme.DarkOnPrimary
import com.tasklist.presentation.ui.theme.DarkPrimary
import com.tasklist.presentation.ui.theme.DarkText

@Composable
fun UI(
    state: Task1State,
    intent: (Task1Intent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBG),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.user.login,
            onValueChange = { intent(Task1Intent.EnterLogin(it)) },
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = { Text(text = stringResource(id = R.string.enter_login_placeholder)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkPrimary,
                unfocusedBorderColor = DarkBorder,
                cursorColor = DarkCursor,
                focusedTextColor = DarkText,
                unfocusedTextColor = DarkText
            ),
            modifier = Modifier
                .fillMaxHeight(0.07f)
                .fillMaxWidth(0.7f),
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = state.user.password,
            onValueChange = { intent(Task1Intent.EnterPassword(it)) },
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = { Text(text = stringResource(id = R.string.enter_password_placeholder)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkPrimary,
                unfocusedBorderColor = DarkBorder,
                cursorColor = DarkCursor,
                focusedTextColor = DarkText,
                unfocusedTextColor = DarkText
            ),
            modifier = Modifier
                .fillMaxHeight(0.08f)
                .fillMaxWidth(0.7f),
            trailingIcon = {
                val image = if (state.passwordVisible) Icons.Default.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { intent(Task1Intent.TogglePasswordVisibility) }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            },
            visualTransformation = if (state.passwordVisible) { VisualTransformation.None } else { PasswordVisualTransformation() },
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                intent(Task1Intent.SubmitLogin)
            },
            modifier = Modifier
                .fillMaxHeight(0.07f)
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = DarkPrimary),
            enabled = state.buttonActivity
        ) {
            Text(stringResource(id = R.string.enter_button), color = DarkOnPrimary)
        }

        Spacer(modifier = Modifier.size(20.dp))

        if(state.isLoading) {
            CircularProgressIndicator()
        }
    }
}