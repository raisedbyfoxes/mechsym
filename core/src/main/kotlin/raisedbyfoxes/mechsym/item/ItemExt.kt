package raisedbyfoxes.mechsym.item

import net.minecraft.client.gui.DrawContext

/**
 * Swiss army knife of useful methods for items that Mojang's code doesn't provide
 */
interface ItemExt {
    fun getFOVMultiplier(useTicks: Int) = 1F

    fun drawHUD(ctx: DrawContext, delta: Float, useTicks: Int) {}
}
