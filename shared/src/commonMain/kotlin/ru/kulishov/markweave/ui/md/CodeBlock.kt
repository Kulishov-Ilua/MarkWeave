package ru.kulishov.markweave.ui.md

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.kulishov.markweave.repository.MDParse


@Composable
fun CodeBlock(
    lang:String,
    code:String,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
){
    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(backgroundColor)){
        Column(
            modifier = Modifier.padding(15.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Text(lang, style =  textStyle.copy(color = contentColor))
            }
            Text(MDParse.mdCodeParser(lang,code,textStyle,contentColor))
        }
    }

}