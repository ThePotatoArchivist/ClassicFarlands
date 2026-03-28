package archives.tater.classicfarlands.mixin;

import archives.tater.classicfarlands.ClassicFarlands;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.util.math.noise.InterpolatedNoiseSampler;

@Mixin(InterpolatedNoiseSampler.class)
public class BlendedNoiseMixin {
    @ModifyExpressionValue(
            method = "sample",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/densityfunction/DensityFunction$NoisePos;blockX()I"),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/densityfunction/DensityFunction$NoisePos;blockZ()I")
            }
    )
    private int modifyX(int original) {
        return ClassicFarlands.adjustCoordinate(original);
    }
}
