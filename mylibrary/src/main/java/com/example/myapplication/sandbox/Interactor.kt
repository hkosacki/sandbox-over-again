package com.example.myapplication.sandbox

import android.content.Context
import android.os.Bundle
import androidx.privacysandbox.sdkruntime.client.SdkSandboxManagerCompat
import mylibrary.SdkService
import mylibrary.SdkServiceFactory

object Interactor {

    suspend fun init(context: Context){
        val sdkman = SdkSandboxManagerCompat.from(context)
        val sandboxedSdk = sdkman.loadSdk("com.example.mylibrary", Bundle.EMPTY)
        val sdkService: SdkService = SdkServiceFactory.wrapToSdkService(sandboxedSdk.getInterface()!!)
    }

}