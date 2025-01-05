package raisedbyfoxes.mechsym.mixin;

import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @ModifyArg(
            method = "createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;)Lnet/minecraft/block/PillarBlock;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$Settings;strength(F)Lnet/minecraft/block/AbstractBlock$Settings;")
    )
    private static float makeAllLogsHarder(float strength) {
        return 5F;
    }

}
