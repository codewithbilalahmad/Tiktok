package com.muhammad.feature.loginwithemailphone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.muhammad.common.theme.R
import com.muhammad.common.theme.SubTextColor
import com.muhammad.common.theme.fontFamily
import com.muhammad.common.ui.AppButton
import com.muhammad.feature.loginwithemailphone.LoginState

@Composable
fun PhoneTabScreen(state: LoginState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp)
            .padding(top = 50.dp)
    ) {
        PhoneNumberTextField(state = state)
        Spacer(Modifier.height(8.dp))
        InfoText { }
        Spacer(Modifier.height(16.dp))
        AppButton(
            text = stringResource(R.string.send_code),
            modifier = Modifier.fillMaxWidth(), enabled = state.phoneNumber.text.isNotEmpty()
        ) {

        }
    }
}

@Composable
fun PhoneNumberTextField(
    state: LoginState,
) {
    val currentPage = state.settledPage
    val focusRequester = remember { FocusRequester() }
    TextField(
        state = state.phoneNumber,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.labelLarge,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        lineLimits = TextFieldLineLimits.SingleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = SubTextColor,
            unfocusedIndicatorColor = SubTextColor,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.labelLarge
            )
        },
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = state.dialCode, style = MaterialTheme.typography.labelMedium)
                Icon(painter = painterResource(R.drawable.ic_down_more), contentDescription = null)
                VerticalDivider(
                    color = SubTextColor,
                    thickness = 1.dp,
                    modifier = Modifier.height(25.dp)
                )
            }
        },
        trailingIcon = {
            if (state.phoneNumber.text.isNotEmpty()) {
                IconButton(onClick = {
                    state.phoneNumber.edit {
                        delete(0, length)
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                        contentDescription = null
                    )
                }
            }
        })
    LaunchedEffect(currentPage) {
        if (currentPage == 0) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun InfoText(onLearnMoreClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.phone_number_info))
        pushStringAnnotation(
            tag = "annotated_tag",
            annotation = "learn_more"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Red,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.learn_more)}") }
        pop()
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(
            tag = "annotated_tag", start = offset, end = offset
        ).firstOrNull()?.let { annotation ->
            if (annotation.item == "learn_more") onLearnMoreClick()
        }
    }, style = TextStyle(color = SubTextColor, fontFamily = fontFamily))
}