package ru.kulishov.markweave.dictionary.md

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.markweave.model.ByteFile
import ru.kulishov.markweave.model.CustomData
import ru.kulishov.markweave.model.MarkDownElement
import ru.kulishov.markweave.model.MarkDownElementViewData
import ru.kulishov.markweave.model.Quote
import ru.kulishov.markweave.repository.MDParse
import ru.kulishov.markweave.ui.md.CodeBlock
import ru.kulishov.markweave.ui.md.InfoBlock
import kotlin.collections.get
import kotlin.collections.plusAssign
import kotlin.compareTo

object MarkDownDictionary {
    var loadFile: (suspend (Long) -> CustomData?)? = null
    var onClickable: ((ByteFile) -> Unit)? = null
    var baseTextStyle = TextStyle(fontSize = 16.sp)
    var primaryColor = Color.Blue
    var backgroundColor = Color.White


    var blockModifiers = mapOf<String, MarkDownElement>(
        ">" to MarkDownElement(
            ">", 0,
            viewData = MarkDownElementViewData(
                "Цитата",
                {
                    Text(
                        ">",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "> [!quote]\n>\n" }
            ), transformer = { arr, getFile, onNavigate ->
                var actualQuote = Quote(text = buildAnnotatedString { })
                if (arr[0].length > 11 && arr[0].substring(1, 12) == "[!question]") {
                    actualQuote =
                        Quote(
                            name = arr[0].substring(12),
                            type = "question",
                            text = buildAnnotatedString { })
                } else {

                    if (arr[0].length > 12 && arr[0].substring(2, 13) == "[!question]") {
                        actualQuote =
                            Quote(
                                name = arr[0].substring(13),
                                type = "question",
                                text = buildAnnotatedString { })
                    } else {
                        //----------------------------------------------------------------------
                        if (arr[0].length > 11 && arr[0].substring(1, 12) == "[!abstract]") {
                            actualQuote = Quote(
                                name = arr[0].substring(12),
                                type = "abstract",
                                text = buildAnnotatedString { }
                            )
                        } else {
                            if (arr[0].length > 12 && arr[0].substring(2, 13) == "[!abstract]") {
                                actualQuote = Quote(
                                    name = arr[0].substring(13),
                                    type = "abstract",
                                    text = buildAnnotatedString { }
                                )
                            } else {
                                //----------------------------------------------------------------------
                                if (arr[0].length > 10 && arr[0].substring(
                                        1,
                                        11
                                    ) == "[!example]"
                                ) {
                                    actualQuote = Quote(
                                        name = arr[0].substring(11),
                                        type = "example",
                                        text = buildAnnotatedString { }
                                    )
                                } else {

                                    if (arr[0].length > 11 && arr[0].substring(
                                            2,
                                            12
                                        ) == "[!example]"
                                    ) {
                                        actualQuote = Quote(
                                            name = arr[0].substring(12),
                                            type = "example",
                                            text = buildAnnotatedString { }
                                        )
                                    } else {
                                        //----------------------------------------------------------------------
                                        if (arr[0].length > 10 && arr[0].substring(
                                                1,
                                                11
                                            ) == "[!warning]"
                                        ) {
                                            actualQuote = Quote(
                                                name = arr[0].substring(11),
                                                type = "warning",
                                                text = buildAnnotatedString { }
                                            )
                                        } else {
                                            if (arr[0].length > 11 && arr[0].substring(
                                                    2,
                                                    12
                                                ) == "[!warning]"
                                            ) {
                                                actualQuote = Quote(
                                                    name = arr[0].substring(12),
                                                    type = "warning",
                                                    text = buildAnnotatedString { }
                                                )
                                            } else {
                                                //----------------------------------------------------------------------
                                                if (arr[0].length > 9 && arr[0].substring(
                                                        1,
                                                        10
                                                    ) == "[!danger]"
                                                ) {
                                                    actualQuote = Quote(
                                                        name = arr[0].substring(10),
                                                        type = "danger",
                                                        text = buildAnnotatedString { }
                                                    )
                                                } else {
                                                    if (arr[0].length > 10 && arr[0].substring(
                                                            2,
                                                            11
                                                        ) == "[!danger]"
                                                    ) {
                                                        actualQuote = Quote(
                                                            name = arr[0].substring(11),
                                                            type = "danger",
                                                            text = buildAnnotatedString { }
                                                        )
                                                    } else {
                                                        //----------------------------------------------------------------------
                                                        if (arr[0].length > 10 && arr[0].substring(
                                                                1,
                                                                11
                                                            ) == "[!success]"
                                                        ) {
                                                            actualQuote = Quote(
                                                                name = arr[0].substring(11),
                                                                type = "success",
                                                                text = buildAnnotatedString { }
                                                            )
                                                        } else {
                                                            if (arr[0].length > 11 && arr[0].substring(
                                                                    2,
                                                                    12
                                                                ) == "[!success]"
                                                            ) {
                                                                actualQuote = Quote(
                                                                    name = arr[0].substring(12),
                                                                    type = "success",
                                                                    text = buildAnnotatedString { }
                                                                )
                                                            } else {
                                                                //----------------------------------------------------------------------
                                                                if (arr[0].length > 10 && arr[0].substring(
                                                                        1,
                                                                        11
                                                                    ) == "[!failure]"
                                                                ) {
                                                                    actualQuote = Quote(
                                                                        name = arr[0].substring(
                                                                            11
                                                                        ),
                                                                        type = "failure",
                                                                        text = buildAnnotatedString { }
                                                                    )
                                                                } else {
                                                                    if (arr[0].length > 11 && arr[0].substring(
                                                                            2,
                                                                            12
                                                                        ) == "[!failure]"
                                                                    ) {
                                                                        actualQuote = Quote(
                                                                            name = arr[0].substring(
                                                                                12
                                                                            ),
                                                                            type = "failure",
                                                                            text = buildAnnotatedString { }
                                                                        )
                                                                    } else {
                                                                        //----------------------------------------------------------------------
                                                                        if (arr[0].length > 7 && arr[0].substring(
                                                                                1,
                                                                                8
                                                                            ) == "[!todo]"
                                                                        ) {
                                                                            actualQuote =
                                                                                Quote(
                                                                                    name = arr[0].substring(
                                                                                        8
                                                                                    ),
                                                                                    type = "todo",
                                                                                    text = buildAnnotatedString { }
                                                                                )
                                                                        } else {
                                                                            if (arr[0].length > 8 && arr[0].substring(
                                                                                    2,
                                                                                    9
                                                                                ) == "[!todo]"
                                                                            ) {
                                                                                actualQuote =
                                                                                    Quote(
                                                                                        name = arr[0].substring(
                                                                                            9
                                                                                        ),
                                                                                        type = "todo",
                                                                                        text = buildAnnotatedString { }
                                                                                    )
                                                                            } else {
                                                                                //----------------------------------------------------------------------
                                                                                if (arr[0].length > 8 && arr[0].substring(
                                                                                        1,
                                                                                        9
                                                                                    ) == "[!quote]"
                                                                                ) {
                                                                                    actualQuote =
                                                                                        Quote(
                                                                                            name = arr[0].substring(
                                                                                                9
                                                                                            ),
                                                                                            type = "quote",
                                                                                            text = buildAnnotatedString { }
                                                                                        )
                                                                                } else {
                                                                                    if (arr[0].length > 9 && arr[0].substring(
                                                                                            2,
                                                                                            10
                                                                                        ) == "[!quote]"
                                                                                    ) {
                                                                                        actualQuote =
                                                                                            Quote(
                                                                                                name = arr[0].substring(
                                                                                                    10
                                                                                                ),
                                                                                                type = "quote",
                                                                                                text = buildAnnotatedString { }
                                                                                            )
                                                                                    } else {
                                                                                        //----------------------------------------------------------------------
                                                                                        if (arr[0].length > 6 && arr[0].substring(
                                                                                                1,
                                                                                                7
                                                                                            ) == "[!tip]"
                                                                                        ) {
                                                                                            actualQuote =
                                                                                                Quote(
                                                                                                    name = arr[0].substring(
                                                                                                        7
                                                                                                    ),
                                                                                                    type = "tip",
                                                                                                    text = buildAnnotatedString { }
                                                                                                )
                                                                                        } else {
                                                                                            if (arr[0].length > 7 && arr[0].substring(
                                                                                                    2,
                                                                                                    8
                                                                                                ) == "[!tip]"
                                                                                            ) {
                                                                                                actualQuote =
                                                                                                    Quote(
                                                                                                        name = arr[0].substring(
                                                                                                            8
                                                                                                        ),
                                                                                                        type = "tip",
                                                                                                        text = buildAnnotatedString { }
                                                                                                    )
                                                                                            } else {
                                                                                                //----------------------------------------------------------------------
                                                                                                if (arr[0].length > 6 && arr[0].substring(
                                                                                                        1,
                                                                                                        7
                                                                                                    ) == "[!bug]"
                                                                                                ) {
                                                                                                    actualQuote =
                                                                                                        Quote(
                                                                                                            name = arr[0].substring(
                                                                                                                7
                                                                                                            ),
                                                                                                            type = "bug",
                                                                                                            text = buildAnnotatedString { }
                                                                                                        )
                                                                                                } else {
                                                                                                    if (arr[0].length > 7 && arr[0].substring(
                                                                                                            2,
                                                                                                            8
                                                                                                        ) == "[!bug]"
                                                                                                    ) {
                                                                                                        actualQuote =
                                                                                                            Quote(
                                                                                                                name = arr[0].substring(
                                                                                                                    8
                                                                                                                ),
                                                                                                                type = "bug",
                                                                                                                text = buildAnnotatedString { }
                                                                                                            )
                                                                                                    } else {
                                                                                                        //----------------------------------------------------------------------
                                                                                                        if (arr[0].length > 7 && arr[0].substring(
                                                                                                                1,
                                                                                                                8
                                                                                                            ) == "[!info]"
                                                                                                        ) {
                                                                                                            actualQuote =
                                                                                                                Quote(
                                                                                                                    name = arr[0].substring(
                                                                                                                        8
                                                                                                                    ),
                                                                                                                    type = "info",
                                                                                                                    text = buildAnnotatedString { }
                                                                                                                )
                                                                                                        } else {
                                                                                                            if (arr[0].length > 8 && arr[0].substring(
                                                                                                                    2,
                                                                                                                    9
                                                                                                                ) == "[!info]"
                                                                                                            ) {
                                                                                                                actualQuote =
                                                                                                                    Quote(
                                                                                                                        name = arr[0].substring(
                                                                                                                            9
                                                                                                                        ),
                                                                                                                        type = "info",
                                                                                                                        text = buildAnnotatedString { }
                                                                                                                    )
                                                                                                            } else {
                                                                                                                actualQuote =
                                                                                                                    Quote(
                                                                                                                        name = arr[0].substring(
                                                                                                                            1
                                                                                                                        ),
                                                                                                                        type = "info",
                                                                                                                        text = buildAnnotatedString { }
                                                                                                                    )
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (nStr in 1 until arr.size) {
                    if (arr[nStr].length > 1) {
                        actualQuote.text += MDParse.mdTextParses(
                            arr[nStr].substring(1),
                            baseTextStyle,
                            primaryColor
                        )
                    }
                }
                return@MarkDownElement {
                    InfoBlock(
                        actualQuote.name,
                        actualQuote.type,
                        actualQuote.text
                    )
                }

            },
            checkFun = { str ->
                return@MarkDownElement if (str.isNotEmpty() && str[0] == '>') true else false
            }),
        "```" to MarkDownElement(
            "```", 0, transformer =
                { arr, getFile, onNavigate ->
                    var lang: String = ""
                    if (arr[0].contains("kotlin") ||
                        arr[0].contains("Kotlin") ||
                        arr[0].contains("kt") ||
                        arr[0].contains("KT")
                    ) {
                        lang = "kotlin"
                    } else {
                        lang = arr[0].replace("'''", "").replace(" ", "")
                    }
                    var code = """"""
                    for (sInd in 1..arr.size - 1) {
                        code += arr[sInd] + '\n'
                    }
                    return@MarkDownElement {
                        CodeBlock(
                            lang,
                            code,
                            backgroundColor,
                            primaryColor,
                            baseTextStyle
                        )
                    }
                },
            viewData = MarkDownElementViewData(
                "Блок кода",
                {
                    Text(
                        "{}",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "```\n\n```\n" }),
            checkFun = { str ->
                return@MarkDownElement if (str.length > 2 && str.substring(
                        0,
                        3
                    ) == "```"
                ) false else true
            }
        )
    )
    var customBlockModifiers = mapOf<String, MarkDownElement>()
    var stringModifiers = mapOf<String, MarkDownElement>(
        "#" to MarkDownElement(
            "#",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 1",
                {
                    Text(
                        "h1",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "# " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 12).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "##" to MarkDownElement(
            "##",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 2",
                {
                    Text(
                        "h2",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "## " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 10).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "###" to MarkDownElement(
            "###",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 3",
                {
                    Text(
                        "h3",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "### " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 8).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "####" to MarkDownElement(
            "####",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 4",
                {
                    Text(
                        "h4",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "#### " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 6).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "#####" to MarkDownElement(
            "#####",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 5",
                {
                    Text(
                        "h5",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "##### " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 4).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "######" to MarkDownElement(
            "######",
            1,
            viewData = MarkDownElementViewData(
                "Заголовок 6",
                {
                    Text(
                        "h6",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "###### " }),
            transformer = { str, getFile, onNavigate ->
                return@MarkDownElement {
                    Text(
                        text = MDParse.mdTextParses(
                            str[0],
                            textStyle = baseTextStyle.copy(
                                fontSize = (baseTextStyle.fontSize.value + 2).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            primaryColor
                        )
                    )
                }
            }),
        "-" to MarkDownElement(
            "-",
            1,
            viewData = MarkDownElementViewData(
                "Список",
                {
                    Text(
                        "-",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "- " }),
            transformer = { str, getFile, onNavigate ->
                val nSplit = str[0].split(' ')
                if (nSplit.size > 3 && nSplit[1] == "[" && nSplit[2] == "]") {
                    return@MarkDownElement {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Checkbox(checked = false, {})
                            Text(
                                text = MDParse.mdTextParses(
                                    str[0].replace("[ ]", ""),
                                    textStyle = baseTextStyle,
                                    primaryColor
                                ),
                            )
                        }
                    }
                } else if (nSplit.size > 2 && nSplit[1] == "[x]") {
                    return@MarkDownElement {
                        Checkbox(checked = true, {})
                        Text(
                            text = MDParse.mdTextParses(
                                str[0].replace("[x]", ""),
                                textStyle = baseTextStyle,
                                primaryColor
                            ),
                        )
                    }
                } else {
                    return@MarkDownElement {
                        Text(
                            text = MDParse.mdTextParses(
                                str[0],
                                textStyle = baseTextStyle,
                                primaryColor
                            ),
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }

            }),
        "---" to MarkDownElement(
            "---",
            1,
            viewData = MarkDownElementViewData(
                "Линия",
                {
                    Text(
                        "--",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "---\n" }),
            transformer = { str, getFile, onNavigate ->
                if (str[0].length < 2) return@MarkDownElement {
                    Box(
                        Modifier.fillMaxWidth().height(2.dp).clip(CircleShape).background(
                            baseTextStyle.color
                        )
                    )
                }
                else {
                    return@MarkDownElement {
                        Text(
                            text = MDParse.mdTextParses(
                                str[0],
                                textStyle = baseTextStyle,
                                primaryColor
                            ),
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }
            }),
        "----" to MarkDownElement(
            "----",
            1,
            viewData = MarkDownElementViewData(
                "Линия",
                {
                    Text(
                        "--",
                        style = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value + 4).sp)
                    )
                },
                { return@MarkDownElementViewData "---\n" }),
            transformer = { str, getFile, onNavigate ->
                if (str[0].length < 2) return@MarkDownElement {
                    Box(
                        Modifier.fillMaxWidth().height(2.dp).clip(CircleShape).background(
                            baseTextStyle.color
                        )
                    )
                }
                else {
                    return@MarkDownElement {
                        Text(
                            text = MDParse.mdTextParses(
                                str[0],
                                textStyle = baseTextStyle,
                                primaryColor
                            ),
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }
            })


    )

    var customStringModifiers = mapOf<String, MarkDownElement>()

    var lexModifiers = mapOf<String, MarkDownElement>(

    )

    fun setBaseParams(
        textStyle: TextStyle,
        primary: Color,
        background: Color,
        stringFormatter: List<MarkDownElement> = emptyList(),
        blockFormatter: List<MarkDownElement> = emptyList(),
        lexFormatter: List<MarkDownElement> = emptyList(),
        getFileCustom: (suspend (Long) -> CustomData)? = null,
        onClickableCustom: ((ByteFile) -> Unit)? = null

    ) {
        baseTextStyle = textStyle
        primaryColor = primary
        backgroundColor = background

        stringFormatter.map { customStringModifiers += mapOf(it.trigger to it) }
        blockFormatter.map { customBlockModifiers += mapOf(it.trigger to it) }

        loadFile = getFileCustom
        onClickable = onClickableCustom
//        lexFormatter.map { lexModifiers+= mapOf(it.trigger to it) }
    }

    fun specialFormatter(
        stringFormatter: List<MarkDownElement> = emptyList(),
        blockFormatter: List<MarkDownElement> = emptyList(),
        lexFormatter: List<MarkDownElement> = emptyList(),
    ) {
        stringFormatter.map { stringModifiers += mapOf(it.trigger to it) }
        blockFormatter.map { blockModifiers += mapOf(it.trigger to it) }
        lexFormatter.map { lexModifiers += mapOf(it.trigger to it) }
    }

    fun setGetFileFun(getFileCustom: (suspend (Long) -> CustomData?)? = null) {
        loadFile = getFileCustom
    }
}