package com.muhammad.common.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.muhammad.core.rippleClickable

@Composable
fun AppExpandedText(
    modifier: Modifier = Modifier,
    text: String,
    textThreshold : Int = 100,
    style: TextStyle = MaterialTheme.typography.bodySmall
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val isLongText = text.length > textThreshold
    val truncatedText = if(!expanded && isLongText) text.take(textThreshold) + "..." else text
    val clickable = buildAnnotatedString {
        append(truncatedText)
        if(isLongText){
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground.copy(0.5f))){
                append(if(expanded) "" else "See more")
            }
        }
    }
    Text(text = clickable, style = style, overflow = TextOverflow.Ellipsis, maxLines = Int.MAX_VALUE, modifier = modifier.rippleClickable{
        setExpanded(!expanded)
    })
}