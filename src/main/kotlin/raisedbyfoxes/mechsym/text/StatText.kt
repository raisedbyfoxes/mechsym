package raisedbyfoxes.mechsym.text

import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Formatting.*

object StatText {
    fun damage(amount: Int, multiplicative: Boolean = false): MutableText =
        Text.literal("\uD83D\uDDE1 ${if (multiplicative) "x" else ""}$amount").formatted(RED)

    fun attackSpeed(amount: Int): MutableText =
        Text.literal("\uD83C\uDF0A $amount").formatted(YELLOW).append(Text.literal("/s").setStyle(Styles.DARK_YELLOW))

    fun distance(amount: Int): MutableText =
        Text.literal("${amount}m").formatted(AQUA)
}