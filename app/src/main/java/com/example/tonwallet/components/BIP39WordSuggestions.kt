package com.example.tonwallet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Chat GPT suggestion?
@Composable
fun BIP39WordSuggestions() {
    val wordList =
        remember { listOf("abandon", "ability", "able", "mafioso", "mafia", "maffin", "zone") }
    val seedPhrase = remember { "example seed phrase" }
    val enteredWords = remember { mutableStateListOf("", "", "") }
    val suggestions = remember { mutableStateListOf<List<String>>() }

    fun generateSuggestions() {
        suggestions.clear()
        for (i in 0..2) {
            val index = wordList.indexOf(enteredWords[i])
            if (index != -1) {
                val start = (index / 32) * 32
                val end = minOf(start + 32, wordList.size)
                suggestions.add(wordList.subList(start, end))
            } else {
                suggestions.add(emptyList())
            }
        }
    }

    Column {
        Text("Enter three random words from your recovery phrase:")
        Row {
            for (i in 0..2) {
                OutlinedTextField(
                    value = enteredWords[i],
                    onValueChange = { enteredWords[i] = it },
                    label = { Text("Word ${i + 1}") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Button(
            onClick = { generateSuggestions() }
        ) {
            Text("Generate Suggestions")
        }
        if (suggestions.any { it.isNotEmpty() }) {
            Text("Suggestions:")
            for (i in 0..2) {
                if (suggestions[i].isNotEmpty()) {
                    LazyRow {
//                        items(suggestions[i]) { word ->
                        items(i) { word ->
                            Text(
                                text = word.toString(),
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = { /* do something with the suggestions */ }
        ) {
            Text("Use Suggestions")
        }
    }
}
