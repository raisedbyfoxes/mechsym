package raisedbyfoxes.mechsym.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.math.RotationAxis
import raisedbyfoxes.mechsym.MSEntityTypes
import raisedbyfoxes.mechsym.MSItems
import raisedbyfoxes.mechsym.Mechsym
import raisedbyfoxes.mechsym.entity.BoneSpearEntity
import raisedbyfoxes.mechsym.item.ItemExt

class MechsymClient : ClientModInitializer {
    override fun onInitializeClient() {
        HudRenderCallback.EVENT.register { ctx, delta ->
            val player = MinecraftClient.getInstance().player ?: return@register
            if (player.isUsingItem) {
                val activeItem = player.activeItem.item
                if (activeItem is ItemExt) activeItem.drawHUD(ctx, delta, player.itemUseTime)
            }
        }

        EntityRendererRegistry.register(MSEntityTypes.BONE_SPEAR) {
            object : EntityRenderer<BoneSpearEntity>(it) {
                override fun getTexture(entity: BoneSpearEntity) = Mechsym.id("textures/item/bone_spear.png")

                override fun render(
                    entity: BoneSpearEntity,
                    yaw: Float,
                    tickDelta: Float,
                    matrices: MatrixStack,
                    vertexConsumers: VertexConsumerProvider,
                    light: Int
                ) {
                    matrices.push()

                    matrices.scale(2F, 2F, 2F)

                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw(tickDelta) - 90F))
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch(tickDelta)))
                    matrices.translate(-0.3F, 0F, 0F)

                    // Minecraft items are rotated 45 degrees (like in inventory) so account for that
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45F))

                    MinecraftClient.getInstance().itemRenderer.renderItem(
                        ItemStack(MSItems.BONE_SPEAR),
                        ModelTransformationMode.GROUND,
                        light,
                        0,
                        matrices,
                        vertexConsumers,
                        entity.world,
                        0
                    )

                    matrices.pop()
                }
            }
        }
    }
}
