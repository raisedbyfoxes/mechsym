package raisedbyfoxes.mechsym.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Blocks.*

class BlockLootTables(dataOut: FabricDataOutput) : FabricBlockLootTableProvider(dataOut) {
    override fun generate() {
        // TODO: Update these to drop logs when using correct tool(s)
        val woodenBlocks = arrayOf(
            OAK_LOG, OAK_WOOD, STRIPPED_OAK_LOG, STRIPPED_OAK_WOOD,
            SPRUCE_LOG, SPRUCE_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_SPRUCE_WOOD,
            BIRCH_LOG, BIRCH_WOOD, STRIPPED_BIRCH_LOG, STRIPPED_BIRCH_WOOD,
            JUNGLE_LOG, JUNGLE_WOOD, STRIPPED_JUNGLE_LOG, STRIPPED_JUNGLE_WOOD,
            ACACIA_LOG, ACACIA_WOOD, STRIPPED_ACACIA_LOG, STRIPPED_ACACIA_WOOD,
            DARK_OAK_LOG, DARK_OAK_WOOD, STRIPPED_DARK_OAK_LOG, STRIPPED_DARK_OAK_WOOD,
            MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD,
            CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD,
            CRIMSON_STEM, CRIMSON_HYPHAE, STRIPPED_CRIMSON_STEM, STRIPPED_CRIMSON_HYPHAE,
            WARPED_STEM, WARPED_HYPHAE, STRIPPED_WARPED_STEM, STRIPPED_WARPED_HYPHAE,
            BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK,
        )

        woodenBlocks.forEach { addDrop(it, dropsNothing()) }
    }
}