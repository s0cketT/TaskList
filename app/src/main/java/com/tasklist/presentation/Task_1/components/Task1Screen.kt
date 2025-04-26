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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tasklist.R
import com.tasklist.presentation.Task_1.Task1ViewModel
import com.tasklist.presentation.Task_1.model.Task1Intent
import com.tasklist.presentation.Task_1.model.Task1State
import com.tasklist.presentation.destinations.Task1SuccessDestination
import com.tasklist.presentation.ui.theme.DarkBG
import com.tasklist.presentation.ui.theme.DarkBorder
import com.tasklist.presentation.ui.theme.DarkCursor
import com.tasklist.presentation.ui.theme.DarkOnPrimary
import com.tasklist.presentation.ui.theme.DarkPrimary
import com.tasklist.presentation.ui.theme.DarkText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun Task1Screen(
    navigator: DestinationsNavigator
) {
    val task1ViewModel = koinViewModel<Task1ViewModel>()
    val state by task1ViewModel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(task1ViewModel::processIntent) }
    val event: Flow<Task1ViewModel.Event> by remember { mutableStateOf(task1ViewModel.event) }

    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        event.filterIsInstance<Task1ViewModel.Event.NavigateToNextScreen>().collect {
            navigator.navigate(Task1SuccessDestination)
        }
    }

    UI(
        state = state,
        intent = intent,
    )
}

@Composable
private fun UI(
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