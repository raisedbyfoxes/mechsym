package raisedbyfoxes.mechsym.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.projectile.PersistentProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import raisedbyfoxes.mechsym.MSEntityTypes
import raisedbyfoxes.mechsym.MSItems

class BoneSpearEntity(
    type: EntityType<BoneSpearEntity> = MSEntityTypes.BONE_SPEAR,
    world: World,
    pos: Vec3d = Vec3d.ZERO
) : PersistentProjectileEntity(type, world) {
    private val shootPos = pos

    init {
        setPosition(pos)
    }

    override fun asItemStack() = ItemStack(MSItems.BONE_SPEAR)

    override fun onEntityHit(result: EntityHitResult) {
        if (world.isClient) return

        val distance = pos.distanceTo(shootPos)
        val damage = if (distance >= 25) 6F else 3F
        result.entity.damage(this.damageSources.arrow(this, this.owner), damage)
        this.velocity = this.velocity.multiply(0.1)

        this.owner?.sendMessage(Text.of(distance.toString()))
    }
}