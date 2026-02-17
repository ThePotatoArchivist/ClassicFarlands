package archives.tater.classicfarlands.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PerlinNoise.class)
public class OctavePerlinNoiseSamplerMixin {
    @ModifyReturnValue(
            method = "wrap",
            at = @At("RETURN")
    )
    private static double revertPrecisionFix(double original, @Local(argsOnly = true) double value) {
        return value;
    }
}