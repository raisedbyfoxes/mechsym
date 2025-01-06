package raisedbyfoxes.mechsym.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import raisedbyfoxes.mechsym.item.ItemExt;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    // Ctor boilerplate
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = "getFovMultiplier()F",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z",
                    shift = At.Shift.AFTER
            )
    )
    private void allowModToChangeFOVWithItems(
            CallbackInfoReturnable<Float> cir,
            @Local LocalFloatRef fov,
            @Local ItemStack activeItem
    ) {
        if (activeItem.getItem() instanceof ItemExt item) {
            var fovMult = item.getFOVMultiplier(getItemUseTime());
            fov.set(fov.get() * fovMult);
        }
    }
}
