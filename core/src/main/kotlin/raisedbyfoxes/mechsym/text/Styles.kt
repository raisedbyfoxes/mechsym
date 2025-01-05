package raisedbyfoxes.mechsym.text

import net.minecraft.text.Style

object Styles {
    val AUBURN = color(0xC9974C)
    val AUBURN_BRIGHT = color(0xF1DD79)

    val DARK_YELLOW = color(0x9D853D)

    private fun color(hexCode: Int) = Style.EMPTY.withColor(hexCode)!!
}