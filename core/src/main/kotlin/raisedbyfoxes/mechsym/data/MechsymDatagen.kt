package raisedbyfoxes.mechsym.data

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import raisedbyfoxes.mechsym.MSEntityTypes
import raisedbyfoxes.mechsym.MSItems

class MechsymDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()

        pack.addProvider(::BlockLootTables)
        pack.addProvider(::EntityLootTables)
        pack.addProvider(::MSWorldPresets)

        pack.addProvider(::ModelProvider)
        pack.addProvider(::EnglishLangProvider)

    }

    class ModelProvider(dataOut: FabricDataOutput) : FabricModelProvider(dataOut) {
        override fun generateBlockStateModels(gen: BlockStateModelGenerator) {

        }

        override fun generateItemModels(gen: ItemModelGenerator) {
            gen.register(MSItems.SHARP_BONE, Models.HANDHELD)
            gen.register(MSItems.BONE_SPEAR, Models.HANDHELD)
        }
    }

    class EnglishLangProvider(dataOut: FabricDataOutput) : FabricLanguageProvider(dataOut, "en_us") {
        override fun generateTranslations(builder: TranslationBuilder) {
            builder.add(MSItems.SHARP_BONE, "Sharp Bone")
            builder.add(MSItems.BONE_SPEAR, "Bone Spear")

            builder.add(MSEntityTypes.BONE_SPEAR, "Bone Spear")
        }
    }
}