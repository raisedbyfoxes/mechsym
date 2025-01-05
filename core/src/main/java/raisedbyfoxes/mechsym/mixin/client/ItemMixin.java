package raisedbyfoxes.mechsym.mixin.client;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import raisedbyfoxes.mechsym.ext.ItemFOVAdjust;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemFOVAdjust {
}
