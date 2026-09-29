package io.kiquar.plugin.haskell

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

    override fun onLoad() {
        loadLanguages()
        loadRunners()
    }

    override fun onDispose() {
        dispose()
    }

    private fun loadLanguages() {
        val fileProviderRegistry = FileProviderRegistry.getInstance()
        fileResolver = AssetsFileResolver(context.assets).also {
            fileProviderRegistry.addFileProvider(it)
        }

        val grammarRegistry = GrammarRegistry.getInstance()
        grammarRegistry.loadGrammars("lang/language.json")

        haskellLanguage = HaskellLanguage(context.resources).also {
            FileTypeManager.register(it)
        }

        cabalLanguage = CabalLanguage(context.resources).also {
            FileTypeManager.register(it)
        }
    }

    private fun loadRunners() {
        hsRunner = HsRunner().also {
            RunnerManager.registerRunner(it)
        }

        cabalRunner = CabalRunner().also {
            RunnerManager.registerRunner(it)
        }
    }

    private fun dispose() {
        fileResolver?.let {
            FileProviderRegistry.getInstance().removeFileProvider(it)
        }
        hsRunner?.let {
            RunnerManager.unregisterRunner(it)
        }
        cabalRunner?.let {
            RunnerManager.unregisterRunner(it)
        }
    }
}