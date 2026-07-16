package io.kiquar.plugin.haskell

import android.content.res.Resources
import com.rk.file.FileType
import com.rk.icons.Icon
import com.rk.file.BuiltinFileType

class CabalLanguage(resources: Resources) : FileType {
    override val extensions = listOf("cabal")
    override val textmateScope = "source.cabal"
    override val name = "cabal"
    override val title = "Cabal"
    override val icon = BuiltinFileType.PROPERTIES.icon
}
