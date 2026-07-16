package io.kiquar.plugin.haskell

import android.app.Activity
import android.os.Bundle
import androidx.annotation.Keep
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext
import com.rk.file.FileTypeManager
import com.rk.runner.RunnerManager
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.kiquar.plugin.haskell.runner.HsRunner
import io.kiquar.plugin.haskell.runner.CabalRunner

@Keep
@Suppress("unused")
class Main(context: ExtensionContext) : ExtensionAPI(context) {
    private var fileResolver: AssetsFileResolver? = null
    private var haskellLanguage: HaskellLanguage? = null
    private var cabalLanguage: CabalLanguage? = null
    private var hsRunner: HsRunner? = null
    private var cabalRunner: CabalRunner? = null

    override fun onExtensionLoaded() {
        val fileProviderRegistry = FileProviderRegistry.getInstance()
        fileResolver = AssetsFileResolver(context.assets)
        fileProviderRegistry.addFileProvider(fileResolver)

        val grammarRegistry = GrammarRegistry.getInstance()
        grammarRegistry.loadGrammars("lang/language.json")

        HaskellLanguage(context.resources).also {
            haskellLanguage = it
            FileTypeManager.register(it)
        }

        CabalLanguage(context.resources).also {
            cabalLanguage = it
            FileTypeManager.register(it)
        }

        HsRunner().also {
            hsRunner = it
            RunnerManager.registerRunner(it)
        }

        CabalRunner().also {
            cabalRunner = it
            RunnerManager.registerRunner(it)
        }
    }

    override fun onInstalled() {
    }
    
    override fun onLoad() {}

    override fun onUninstalled() {
        dispose()
    }

    private fun dispose() {
        val fileProviderRegistry = FileProviderRegistry.getInstance()
        fileResolver?.let {
            fileProviderRegistry.removeFileProvider(it)
        }
        hsRunner?.let {
            RunnerManager.unregisterRunner(it)
        }
        cabalRunner?.let {
            RunnerManager.unregisterRunner(it)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
}
