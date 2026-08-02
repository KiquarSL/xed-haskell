package io.kiquar.plugin.haskell

import android.content.res.Resources
import com.rk.file.FileType
import com.rk.icons.Icon

class HaskellLanguage(resources: Resources) : FileType {
    override val extensions = listOf("hs", "lhs", "hsig")
    override val textmateScope = "source.haskell"
    override val name = "haskell"
    override val title = "Haskell"
    override val icon = Icon.ExternalResourceIcon(R.drawable.haskell, resources)
}
