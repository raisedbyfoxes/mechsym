package raisedbyfoxes.mechsym.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

class EntityLootTables(dataOut: FabricDataOutput) : SimpleFabricLootTableProvider(dataOut, LootContextTypes.ENTITY) {
    override fun accept(exporter: BiConsumer<Identifier, LootTable.Builder>) {
        exporter.accept(
            EntityType.COW.lootTableId, LootTable.builder()
                .pool(lootPool(Items.BEEF, uniform(3, 4)))
                .pool(lootPool(Items.BONE, uniform(1, 2)))
        )

        exporter.accept(
            EntityType.PIG.lootTableId, LootTable.builder()
                .pool(lootPool(Items.PORKCHOP, uniform(3, 5)))
                .pool(lootPool(Items.BONE, uniform(1, 2)))
        )

        exporter.accept(
            EntityType.SHEEP.lootTableId, LootTable.builder()
                .pool(lootPool(Items.MUTTON, uniform(3, 5)))
                .pool(lootPool(Items.BONE, uniform(1, 2)))
        )
    }

    // Boilerplate vacuums
    private fun lootPool(item: Item, count: LootNumberProvider): LootPool.Builder =
        LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1F))
            .with(ItemEntry.builder(item)).apply(SetCountLootFunction.builder(count))

    private fun uniform(min: Int, max: Int): UniformLootNumberProvider =
        UniformLootNumberProvider.create(min.toFloat(), max.toFloat())
}