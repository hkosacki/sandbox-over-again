package mylibrary

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.privacysandbox.sdkruntime.core.activity.ActivityHolder
import androidx.privacysandbox.sdkruntime.core.activity.SdkSandboxActivityHandlerCompat
import androidx.privacysandbox.sdkruntime.core.controller.SdkSandboxControllerCompat
import androidx.privacysandbox.ui.core.SandboxedUiAdapter
import androidx.privacysandbox.ui.core.SessionObserverFactory
import com.example.myapplication.sandbox.mylibrary_runtime_sdk.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import kotlin.random.Random

class SdkSandboxedUiAdapterImpl(
    private val sdkContext: Context,
    private val request: SdkBannerRequest
) : SdkSandboxedUiAdapter {
    override fun openSession(
        context: Context,
        windowInputToken: IBinder,
        initialWidth: Int,
        initialHeight: Int,
        isZOrderOnTop: Boolean,
        clientExecutor: Executor,
        client: SandboxedUiAdapter.SessionClient
    ) {
        val session = SdkUiSession(clientExecutor, sdkContext, request)
        clientExecutor.execute {
            client.onSessionOpened(session)
        }
    }

    override fun addObserverFactory(sessionObserverFactory: SessionObserverFactory) {
        // todo
    }

    override fun removeObserverFactory(sessionObserverFactory: SessionObserverFactory) {
        // TODO("Not yet implemented")
    }
}

private class SdkUiSession(
    clientExecutor: Executor,
    private val sdkContext: Context,
    private val request: SdkBannerRequest
) : SandboxedUiAdapter.Session {

    private val controller = SdkSandboxControllerCompat.from(sdkContext)

    /** A scope for launching coroutines in the client executor. */
    private val scope = CoroutineScope(clientExecutor.asCoroutineDispatcher() + Job())

    private val urls = listOf(
        "https://github.com", "https://developer.android.com/"
    )

    override val view: View = getAdView()

    private fun getAdView(): View {
        if (request.isWebViewBannerAd) {
            val webview = WebView(sdkContext)
            webview.loadUrl(urls[Random.nextInt(urls.size)])
            return webview
        }
        return View.inflate(sdkContext, R.layout.banner, null).apply {
            val textView = findViewById<TextView>(R.id.banner_header_view)
            textView.text =
                context.getString(R.string.banner_ad_label, request.appPackageName)

            setOnClickListener {
                launchActivity()
            }
        }
    }

    override fun close() {
        // Notifies that the client has closed the session. It's a good opportunity to dispose
        // any resources that were acquired to maintain the session.
        scope.cancel()
    }

    override fun notifyConfigurationChanged(configuration: Configuration) {
        // Notifies that the device configuration has changed and affected the app.
    }

    override fun notifyResized(width: Int, height: Int) {
        // Notifies that the size of the presentation area in the app has changed.
    }

    override fun notifyZOrderChanged(isZOrderOnTop: Boolean) {
        // Notifies that the Z order has changed for the UI associated by this session.
    }

    private fun launchActivity() = scope.launch {
        val handler = object : SdkSandboxActivityHandlerCompat {
            override fun onActivityCreated(activityHolder: ActivityHolder) {
                val contentView = View.inflate(sdkContext, R.layout.full_screen, null)
                contentView.findViewById<WebView>(R.id.full_screen_ad_webview).apply {
                    loadUrl(urls[Random.nextInt(urls.size)])
                }
                activityHolder.getActivity().setContentView(contentView)
            }
        }

        val token = controller.registerSdkSandboxActivityHandler(handler)
        val launched = request.activityLauncher.launchSdkActivity(token)
        if (!launched) controller.unregisterSdkSandboxActivityHandler(handler)
    }

    override val signalOptions: Set<String>
        get() = setOf()

    override fun notifyUiChanged(uiContainerInfo: Bundle) {
        // nothing to do yet
    }
}
