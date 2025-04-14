package com.tasklist.presentation.auto_migration_room.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasklist.domain.model.AutoMigrationDomainModel
import com.tasklist.presentation.auto_migration_room.AutoMigrationIntent
import com.tasklist.presentation.auto_migration_room.AutoMigrationState
import com.tasklist.presentation.auto_migration_room.AutoMigrationViewModel
import com.tasklist.presentation.ui.theme.CardBackgroundColor
import com.tasklist.presentation.ui.theme.CardContentTextColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun AutoMigrationRoomScreen() {

    val autoMigrationViewModel: AutoMigrationViewModel = koinViewModel()
    val state by autoMigrationViewModel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(autoMigrationViewModel::processIntent) }

    UI(state = state, intent = intent)

}

@Preview
@Composable
private fun UI(
    state: AutoMigrationState = AutoMigrationState(),
    intent: (AutoMigrationIntent) -> Unit = {}
) {

    Log.e("!!!", "${state.autoMigrationList}")

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)) {
            itemsIndexed(state.autoMigrationList, key = { index, item -> item.id }) { index, item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = item.B, color = CardContentTextColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = item.C, color = CardContentTextColor)
                        Text(text = item.D, color = CardContentTextColor)

                        Button(onClick = {
                            intent(AutoMigrationIntent.DeleteItem(item)) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                val randomModel = AutoMigrationDomainModel(
                    id = 0,
                    B = "B: " + (1..100).random().toString(),
                    C = "C: " + listOf("Alpha", "Beta", "Gamma", "Delta").random(),
                    D = "D: " + listOf("Loading", "Success", "Error").random()
                )

                intent(AutoMigrationIntent.InsertItem(randomModel)) }) {
                Text("Insert")
            }
        }
    }
}

