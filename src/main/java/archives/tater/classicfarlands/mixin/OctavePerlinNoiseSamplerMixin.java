package archives.tater.classicfarlands.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;

@Mixin(OctavePerlinNoiseSampler.class)
public class OctavePerlinNoiseSamplerMixin {
    @ModifyReturnValue(
            method = "maintainPrecision",
            at = @At("RETURN")
    )
    private static double revertPrecisionFix(double original, @Local(argsOnly = true) double value) {
        return value;
    }
}