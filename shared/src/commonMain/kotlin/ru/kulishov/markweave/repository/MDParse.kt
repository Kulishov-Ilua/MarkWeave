package ru.kulishov.markweave.repository

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import ru.kulishov.markweave.dictionary.md.KotlinPLP
import ru.kulishov.markweave.dictionary.md.MarkDownDictionary
import ru.kulishov.markweave.model.MarkDownElement

object MDParse {
    fun mdParser(
        input: String,
        baseTextStyle: TextStyle,
        primaryColor: Color,
        backgroundColor:Color
    ):List<@Composable () -> Unit>{
        val layout = mutableListOf<@Composable () -> Unit> ()


        val stringCopy = mutableListOf<String>()
        var actualElement: MarkDownElement? = null

        val fullBlockModifiers = MarkDownDictionary.blockModifiers+ MarkDownDictionary.customBlockModifiers
        val fullStringModifiers = MarkDownDictionary.stringModifiers+ MarkDownDictionary.customStringModifiers
        input.split('\n').map { str ->

            if(actualElement!=null){
                if(actualElement.checkFun!=null&&actualElement.checkFun(str)){
                    stringCopy+=str.replace(actualElement.trigger,"")
                }else{
                    layout+=actualElement.transformer(stringCopy,null,null)
                    stringCopy.clear()
                    actualElement=null
                }
            }else if(str.isNotEmpty()){
                val fElement = fullBlockModifiers[str.split(' ')[0]]
                if(fElement!=null){
                    actualElement=fElement
                    stringCopy+=str.replace(actualElement.trigger,"")
                }else{
                    val fElement = fullStringModifiers[str.split(' ')[0]]
                    layout += if(fElement!=null){
                        fElement.transformer(listOf(str.replace(fElement.trigger,"")),
                            MarkDownDictionary.loadFile,null)
                    }else {
                        {Text(mdTextParses(str,baseTextStyle,primaryColor))}
                    }
                }
            }
        }

        return layout

    }

    fun mdTextParses(text: String, textStyle: TextStyle,
                     primaryColor: Color
    ): AnnotatedString {
        var ret = buildAnnotatedString { }
        var state="0"
        var textCopy = ""
        text.map { ch ->
            when(ch){
                '*' -> {
                    when(state){
                        "0" ->{
                            if(textCopy.isNotEmpty()){
                                ret += buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = textStyle.color
                                        )
                                    ) {
                                        append(textCopy)
                                    }
                                }
                                textCopy=""
                            }
                            state="*"
                        }
                        "*"-> state = "**"
                        "*a" ->{
                            state = "0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = textStyle.fontWeight,
                                        fontFamily = textStyle.fontFamily,
                                        fontStyle = FontStyle.Companion.Italic,
                                        fontSize = textStyle.fontSize,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        "**" -> {}
                        "**a" -> state ="**a*"
                        "**a*" -> {
                            state="0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = FontWeight.Companion.Bold,
                                        fontFamily = textStyle.fontFamily,
                                        fontSize = textStyle.fontSize,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        else -> textCopy+=ch

                    }
                }
                '_' ->{
                    when(state){
                        "0" -> {
                            if(textCopy.isNotEmpty()){
                                ret += buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = textStyle.color
                                        )
                                    ) {
                                        append(textCopy)
                                    }
                                }
                                textCopy=""
                            }
                            state="_"
                        }
                        "_"-> state = "__"
                        "_a" ->{
                            state = "0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = textStyle.fontWeight,
                                        fontFamily = textStyle.fontFamily,
                                        fontStyle = FontStyle.Companion.Italic,
                                        fontSize = textStyle.fontSize,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        "__" -> {}
                        "__a" -> state ="__a_"
                        "__a_" -> {
                            state="0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = FontWeight.Companion.Bold,
                                        fontFamily = textStyle.fontFamily,
                                        fontSize = textStyle.fontSize,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        else -> textCopy+=ch

                    }
                }
                '~' -> {
                    when(state){
                        "0" -> state="~"
                        "~"-> {
                            if(textCopy.isNotEmpty()){
                                ret += buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = textStyle.color
                                        )
                                    ) {
                                        append(textCopy)
                                    }
                                }
                                textCopy=""
                            }
                            state = "~~"
                        }
                        "~~" -> {}
                        "~~a" -> state ="~~a~"
                        "~~a~" -> {
                            state="0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = textStyle.fontWeight,
                                        fontFamily = textStyle.fontFamily,
                                        fontSize = textStyle.fontSize,
                                        textDecoration = TextDecoration.Companion.LineThrough,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        else -> textCopy+=ch

                    }
                }
                '=' -> {
                    when(state){
                        "0" -> state="="
                        "="-> {
                            if(textCopy.isNotEmpty()){
                                ret += buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = textStyle.color
                                        )
                                    ) {
                                        append(textCopy)
                                    }
                                }
                                textCopy=""
                            }
                            state = "=="
                        }
                        "==" -> {}
                        "==a" -> state ="==a="
                        "==a=" -> {
                            state="0"
                            ret += buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontWeight = textStyle.fontWeight,
                                        fontFamily = textStyle.fontFamily,
                                        fontSize = textStyle.fontSize,
                                        background = primaryColor,
                                        color = textStyle.color
                                    )
                                ) {
                                    append(textCopy)
                                }
                            }
                            textCopy=""
                        }
                        else -> textCopy+=ch

                    }
                }

                else -> {
                    when(state){
                        "0" -> textCopy+= ch
                        "*" ->{
                            state="*a"
                            textCopy+=ch
                        }
                        "*a" -> textCopy+=ch
                        "**" -> {
                            state="**a"
                            textCopy+=ch
                        }
                        "**a" -> textCopy+=ch
                        "**a*" -> {
                            state="**a"
                            textCopy+="*$ch"
                        }
                        "_" ->{
                            state="_a"
                            textCopy+=ch
                        }
                        "_a" -> textCopy+=ch
                        "__" -> {
                            state="__a"
                            textCopy+=ch
                        }
                        "__a" -> textCopy+=ch
                        "__a_" -> {
                            state="__a"
                            textCopy+="_$ch"
                        }
                        "~" ->{
                            state="0"
                            textCopy+="~$ch"
                        }
                        "~~" -> {
                            state="~~a"
                            textCopy+=ch
                        }
                        "~~a" -> textCopy+=ch
                        "~~a~" -> {
                            state="~~a"
                            textCopy+="~$ch"
                        }
                        "=" ->{
                            state="0"
                            textCopy+="=$ch"
                        }
                        "==" -> {
                            state="==a"
                            textCopy+=ch
                        }
                        "==a" -> textCopy+=ch
                        "==a=" -> {
                            state="==a"
                            textCopy+="=$ch"
                        }
                    }
                }

            }
        }
        ret += buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = textStyle.fontWeight,
                    fontFamily = textStyle.fontFamily,
                    fontSize = textStyle.fontSize,
                    color = textStyle.color
                )
            ) {
                append("${if (state != "0") state else ""}$textCopy")
            }
        }
        textCopy=""

        return ret
    }

    fun mdCodeParser(
        lang: String,
        text:String,
        textStyle: TextStyle,
        primaryColor: Color
    ): AnnotatedString{
        var ret = buildAnnotatedString {}
        when(lang){
            "Kotlin", "kotlin", "kt", "Kt" ->{
                // 0 - nothing
                // 1 - fun, method
                // 2 - char,
                // 3 - annotation
                var state = 0
                var buffer=""
                text.split(' ').map { word ->


                    val type:Int? = KotlinPLP.words[word]
                    if(type!=null){
                        if(type==0){
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = Color(193,134,101)
                                )){
                                    append("$word ")
                                }
                            }
                            state=1
                        }else if(type<300){
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = Color(193,134,101)
                                )){
                                    append("$word ")
                                }
                            }
                        }
                    }else{

                        for(symb in word){
                            when(symb){
                                '(','{' -> {
                                    if(state==1){
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = Color(85,150,226)
                                            )){
                                                append("$buffer ")
                                            }
                                            buffer=symb.toString()
                                        }
                                        state=0
                                    }else if(symb=='('&&state==0){
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = Color(85,150,226)
                                            )){
                                                append("$buffer ")
                                            }
                                            buffer=symb.toString()
                                        }
                                    }else if(state==3){
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = Color(152,148,85)
                                            )){
                                                append("$buffer ")
                                            }
                                            buffer=symb.toString()
                                        }
                                        state=0
                                    }
                                    else{
                                        buffer+=symb
                                    }
                                }
                                '.' -> {
                                    if(state==0){
                                        state=1
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = primaryColor
                                            )){
                                                append("$buffer.")
                                            }
                                            buffer=""
                                        }
                                    }else{
                                        buffer+=symb
                                    }

                                }
                                '"', '\'' -> {
                                    if(state!=2){
                                        state=2
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = primaryColor
                                            )){
                                                append(buffer)
                                            }
                                            buffer=symb.toString()
                                        }
                                    }else{
                                        state=0
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = Color(95,139,96)
                                            )){
                                                append("$buffer\" ")
                                            }
                                            buffer=""
                                        }
                                    }
                                }
                                '@' ->{
                                    if (state==0){
                                        state=3
                                        ret+= buildAnnotatedString {
                                            withStyle(SpanStyle(
                                                fontWeight = textStyle.fontWeight,
                                                fontFamily = textStyle.fontFamily,
                                                fontSize = textStyle.fontSize,
                                                color = primaryColor
                                            )){
                                                append("$buffer ")
                                            }
                                            buffer=symb.toString()
                                        }
                                    }else{
                                        buffer+=symb
                                    }


                                }else -> {
                                buffer+= symb
                            }

                            }

                        }
                        if(buffer!=""){
                            when(state){
                                0 -> {
                                    ret+= buildAnnotatedString {
                                        withStyle(SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = primaryColor
                                        )){
                                            append("$buffer ")
                                        }
                                        buffer=""
                                    }
                                }
                                1-> {
                                    ret+= buildAnnotatedString {
                                        withStyle(SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = primaryColor
                                        )){
                                            append("$buffer ")
                                        }
                                        buffer=""
                                    }
                                    state=0
                                }
                                2->{

                                }
                                3->{
                                    ret+= buildAnnotatedString {
                                        withStyle(SpanStyle(
                                            fontWeight = textStyle.fontWeight,
                                            fontFamily = textStyle.fontFamily,
                                            fontSize = textStyle.fontSize,
                                            color = Color(152,148,85)
                                        )){
                                            append("$buffer ")
                                        }
                                        buffer=""
                                    }
                                    state=0
                                }
                            }
                        }
                    }





                }
                if(buffer!=""){
                    when(state){
                        0 -> {
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = primaryColor
                                )){
                                    append("$buffer ")
                                }
                                buffer=""
                            }
                        }
                        1-> {
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = primaryColor
                                )){
                                    append("$buffer ")
                                }
                                buffer=""
                            }
                            state=0
                        }
                        2->{
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = Color(95,139,96)
                                )){
                                    append("$buffer ")
                                }
                                buffer=""
                            }
                            state=0
                        }
                        3->{
                            ret+= buildAnnotatedString {
                                withStyle(SpanStyle(
                                    fontWeight = textStyle.fontWeight,
                                    fontFamily = textStyle.fontFamily,
                                    fontSize = textStyle.fontSize,
                                    color = Color(152,148,85)
                                )){
                                    append("$buffer ")
                                }
                                buffer=""
                            }
                            state=0
                        }
                    }
                }

            }
            else -> {
                ret += buildAnnotatedString {
                    withStyle(SpanStyle(
                        fontWeight = textStyle.fontWeight,
                        fontFamily = textStyle.fontFamily,
                        fontSize = textStyle.fontSize,
                        color = textStyle.color
                    )){
                        append(text)
                    }

                }
            }
        }

        return ret
    }
}