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
    val icon: Icon? = BuiltinFileType.ZIG.icon,
    val supportedExtensions: List<String> = listOf("hs"),
) : Runner() {

    override val id = "haskell.run.hs"
    override val label = "Run Haskell"

    override fun getIcon(context: Context) = icon

    override fun matcher(fileObject: FileObject): Boolean {
        return supportedExtensions.contains(fileObject.getExtension())
    }

    override suspend fun run(activity: Activity, fileObject: FileObject) {
        val workingDir = fileObject.getParentFile()?.getAbsolutePath()
        launchTerminal(
            activity = activity,
            terminalCommand = TerminalCommand(
                exe = "/bin/ghc",
                args = arrayOf(fileObject.getAbsolutePath()),
                id = id,
                workingDir = workingDir,
            ),
        )
    }

    override suspend fun isRunning() = false

    override suspend fun stop() {}
}
