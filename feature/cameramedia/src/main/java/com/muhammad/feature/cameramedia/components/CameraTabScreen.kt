package com.muhammad.feature.cameramedia.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.muhammad.common.theme.LightGreenColor
import com.muhammad.common.theme.R
import com.muhammad.common.theme.TealColor
import com.muhammad.common.ui.CaptureButton
import com.muhammad.core.IntentUtils.openAppSettings
import com.muhammad.core.rippleClickable
import com.muhammad.feature.cameramedia.CameraCaptureOptions
import com.muhammad.feature.cameramedia.CameraController
import com.muhammad.feature.cameramedia.PermissionType
import com.muhammad.feature.cameramedia.Tabs
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun CameraScreen(
    cameraOpenType: Tabs, onBack: () -> Unit,
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val activity = (context as Activity)
    var isMicroPhonePermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var isCameraPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var cameraPermissonShouldShowRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            )
        )
    }
    var micPermissonShouldShowRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.RECORD_AUDIO
            )
        )
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isCameraPermissionGranted = isGranted
        cameraPermissonShouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.CAMERA
        )
    }
    val micPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isMicroPhonePermissionGranted = isGranted
        micPermissonShouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.RECORD_AUDIO
        )
    }
    LaunchedEffect(lifeCycleOwner) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            if (cameraPermissonShouldShowRationale) {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    val fileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {

        }
    Column(modifier = Modifier.fillMaxSize()) {
        if (isCameraPermissionGranted) {
            CameraPreview(cameraOpenType = cameraOpenType, onCancel = onBack, onClickOpenFile = {
                fileLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            })
        } else {
            CameraMicrophoneAccessScreen(
                isMicroPhonePermissionGranted = isMicroPhonePermissionGranted,
                cameraOpenType = cameraOpenType,
                onCancel = onBack,
                onClickOpenFile = {
                    fileLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                },
                onGrantPermission = { type ->
                    when (type) {
                        PermissionType.CAMERA -> {
                            if (cameraPermissonShouldShowRationale) {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            } else {
                                context.openAppSettings()
                            }
                        }

                        PermissionType.MICROPHONE -> {
                            if (micPermissonShouldShowRationale) {
                                micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            } else {
                                context.openAppSettings()
                            }
                        }
                    }
                })
        }
    }
}

@Composable
fun CameraMicrophoneAccessScreen(
    isMicroPhonePermissionGranted: Boolean,
    cameraOpenType: Tabs,
    onCancel: () -> Unit,
    onClickOpenFile: () -> Unit,
    onGrantPermission: (PermissionType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(TealColor, LightGreenColor)
                )
            )
            .padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                onCancel()
            }, modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.allow_tiktok_to_access_your),
            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "${stringResource(id = R.string.take_photos_record_videos_or_preview)} ${
                stringResource(id = R.string.you_can_change_preference_in_setting)
            }", style = MaterialTheme.typography.labelMedium.copy(
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        Spacer(Modifier.height(30.dp))
        Card(
            modifier = Modifier.fillMaxWidth(0.75f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(0.2f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    18.dp,
                    Alignment.CenterVertically
                )
            ) {
                Row(
                    modifier = Modifier.rippleClickable {
                        onGrantPermission(PermissionType.CAMERA)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.ic_camera), contentDescription = null)
                    Text(
                        text = stringResource(R.string.access_camera),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (!isMicroPhonePermissionGranted) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(
                        modifier = Modifier.rippleClickable {
                            onGrantPermission(PermissionType.MICROPHONE)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_microphone),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(R.string.access_microhpone),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        CameraControllerFooter(
            cameraOpenType = cameraOpenType,
            onEffectClick = {},
            onCaptureClick = {},
            onClickOpenFile = onClickOpenFile,
            isLayoutEnabled = false, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CameraPreview(cameraOpenType: Tabs, onCancel: () -> Unit, onClickOpenFile: () -> Unit) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    var defaultCameraFacing by remember { mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA) }
    val preview = remember { Preview.Builder().build() }
    LaunchedEffect(defaultCameraFacing) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifeCycleOwner, defaultCameraFacing, preview)
        } catch (e: Exception) {
            println(e)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    preview.surfaceProvider = surfaceProvider
                }
            }, modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            CameraControllerFooter(
                cameraOpenType = cameraOpenType,
                onEffectClick = {},
                onCaptureClick = {},
                onClickOpenFile = onClickOpenFile,
                isLayoutEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
            CameraSideControllerSection(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp),
                defaultCameraFacing = defaultCameraFacing
            ) { controller ->
                when (controller) {
                    CameraController.FLIP -> {
                        defaultCameraFacing =
                            if (defaultCameraFacing == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                    }

                    else -> Unit
                }
            }
            IconButton(
                onClick = {
                    onCancel()
                }, modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
            Row(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_music_note),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.add_sound),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CameraControllerFooter(
    cameraOpenType: Tabs,
    onEffectClick: () -> Unit,
    onClickOpenFile: () -> Unit,
    onCaptureClick: () -> Unit,
    isLayoutEnabled: Boolean = false, modifier: Modifier,
) {
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHalfWidth = configuration.screenWidthDp.div(2).dp
    val horizontalContentPadding = screenHalfWidth.minus(13.dp)
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = when (cameraOpenType) {
            Tabs.CAMERA -> 2
            Tabs.STORY -> 1
            else -> 0
        }
    )
    val alpha = if (isLayoutEnabled) 1f else 0.30f
    val captureOptions = remember {
        when (cameraOpenType) {
            Tabs.CAMERA -> CameraCaptureOptions.entries.toMutableList().apply {
                removeAll(listOf(CameraCaptureOptions.TEXT, CameraCaptureOptions.VIDEO))
            }

            Tabs.STORY -> {
                CameraCaptureOptions.entries.toMutableList().apply {
                    removeAll(
                        listOf(
                            CameraCaptureOptions.SIXTY_SECONDS,
                            CameraCaptureOptions.FIFTEEN_SECONDS
                        )
                    )
                }
            }

            else -> emptyList()
        }
    }
    val itemWidth = 62.dp
    val itemSpacing = 26.dp
    val currentCenteredIndex by getCenteredItemIndex(
        listState = listState,
        itemWidth = itemWidth,
        itemSpacing = itemSpacing,
        endPadding = screenHalfWidth.minus(30.dp)
    )
    val captureButtonColor = when (captureOptions.getOrNull(currentCenteredIndex ?: -1)) {
        CameraCaptureOptions.FIFTEEN_SECONDS, CameraCaptureOptions.SIXTY_SECONDS, CameraCaptureOptions.VIDEO -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onBackground
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .alpha(alpha)
                    .size(width = 62.dp, height = 22.dp)
                    .background(
                        MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp)
                    )
            )
            LazyRow(
                modifier = Modifier.alpha(alpha),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                state = listState,
                userScrollEnabled = isLayoutEnabled,
                contentPadding = PaddingValues(horizontal = horizontalContentPadding),
                flingBehavior = rememberSnapFlingBehavior(listState)
            ) {
                itemsIndexed(captureOptions) { index, option ->
                    val isCurrentOption = index == currentCenteredIndex
                    val color =
                        if (isCurrentOption) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground
                    Text(
                        text = option.value,
                        style = MaterialTheme.typography.labelLarge.copy(color = color),
                        modifier = Modifier.rippleClickable {
                            if (isLayoutEnabled) {
                                scope.launch {
                                    listState.animateScrollToItem(index)
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                }
                            }
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.rippleClickable {
                    if (isLayoutEnabled) {
                        onEffectClick()
                    }
                },
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_effect_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(alpha)
                        .size(32.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        ), contentScale = ContentScale.Crop
                )
                Text(
                    text = stringResource(R.string.effects),
                    style = MaterialTheme.typography.labelMedium, modifier = Modifier.alpha(alpha)
                )
            }
            CaptureButton(
                modifier = Modifier.alpha(alpha),
                color = captureButtonColor,
                onClick = onCaptureClick
            )
            Column(
                modifier = Modifier.rippleClickable {
                    if (isLayoutEnabled) {
                        onClickOpenFile()
                    }
                },
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_upload_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(alpha)
                        .size(32.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        ), contentScale = ContentScale.Crop
                )
                Text(
                    text = stringResource(R.string.upload),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun CameraSideControllerSection(
    modifier: Modifier,
    defaultCameraFacing: CameraSelector,
    onClick: (CameraController) -> Unit,
) {
    val controllers = if (defaultCameraFacing == CameraSelector.DEFAULT_BACK_CAMERA) {
        CameraController.entries.toMutableList().apply { remove(CameraController.MIRROR) }
    } else {
        CameraController.entries.toMutableList().apply { remove(CameraController.FLASH) }
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        controllers.forEach { controller ->
            ControllerItem(controller = controller, onClick = onClick)
        }
    }
}

@Composable
fun ControllerItem(controller: CameraController, onClick: (CameraController) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.rippleClickable {
            onClick(controller)
        }) {
        Icon(
            imageVector = ImageVector.vectorResource(controller.icon),
            contentDescription = null,
            modifier = Modifier.size(27.dp),
            tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
        )
        Text(text = stringResource(controller.title), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun getCenteredItemIndex(
    listState: LazyListState,
    itemWidth: Dp,
    itemSpacing: Dp,
    endPadding: Dp,
): State<Int?> {
    val density = LocalDensity.current
    val endPaddingPx = with(density) { endPadding.toPx() }

    return remember(listState, itemWidth, itemSpacing, endPaddingPx) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) return@derivedStateOf null

            val viewportCenter = (layoutInfo.viewportEndOffset - endPaddingPx).toInt()

            visibleItems.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                abs(itemCenter - viewportCenter)
            }?.index
        }
    }
}
