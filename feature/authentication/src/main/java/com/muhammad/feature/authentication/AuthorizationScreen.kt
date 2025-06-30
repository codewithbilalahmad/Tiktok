package com.muhammad.feature.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.theme.fontFamily
import com.muhammad.common.ui.AppIconButton
import com.muhammad.core.rippleClickable

@Composable
fun AuthorizationScreen(onBack: () -> Unit,onEmailLoginClick : () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp)
            .padding(top = 16.dp)
            .verticalScroll(
                rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier.rippleClickable {
                    onBack()
                }
            )
            Icon(
                painter = painterResource(R.drawable.ic_question_circle),
                contentDescription = null
            )
        }
        Spacer(Modifier.height(56.dp))
        Text(
            text = stringResource(R.string.login_or_sign_up),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.login_in_to_your_existing_account),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
                color = SubTextColor
            )
        )
        Spacer(Modifier.height(20.dp))
        AuthorizationButton { option ->
            when (option) {
                LoginOption.PHONE_EMAIL_USERNAME -> {
                    onEmailLoginClick()
                }
                else -> Unit
            }
        }
        Spacer(Modifier.weight(1f))
        PrivacyPolicyFooter()
    }
}

@Composable
fun AuthorizationButton(onClick: (LoginOption) -> Unit) {
    LoginOption.entries.forEach { option ->
        AppIconButton(
            text = stringResource(option.title),
            icon = option.icon,
            modifier = Modifier.fillMaxWidth(),
            containerColor = option.containerColor,
            contentColor = option.contentColor,
            onClick = { onClick(option) })
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun PrivacyPolicyFooter() {
    val spanStyle = SpanStyle(
        fontSize = 13.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    )
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.by_continuing_you_agree).plus(" "))
        pushStringAnnotation(
            tag = "annotated_tag", annotation = "terms of service"
        )
        withStyle(style = spanStyle) {
            append(stringResource(id = R.string.terms_of_service))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.and_acknowledge_that_you_have)).plus(" "))
        pushStringAnnotation(
            tag = "annotated_tag", annotation = "privacy policy"
        )
        withStyle(style = spanStyle) {
            append(stringResource(id = R.string.privacy_policy))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.to_learn_how_we_collect)))

    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "annotated_tag", start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                when (annotation.item) {
                    "terms of service" -> {

                    }

                    "privacy policy" -> {

                    }
                }
            }
        }, style = TextStyle(
            color = SubTextColor,
            fontFamily = fontFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    )
    Spacer(Modifier.height(20.dp))
}