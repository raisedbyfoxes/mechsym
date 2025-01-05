package raisedbyfoxes.mechsym.mixin.client;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Redirect(
            method = "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isSectionVisible(ILnet/minecraft/item/ItemStack$TooltipSection;)Z",
                    ordinal = 4
            )
    )
    private boolean removeModifierTooltip(int flags, ItemStack.TooltipSection tooltipSection) {
        // Our own code shows stats of weapons and tools in a much better way (imo) than vanilla and I hate
        // redundancy!
        return false;
    }
}
