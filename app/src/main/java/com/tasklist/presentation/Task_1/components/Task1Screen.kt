package com.tasklist.presentation.Task_1.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tasklist.presentation.Navigation.Screens

import com.tasklist.presentation.Task_1.Task1ViewModel
import com.tasklist.presentation.Task_1.model.Task1Intent
import org.koin.androidx.compose.koinViewModel

@Composable
fun Task1Screen(
    navController: NavController,
    task1ViewModel: Task1ViewModel = koinViewModel()
) {
    val state by task1ViewModel.state.collectAsState()
    val context = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        task1ViewModel.processIntent(Task1Intent.ResetState, navController)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.user.login,
            onValueChange = { task1ViewModel.processIntent(Task1Intent.EnterLogin(it), navController) },
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = { Text(text = "Введите логин") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxHeight(0.07f)
                .fillMaxWidth(0.7f),
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = state.user.password,
            onValueChange = { task1ViewModel.processIntent(Task1Intent.EnterPassword(it), navController) },
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = { Text(text = "Введите пароль") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxHeight(0.08f)
                .fillMaxWidth(0.7f),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            },
            visualTransformation = if (passwordVisible) { VisualTransformation.None } else { PasswordVisualTransformation() },
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                task1ViewModel.processIntent(Task1Intent.SubmitLogin, navController)
            },
            modifier = Modifier
                .fillMaxHeight(0.07f)
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            enabled = state.user.login.isNotBlank() && state.user.password.isNotBlank()
        ) {
            Text("Войти", color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(modifier = Modifier.size(20.dp))

        if(state.isLoading) {
            CircularProgressIndicator()
        }
    }
}
