package raisedbyfoxes.mechsym

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.BlockTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting.*
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World
import raisedbyfoxes.mechsym.entity.BoneSpearEntity
import kotlin.math.min

object MSItems {
    val SEMI_SHARPENED_BONE = register("semi_sharpened_bone", object : Item(Settings().maxCount(1)) {
        val MAX_SHARPEN_STAGE = 5

        override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
            if (world.isClient || stack.nbt != null) return

            val nbt = NbtCompound()
            nbt.putInt("sharpenStage", 1)
            stack.nbt = nbt
        }

        override fun useOnBlock(context: ItemUsageContext): ActionResult {
            val blockState = context.world.getBlockState(context.blockPos)
            if (!blockState.isIn(BlockTags.BASE_STONE_OVERWORLD)) return ActionResult.PASS

            val nbt = context.stack.nbt ?: return ActionResult.PASS
            val newSharpenStage = nbt.getInt("sharpenStage") + 1

            context.world.playSound(null, context.blockPos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS)

            if (newSharpenStage >= MAX_SHARPEN_STAGE) {
                context.player!!.getStackInHand(context.hand).decrement(1)
                context.player!!.giveItemStack(ItemStack(BONE_SPEAR))
            } else {
                nbt.putInt("sharpenStage", newSharpenStage)
            }

            return ActionResult.SUCCESS
        }

        override fun appendTooltip(
            stack: ItemStack,
            world: World?,
            tooltip: MutableList<Text>,
            context: TooltipContext
        ) {
            val sharpenStage = stack.nbt!!.getInt("sharpenStage")
            tooltip.add(
                Text.literal("Sharpening: ").formatted(DARK_GRAY)
                    .append(Text.literal("${sharpenStage * 20}% ").formatted(GOLD))
                    .append(Text.literal("|".repeat(sharpenStage * MAX_SHARPEN_STAGE)).formatted(GRAY))
                    .append(
                        Text.literal("|".repeat((MAX_SHARPEN_STAGE - sharpenStage) * MAX_SHARPEN_STAGE))
                            .formatted(DARK_GRAY)
                    )
            )
        }

        override fun isItemBarVisible(stack: ItemStack) = true

        override fun getItemBarStep(stack: ItemStack): Int {
            val sharpenStage = stack.nbt?.getInt("sharpenStage") ?: return 0
            return sharpenStage * 13 / MAX_SHARPEN_STAGE
        }

        override fun getItemBarColor(stack: ItemStack): Int = 0xFFAA00
    })

    val BONE_SPEAR = register("bone_spear", object : Item(Settings().maxCount(1)) {
        val attributeModifiers: Multimap<EntityAttribute, EntityAttributeModifier> =
            ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
                .put(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(
                        ATTACK_DAMAGE_MODIFIER_ID, "", 2.0, EntityAttributeModifier.Operation.ADDITION
                    )
                ).put(
                    EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(
                        ATTACK_SPEED_MODIFIER_ID, "", -3.0, EntityAttributeModifier.Operation.ADDITION
                    )
                ).build()

        override fun getAttributeModifiers(slot: EquipmentSlot?) =
            if (slot == EquipmentSlot.MAINHAND) attributeModifiers else super.getAttributeModifiers(slot)

        override fun appendTooltip(
            stack: ItemStack,
            world: World?,
            tooltip: MutableList<Text>,
            context: TooltipContext
        ) {
            tooltip.add(Text.literal("Throwable").formatted(GRAY))
        }

        override fun getUseAction(stack: ItemStack) = UseAction.SPEAR

        override fun getMaxUseTime(stack: ItemStack) = Int.MAX_VALUE

        override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, ticksRemaining: Int) {
            if (user !is PlayerEntity) return

            val entity = BoneSpearEntity(world = world)
            entity.owner = user
            entity.setPosition(user.eyePos)
            entity.setVelocity(user, user.pitch, user.yaw, 0F, min(Int.MAX_VALUE - ticksRemaining, 20) / 10F, 0F)
            world.spawnEntity(entity)

            if (!user.isCreative) user.inventory.removeOne(stack)
        }

        override fun use(world: World?, user: PlayerEntity, hand: Hand?): TypedActionResult<ItemStack> {
            user.setCurrentHand(hand)
            return TypedActionResult.consume(user.getStackInHand(hand))
        }
    })

    private fun register(name: String, item: Item): Item =
        Registry.register(Registries.ITEM, Mechsym.id(name), item)
}