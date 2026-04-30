package archives.tater.classicfarlands.mixin;

import archives.tater.classicfarlands.ClassicFarlands;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.levelgen.synth.BlendedNoise;

@Mixin(BlendedNoise.class)
public class BlendedNoiseMixin {
    @ModifyExpressionValue(
            method = "compute",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/DensityFunction$FunctionContext;blockX()I"),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/DensityFunction$FunctionContext;blockZ()I")
            }
    )
    private int modifyCoordinate(int original) {
        return ClassicFarlands.adjustCoordinate(original);
    }
}
