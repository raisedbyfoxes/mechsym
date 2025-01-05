package raisedbyfoxes.mechsym

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier

class Mechsym : ModInitializer {
    override fun onInitialize() {
        MSItems
        MSEntityTypes

        UseBlockCallback.EVENT.register { player, world, hand, hit ->
            // Callback purely for vanilla item interactions (e.g. bone on rock)
            // Items added by Mechsym have their interactions inside their own object/class.
            val heldStack = player.getStackInHand(hand)
            val blockState = world.getBlockState(hit.blockPos)

            if (blockState.isIn(BlockTags.BASE_STONE_OVERWORLD) && heldStack.item == Items.BONE) {
                heldStack.decrement(1)
                player.giveItemStack(ItemStack(MSItems.SHARP_BONE))
                world.playSound(null, hit.blockPos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS)

                return@register ActionResult.SUCCESS
            }

            return@register ActionResult.PASS
        }
    }

    companion object {
        const val TPS = 20

        fun id(path: String) = Identifier("mechsym", path)
    }
}
