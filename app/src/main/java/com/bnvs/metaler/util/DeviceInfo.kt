package com.bnvs.metaler.util

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.provider.Settings

class DeviceInfo(val context: Context) {
    // android device id 확인
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // android devcie model / android devcie os / 제조사 확인
    // 예시 : SM-N960N/10/Samsung
    fun getDeviceModel(): String {
        return Build.MODEL + "/" + Build.VERSION.RELEASE.toString() + "/" + Build.BRAND
    }

    // android devcie 확인
    fun getDeviceOs(): String {
        return "A"
    }

    // android app version 확인
    fun getAppVersion(): String {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }
}