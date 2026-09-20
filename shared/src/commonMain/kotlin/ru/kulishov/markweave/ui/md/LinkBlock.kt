package ru.kulishov.markweave.ui.md

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import markweave.shared.generated.resources.Res
import markweave.shared.generated.resources.outline_link_2_24
import org.jetbrains.compose.resources.painterResource

@Composable
fun LinkBlock(
    name: String,
    primaryColor: Color = MaterialTheme.colorScheme.onSurface,
    primaryBackgroundColor:Color = MaterialTheme.colorScheme.background
){
    Box(Modifier.fillMaxWidth().background(primaryBackgroundColor, shape = RoundedCornerShape(10))){
        Row(Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Icon(painterResource(Res.drawable.outline_link_2_24), contentDescription = "linkIcon",
                tint = primaryColor, modifier = Modifier.size(50.dp))
            Text(name, style = MaterialTheme.typography.titleLarge)
        }
    }
}