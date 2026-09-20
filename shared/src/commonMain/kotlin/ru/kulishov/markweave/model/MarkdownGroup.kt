package ru.kulishov.markweave.model

import markweave.shared.generated.resources.Res
import markweave.shared.generated.resources.blocks
import markweave.shared.generated.resources.elements
import markweave.shared.generated.resources.strings
import org.jetbrains.compose.resources.StringResource


object MarkdownGroup{
    var groups = mapOf<Int, StringResource>(
        0 to Res.string.blocks,
        1 to Res.string.strings,
        2 to Res.string.elements
    )

}

