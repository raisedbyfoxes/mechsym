package raisedbyfoxes.mechsym

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import raisedbyfoxes.mechsym.entity.BoneSpearEntity

object MSEntityTypes {
    val BONE_SPEAR = register(
        "bone_spear", FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::BoneSpearEntity)
            .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
            .build()
    )

    private fun <E : Entity> register(name: String, entityType: EntityType<E>): EntityType<E> =
        Registry.register(Registries.ENTITY_TYPE, Mechsym.id(name), entityType)
}