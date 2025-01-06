package raisedbyfoxes.mechsym.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.biome.source.FixedBiomeSource
import net.minecraft.world.dimension.DimensionOptions
import net.minecraft.world.dimension.DimensionTypes
import net.minecraft.world.gen.WorldPreset
import net.minecraft.world.gen.WorldPresets
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import net.minecraft.world.gen.chunk.GenerationShapeConfig
import net.minecraft.world.gen.chunk.NoiseChunkGenerator
import net.minecraft.world.gen.densityfunction.DensityFunction
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes
import net.minecraft.world.gen.noise.NoiseParametersKeys
import net.minecraft.world.gen.noise.NoiseRouter
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import java.util.concurrent.CompletableFuture

class MSWorldPresets(dataOut: FabricDataOutput, future: CompletableFuture<WrapperLookup>) :
    FabricDynamicRegistryProvider(dataOut, future) {
    override fun getName() = "World Presets"

    override fun configure(registries: WrapperLookup, entries: Entries) {
        val dimTypes = registries.getWrapperOrThrow(RegistryKeys.DIMENSION_TYPE)
        val biomes = registries.getWrapperOrThrow(RegistryKeys.BIOME)
        val noiseParameters = registries.getWrapperOrThrow(RegistryKeys.NOISE_PARAMETERS)

        entries.add(
            WorldPresets.DEFAULT, WorldPreset(
                mapOf(
                    DimensionOptions.OVERWORLD to DimensionOptions(
                        dimTypes.getOrThrow(DimensionTypes.OVERWORLD),
                        NoiseChunkGenerator(
                            FixedBiomeSource(biomes.getOrThrow(BiomeKeys.FOREST)),
                            RegistryEntry.of(
                                ChunkGeneratorSettings(
                                    GenerationShapeConfig(0, 64, 1, 1),
                                    Blocks.PURPLE_WOOL.defaultState,
                                    Blocks.WATER.defaultState,
                                    noiseRouter(
                                        finalDensity = DensityFunctionTypes.add(
                                            DensityFunctionTypes.noise(
                                                noiseParameters.getOrThrow(NoiseParametersKeys.SURFACE),
                                                1.0, 0.0
                                            ),
                                            DensityFunctionTypes.yClampedGradient(0, 64, 1.0, -1.0)
                                        )
                                    ),
                                    MaterialRules.block(Blocks.DIAMOND_BLOCK.defaultState),
                                    emptyList(),
                                    8,
                                    true,
                                    false,
                                    false,
                                    false
                                )
                            )
                        ),
                    )
                )
            )
        )
    }

    private fun noiseRouter(
        barrier: DensityFunction = DensityFunctionTypes.zero(),
        fluidLevelFloodedness: DensityFunction = DensityFunctionTypes.zero(),
        fluidLevelSpread: DensityFunction = DensityFunctionTypes.zero(),
        lava: DensityFunction = DensityFunctionTypes.zero(),
        temperature: DensityFunction = DensityFunctionTypes.zero(),
        vegetation: DensityFunction = DensityFunctionTypes.zero(),
        continents: DensityFunction = DensityFunctionTypes.zero(),
        erosion: DensityFunction = DensityFunctionTypes.zero(),
        depth: DensityFunction = DensityFunctionTypes.zero(),
        ridges: DensityFunction = DensityFunctionTypes.zero(),
        initialDensityWithoutJaggedness: DensityFunction = DensityFunctionTypes.zero(),
        finalDensity: DensityFunction = DensityFunctionTypes.zero(),
        veinToggle: DensityFunction = DensityFunctionTypes.zero(),
        veinRidged: DensityFunction = DensityFunctionTypes.zero(),
        veinGap: DensityFunction = DensityFunctionTypes.zero(),
    ) = NoiseRouter(
        barrier, fluidLevelFloodedness, fluidLevelSpread, lava, temperature, vegetation, continents, erosion,
        depth, ridges, initialDensityWithoutJaggedness, finalDensity, veinToggle, veinRidged, veinGap
    )
}