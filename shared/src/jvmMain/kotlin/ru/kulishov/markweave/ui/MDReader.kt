package ru.kulishov.markweave.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kulishov.markweave.dictionary.md.MarkDownDictionary
import ru.kulishov.markweave.repository.MDParse
import java.nio.file.Files

import javax.swing.JFileChooser
import javax.swing.JFrame



fun chooseAndReadFile():List<@Composable () -> Unit>?{
    val fileChooser = JFileChooser()

    val result = fileChooser.showOpenDialog(null)
    val selectedFile = if (result == JFileChooser.APPROVE_OPTION) {
        val file = fileChooser.selectedFile
        file.readText()
    } else {
        return  null
    }
    if(selectedFile!=null){
        return MDParse.mdParser(selectedFile, MarkDownDictionary.baseTextStyle,
            MarkDownDictionary.primaryColor, MarkDownDictionary.backgroundColor)
    }
    return null
}
@Composable
fun MDReader(){
    var actualLayout by remember { mutableStateOf(listOf<@Composable () -> Unit> ()) }


    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)){
        Column {
            Box(Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer)){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Button(onClick = {

                    actualLayout=chooseAndReadFile()?: actualLayout

                    }){
                        Text("Open")
                    }
                }
            }
            LazyColumn(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,) {
                items(actualLayout){
                    it()
                }

            }
        }

    }
}