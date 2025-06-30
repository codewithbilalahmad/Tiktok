package com.muhammad.feature.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muhammad.common.theme.R
import com.muhammad.common.ui.AppBottomSheet
import com.muhammad.common.ui.AppDivider
import com.muhammad.common.ui.AppLoading
import com.muhammad.core.dropLastGrapheme
import com.muhammad.core.rippleClickable
import com.muhammad.core.toFormattedDate
import com.muhammad.data.domain.model.Comment
import com.muhammad.feature.home.HomeEvent
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    showCommentBottomSheet: Boolean,
    commentsList: List<Comment>, isCommentLoading: Boolean, isMoreCommentLoading: Boolean,
    onDismiss: () -> Unit, comment: String,
    onAction: (HomeEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val highLightedEmojis = listOf("😁", "🥰", "😂", "😲", "😏", "😅")
    AppBottomSheet(
        showBottomSheet = showCommentBottomSheet,
        sheetState = sheetState,
        onDismiss = {
            onDismiss()
        }) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CommentHeader(onClose = {
                    onDismiss()
                })
                AppDivider(color = Color.White)
                CommentBody(
                    commentsList = commentsList,
                    isCommentLoading = isCommentLoading,
                    isMoreCommentLoading = isMoreCommentLoading, onAction = onAction
                )
                CommentFooter(
                    highLightedEmojis = highLightedEmojis,
                    comment = comment,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun CommentHeader(onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Comments",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold), modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = {
                onClose()
            }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(2.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = null
            )
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
private fun CommentFooter(
    highLightedEmojis: List<String>,
    comment: String,
    onAction: (HomeEvent) -> Unit,
) {
    var isEmojiListShow by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 5 })
    val keyboardIsVisible = WindowInsets.isImeVisible
    val emojiPickerVisible = isEmojiListShow && !keyboardIsVisible
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()
    var commentTextFieldShape by remember { mutableStateOf(CircleShape) }
    var showCommentTextFieldTrailingIcons by remember { mutableStateOf(true) }
    var isCommentTextFieldFocused by remember { mutableStateOf(false) }
    LaunchedEffect(isEmojiListShow) {
        commentTextFieldShape = if (isEmojiListShow) {
            RoundedCornerShape(16.dp)
        } else {
            CircleShape
        }
    }
    LaunchedEffect(keyboardIsVisible) {
        if (!keyboardIsVisible) {
            focusManager.clearFocus()
            isCommentTextFieldFocused = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .shadow(elevation = (0.4).dp)
            .padding(horizontal = 8.dp)
    ) {
        AnimatedVisibility(
            !emojiPickerVisible,
            enter = expandVertically(animationSpec = tween(300, easing = FastOutSlowInEasing)),
            exit = shrinkVertically(animationSpec = tween(300, easing = FastOutSlowInEasing))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                highLightedEmojis.forEach { emoji ->
                    Text(text = emoji, fontSize = 25.sp, modifier = Modifier.rippleClickable {
                        val newComment = comment + emoji
                        onAction(HomeEvent.OnCommentChange(newComment))
                    })
                }
            }
        }
        if (emojiPickerVisible) {
            Spacer(Modifier.height(6.dp))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                model = "https://avatars.githubusercontent.com/u/160990871?s=96&v=4"
            )
            CommentTextField(
                value = comment,
                shape = commentTextFieldShape,
                onValueChange = { newValue ->
                    onAction(HomeEvent.OnCommentChange(newValue))
                }, focusRequester = focusRequester,
                onEmojiOrKeyboardToggle = {
                    if (isEmojiListShow) {
                        isEmojiListShow = false
                        keyboardController?.show()
                        focusRequester.requestFocus()
                        isCommentTextFieldFocused = true
                    } else {
                        isEmojiListShow = true
                        showCommentTextFieldTrailingIcons = false
                    }
                },
                showTrailingSection = showCommentTextFieldTrailingIcons,
                onTagClick = {},
                onSendComment = {}, isKeyboardShow = emojiPickerVisible,
                showSendButton = !showCommentTextFieldTrailingIcons && comment.isNotEmpty() && !keyboardIsVisible && !isEmojiListShow,
                modifier = Modifier
                    .weight(1f)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            isCommentTextFieldFocused = true
                            commentTextFieldShape = RoundedCornerShape(16.dp)
                            showCommentTextFieldTrailingIcons = false
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        } else if (!event.isFocused && !isEmojiListShow) {
                            isCommentTextFieldFocused = false
                            showCommentTextFieldTrailingIcons = true
                        }
                    })
        }
        if (isCommentTextFieldFocused || isEmojiListShow) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_email),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = ImageVector.vectorResource(if (keyboardIsVisible) R.drawable.ic_emoji else R.drawable.ic_keyboard),
                    contentDescription = null, modifier = Modifier.rippleClickable {
                        if (keyboardIsVisible) {
                            keyboardController?.hide()
                            isEmojiListShow = true
                        } else {
                            keyboardController?.show()
                            isEmojiListShow = false
                        }
                    },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FilledTonalIconButton(
                    onClick = {}, colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                    ), shape = CircleShape, modifier = Modifier.size(
                        IconButtonDefaults.smallContainerSize(
                            IconButtonDefaults.IconButtonWidthOption.Narrow
                        )
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                        contentDescription = null, modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        if (emojiPickerVisible) {
            EmojiPickerComment(pagerState, onEmojiClick = { emoji ->
                val newComment = comment + emoji
                onAction(HomeEvent.OnCommentChange(newComment))
            }, onClear = {
                if (comment.isNotEmpty()) {
                    val newComment = comment.dropLastGrapheme()
                    onAction(HomeEvent.OnCommentChange(newComment))
                }
            })
        }
    }
}

@Composable
fun ColumnScope.CommentBody(
    commentsList: List<Comment>,
    isCommentLoading: Boolean, onAction: (HomeEvent) -> Unit,
    isMoreCommentLoading: Boolean = false,
) {
    val listState = rememberLazyListState()
    if (isCommentLoading) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f), contentAlignment = Alignment.Center) {
            AppLoading(modifier = Modifier)
        }
    } else {
        LazyColumn(modifier = Modifier.weight(1f), state = listState) {
            items(commentsList.size) { index ->
                CommentItem(comment = commentsList[index])
            }
            if (isMoreCommentLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AppLoading(Modifier)
                    }
                }
            }
        }
        val shouldPaginate by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem != null && lastVisibleItem.index >= commentsList.lastIndex
            }
        }
        LaunchedEffect(shouldPaginate) {
            if(shouldPaginate && !isMoreCommentLoading){
                onAction(HomeEvent.OnPaginateComments)
            }
        }
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = comment.profileImageUrl, contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentDescription = null
        )
        Spacer(Modifier.width(6.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = comment.username,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.comment,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.commentedAt.toFormattedDate(),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Reply",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_like_outline),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = comment.likeCount.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_dislike_outline),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.width(40.dp), thickness = 1.dp)
                Spacer(Modifier.width(4.dp))
                if (comment.replyCount != 0) {
                    Text(
                        text = "View ${comment.replyCount} Reply",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmojiPickerComment(
    pagerState: PagerState,
    onEmojiClick: (String) -> Unit,
    onClear: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (index) {
                    0 -> EmojiListFirst(onEmojiClick = onEmojiClick, onClear = onClear)
                    1 -> EmojiListSecond(onEmojiClick = onEmojiClick, onClear = onClear)
                    2 -> EmojiListThird(onEmojiClick = onEmojiClick, onClear = onClear)
                    3 -> EmojiListFourth(onEmojiClick = onEmojiClick, onClear = onClear)
                    4 -> EmojiListFifth(onEmojiClick = onEmojiClick, onClear = onClear)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        DotIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            dotSize = 5,
            currentDot = pagerState.currentPage
        )
    }
}

@Composable
private fun EmojiListFirst(onEmojiClick: (String) -> Unit, onClear: () -> Unit) {
    EmojiRow(emojis = listOf("😀", "😁", "😂", "🤣", "😃", "😄", "😅"), onEmojiClick = onEmojiClick)
    EmojiRow(emojis = listOf("😆", "😉", "😊", "😋", "😎", "😍", "😘"), onEmojiClick = onEmojiClick)
    EmojiRowWithClearIcon(
        emojis = listOf("🥰", "😗", "😙", "😚", "🤑", "🙂"),
        onEmojiClick = onEmojiClick,
        onClearClick = onClear
    )
}

@Composable
private fun EmojiListSecond(onEmojiClick: (String) -> Unit, onClear: () -> Unit) {
    EmojiRow(emojis = listOf("🤩", "🤔", "🤨", "😐", "😑", "😶", "🙄"), onEmojiClick = onEmojiClick)
    EmojiRow(emojis = listOf("😏", "😣", "😥", "😮", "🤐", "😯", "😪"), onEmojiClick = onEmojiClick)
    EmojiRowWithClearIcon(
        emojis = listOf("😫", "🥱", "😴", "😌", "😛", "😜"),
        onEmojiClick = onEmojiClick,
        onClearClick = onClear
    )
}

@Composable
private fun EmojiListThird(onEmojiClick: (String) -> Unit, onClear: () -> Unit) {
    EmojiRow(emojis = listOf("😒", "😓", "😔", "😕", "🙃", "🤯", "😖"), onEmojiClick = onEmojiClick)
    EmojiRow(emojis = listOf("😷", "🤒", "🤕", "🤑", "🤠", "😈", "👿"), onEmojiClick = onEmojiClick)
    EmojiRowWithClearIcon(
        emojis = listOf("👻", "💀", "☠", "👽", "👾", "🤖"),
        onEmojiClick = onEmojiClick,
        onClearClick = onClear
    )
}

@Composable
private fun EmojiListFourth(onEmojiClick: (String) -> Unit, onClear: () -> Unit) {
    EmojiRow(emojis = listOf("😺", "😸", "😹", "😻", "😼", "😽", "🙀"), onEmojiClick = onEmojiClick)
    EmojiRow(emojis = listOf("😿", "😾", "🥶", "👶", "👧", "👦", "👩"), onEmojiClick = onEmojiClick)
    EmojiRowWithClearIcon(
        emojis = listOf("👨", "👩‍🦱", "👨‍🦱", "👩‍🦳", "👨‍🦳", "👩‍🦲"),
        onEmojiClick = onEmojiClick,
        onClearClick = onClear
    )
}

@Composable
private fun EmojiListFifth(onEmojiClick: (String) -> Unit, onClear: () -> Unit) {
    EmojiRow(
        emojis = listOf("👩‍⚕️", "👨‍⚕️", "👩‍🎓", "👨‍🎓", "👩‍🏫", "👨‍🏫", "👩‍🏭"),
        onEmojiClick = onEmojiClick
    )
    EmojiRow(
        emojis = listOf("👨‍🏭", "👩‍💻", "👨‍💻", "👩‍🎤", "👨‍🎤", "👩‍🎨", "👨‍🎨"),
        onEmojiClick = onEmojiClick
    )
    EmojiRowWithClearIcon(
        emojis = listOf("👩‍🚀", "👨‍🚀", "👩‍🚒", "👨‍🚒", "👮‍♀️", "👮‍♂️"),
        onEmojiClick = onEmojiClick,
        onClearClick = onClear
    )
}

@Composable
private fun EmojiRow(emojis: List<String>, onEmojiClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        emojis.forEach { emoji ->
            Text(
                text = emoji,
                fontSize = 25.sp,
                modifier = Modifier.rippleClickable { onEmojiClick(emoji) })
        }
    }
}

@Composable
private fun EmojiRowWithClearIcon(
    emojis: List<String>,
    onEmojiClick: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        emojis.forEach { emoji ->
            Text(text = emoji, fontSize = 25.sp, modifier = Modifier.rippleClickable {
                onEmojiClick(emoji)
            })
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
            contentDescription = null, modifier = Modifier.rippleClickable {
                onClearClick()
            }
        )
    }
}