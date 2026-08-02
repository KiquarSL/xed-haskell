package io.kiquar.plugin.haskell.runner

import android.content.Context
import android.app.Activity
import com.rk.file.FileObject
import com.rk.icons.Icon
import com.rk.runner.Runner
import com.rk.file.BuiltinFileType
import com.rk.exec.launchTerminal
import com.rk.exec.TerminalCommand

class HsRunner(
    val icon: Icon? = null,
) : Runner() {

    override val id = "haskell.run"
    override val label = "Run Haskell File"

    override fun getIcon(context: Context) = icon

    fun matcher(fileObject: FileObject): Boolean {
        return fileObject.getExtension() == "hs"
    }

    suspend fun run(activity: Activity, fileObject: FileObject) {
        val workingDir = fileObject.getParentFile()?.getAbsolutePath()
        launchTerminal(
            activity = activity,
            terminalCommand = TerminalCommand(
                exe = "/bin/ghc",
                args = arrayOf(fileObject.getAbsolutePath(), "&&", "./Main"),
                id = id,
                workingDir = workingDir,
            ),
        )
    }

    override suspend fun isRunning(): Boolean = false

    override suspend fun stop() {}
}