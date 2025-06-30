package com.muhammad.feature.loginwithemailphone.components

import androidx.compose.foundation.border
import com.muhammad.common.theme.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.SeparatorColor
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.theme.fontFamily
import com.muhammad.common.ui.AppButton
import com.muhammad.core.rippleClickable
import com.muhammad.feature.loginwithemailphone.LoginState

@Composable
fun EmailUsernameTabScreen(state: LoginState) {
    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 42.dp, start = 25.dp, end = 28.dp)
                .verticalScroll(rememberScrollState())
        ) {
            EmailField(state = state)
            Spacer(Modifier.height(20.dp))
            PrivacyPolicyText { }
            Spacer(Modifier.height(16.dp))
            AppButton(text = stringResource(R.string.next), onClick = {

            }, enabled = state.email.text.isNotEmpty(), modifier = Modifier.fillMaxWidth())
        }
        DomainSuggestion(onDomainClick = {domain ->
            val localPart = state.email.text.toString().substringBefore("@")
            state.email.edit {
                replace(0 , length, "$localPart$domain")
            }
        }, domains = state.suggestedDomains)
    }
}

@Composable
fun EmailField(state: LoginState) {
    val currentPage = state.settledPage
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        state = state.email,
        textStyle = MaterialTheme.typography.labelLarge,
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = SubTextColor,
            unfocusedIndicatorColor = SubTextColor,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.enter_email_address_or_username),
                style = MaterialTheme.typography.labelLarge,
                color = SubTextColor
            )
        },
        trailingIcon = {
            if (state.email.text.isNotEmpty()) {
                IconButton(onClick = {
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                        contentDescription = null
                    )
                }
            }
        }
    )
    LaunchedEffect(key1 = currentPage) {
        if (currentPage == 1) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun PrivacyPolicyText(onClickAnnotation: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
            )
        ){
            append(stringResource(id = R.string.by_continuing_you_agree))
        }
        pushStringAnnotation(
            tag = "annotated_tag",
            annotation = "terms_of_service"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Red,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.terms_of_service)}") }
        pop()
        withStyle(
            style = SpanStyle(
                color = Color.White,
            )
        ){
            append(stringResource(id = R.string.and_confirm_that_you_have_read))
        }
        pushStringAnnotation(
            tag = "annotated_tag",
            annotation = " privacy_policy"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Red,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.privacy_policy)}") }
        pop()
    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "annotated_tag", start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onClickAnnotation(annotation.item)
            }
        }, style = TextStyle(
            fontFamily = fontFamily
        )
    )
}

@Composable
fun DomainSuggestion(onDomainClick: (String) -> Unit, domains: List<String>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(domains) { domain ->
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                        color = SeparatorColor
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp)
                    .rippleClickable {
                        onDomainClick(domain)
                    }
            ) {
                Text(text = domain, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}