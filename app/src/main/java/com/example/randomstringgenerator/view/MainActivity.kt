package com.example.randomstringgenerator.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.randomstringgenerator.viewmodel.RandomStringViewModel

class MainActivity : ComponentActivity() {

    val randomStringViewModel = ViewModelProvider(this)[RandomStringViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomStringGeneratorApp(randomStringViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomStringGeneratorApp(
    randomStringViewModel: RandomStringViewModel,
) {

    val randomStringList by randomStringViewModel.randomStringList.observeAsState()

    var length by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Random String Generator")
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        })
    { innerPadding ->
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = length,
                    onValueChange = { length = it },
                    label = { Text("Enter length") }
                )

                Row {
                    Button(
                        onClick = {
                            randomStringViewModel.addRandomString(Integer.parseInt(length))
                        }) {
                        Text("Generate Random String")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = {
                            randomStringViewModel.deleteAllRandomString()
                        }) {
                        Text("Clear All")
                    }
                }

                LazyColumn {
                    items(count = randomStringList?.size ?: 0, itemContent = { index ->
                        val randomString = randomStringList?.get(index)
                        Row {
                            Text(
                                text = randomString?.value ?: "",
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Button(
                                onClick = {
                                    if (randomString != null) {
                                        randomStringViewModel.deleteRandomString(randomString)
                                    }
                                }) {
                                Text("Remove")
                            }
                        }
                        HorizontalDivider()
                    }
                    )
                }
            }
        }
    }
}
