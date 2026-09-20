package ru.kulishov.markweave.ui.md

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import markweave.shared.generated.resources.Res
import markweave.shared.generated.resources.outline_bug_report_24
import markweave.shared.generated.resources.outline_cancel_24
import markweave.shared.generated.resources.outline_charger_24
import markweave.shared.generated.resources.outline_check_circle_24
import markweave.shared.generated.resources.outline_emergency_home_24
import markweave.shared.generated.resources.outline_format_quote_24
import markweave.shared.generated.resources.outline_help_24
import markweave.shared.generated.resources.outline_ink_pen_24
import markweave.shared.generated.resources.outline_list_24
import markweave.shared.generated.resources.outline_local_fire_department_24
import markweave.shared.generated.resources.outline_task_24
import org.jetbrains.compose.resources.painterResource


@Composable
fun InfoBlock(
    name:String? = null,
    type:String = "note",
    text:AnnotatedString,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    primaryBackgroundColor:Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
    importantColor: Color = MaterialTheme.colorScheme.tertiary,
    importantBackgroundColor:Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
    successColor: Color = MaterialTheme.colorScheme.tertiaryFixed,
    successBackgroundColor:Color = MaterialTheme.colorScheme.tertiaryFixed.copy(alpha = 0.1f),
    errorColor: Color = MaterialTheme.colorScheme.error,
    errorBackgroundColor:Color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    onSurfaceColor: Color = MaterialTheme.colorScheme.onSurface
){
    val currentColor = when(type){
        "tip","abstract","success" -> Pair(successBackgroundColor,successColor)
        "question","warning", "faq"-> Pair(importantBackgroundColor,importantColor)
        "failure","danger", "bug"-> Pair(errorBackgroundColor,errorColor)
        "info" -> Pair(containerColor, onSurfaceColor)
        else-> Pair(primaryBackgroundColor,primaryColor)
    }

    Box(Modifier.fillMaxWidth().background(currentColor.first, shape = RoundedCornerShape(10))){
        Column(Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(painterResource(
                    when(type){
                        "tip" -> Res.drawable.outline_local_fire_department_24
                        "abstract","example" -> Res.drawable.outline_list_24
                        "success" -> Res.drawable.outline_check_circle_24
                        "question","faq" -> Res.drawable.outline_help_24
                        "warning" -> Res.drawable.outline_emergency_home_24
                        "failure" -> Res.drawable.outline_cancel_24
                        "danger" -> Res.drawable.outline_charger_24
                        "bug"-> Res.drawable.outline_bug_report_24
                        "quote" -> Res.drawable.outline_format_quote_24
                        "todo" -> Res.drawable.outline_task_24
                        else-> Res.drawable.outline_ink_pen_24
                    }
                ), contentDescription = "icon",
                    tint = currentColor.second,
                    modifier = Modifier.size(24.dp))
                Text(text= if(name!=null && name.isNotEmpty()) name else type, style = MaterialTheme.typography.titleLarge.copy(color = currentColor.second))
            }
            Text(text, style = MaterialTheme.typography.bodyMedium)

        }

    }
}