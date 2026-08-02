package io.kiquar.plugin.haskell

import androidx.annotation.Keep
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext
import com.rk.utils.toast

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
	
	// Local functions
	
	private fun loadLanguages() {
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
	}
	
	private fun loadRunners() {
		HsRunner().also {
            hsRunner = it
            RunnerManager.registerRunner(it)
        }

        CabalRunner().also {
            cabalRunner = it
            RunnerManager.registerRunner(it)
        }
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

}