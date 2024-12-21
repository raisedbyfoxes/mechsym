package raisedbyfoxes.mechsym.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import raisedbyfoxes.mechsym.MSEntityTypes
import raisedbyfoxes.mechsym.MSItems

class BoneSpearEntity(type: EntityType<BoneSpearEntity> = MSEntityTypes.BONE_SPEAR, world: World) :
    PersistentProjectileEntity(type, world) {
    override fun asItemStack() = ItemStack(MSItems.BONE_SPEAR)
}