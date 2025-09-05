package com.example.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sampleapp.features.domain.model.ClockEntity
import com.example.sampleapp.features.domain.usecase.GetBranchesUseCase
import com.example.sampleapp.features.domain.usecase.clock.GetLatestTickUseCase
import com.example.sampleapp.features.domain.usecase.clock.InsertTickUseCase
import com.example.sampleapp.shared.models.pagination.SearchRequest
import com.example.sampleapp.ui.theme.SampleappTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getBranchesUseCase: GetBranchesUseCase
    @Inject lateinit var getLatestTickUseCase: GetLatestTickUseCase
    @Inject lateinit var insertTickUseCase: InsertTickUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SampleappTheme {
                BranchTestScreen(
                    getBranchesUseCase = getBranchesUseCase,
                    getLatestTickUseCase = getLatestTickUseCase,
                    insertTickUseCase = insertTickUseCase
                )
            }
        }
    }
}
@Composable
fun BranchTestScreen(
    getBranchesUseCase: GetBranchesUseCase,
    getLatestTickUseCase: GetLatestTickUseCase,
    insertTickUseCase: InsertTickUseCase
) {
    var branchResultText by remember { mutableStateOf("Press the button to fetch branches") }
    var latestTick by remember { mutableStateOf<ClockEntity?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isTimerRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect latest tick from database
    LaunchedEffect(Unit) {
        try {
            getLatestTickUseCase().collect { tick ->
                latestTick = tick
            }
        } catch (e: Exception) {
            Log.e("BranchTestScreen", "Error collecting latest tick", e)
            errorMessage = "Error loading tick: ${e.message}"
        }
    }

    // Timer logic: Insert new timestamp every second when running
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (true) {
                try {
                    val newTick = ClockEntity(timestamp = System.currentTimeMillis())
                    insertTickUseCase(newTick)
                    Log.d("BranchTestScreen", "Inserted new tick: $newTick")
                } catch (e: Exception) {
                    Log.e("BranchTestScreen", "Error inserting tick", e)
                    errorMessage = "Error inserting tick: ${e.message}"
                }
                delay(1.seconds)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Fetch Branches Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val result = getBranchesUseCase(
                                SearchRequest(
                                    pageNumber = 1,
                                    pageSize = 10,
                                    search = null,
                                    searchColumn = null,
                                    sortColumnName = null,
                                    sortDirection = null
                                )
                            )
                            branchResultText = if (result != null) {
                                Log.d("BranchTestScreen", "Branches: $result")
                                "Fetched ${result.items.size} branches"
                            } else {
                                "No data received"
                            }
                        } catch (e: Exception) {
                            branchResultText = "Error: ${e.message}"
                            Log.e("BranchTestScreen", "Error fetching branches", e)
                            snackbarHostState.showSnackbar("Error fetching branches: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Fetch Branches")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(branchResultText)

            Spacer(modifier = Modifier.height(20.dp))

            // Display latest timestamp from database
            Text(
                text = "Latest Timestamp: ${
                    latestTick?.timestamp?.let { dateFormat.format(Date(it)) } ?: "No timestamp available"
                }"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Start/Stop Timer Button
            Button(
                onClick = {
                    isTimerRunning = !isTimerRunning
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isTimerRunning) "Stop Timer" else "Start Timer")
            }

            // Error Message Display
            errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}