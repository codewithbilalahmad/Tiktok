package com.muhammad.core

import android.content.Context
import android.content.Intent
import android.provider.Settings

object IntentUtils{
    fun Context.share(type : String = "text/plain", title : String, text : String){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            setType(type)
        }
        val chooserIntent = Intent.createChooser(sendIntent, title)
        startActivity(chooserIntent)
    }
    fun Context.openAppSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = android.net.Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}