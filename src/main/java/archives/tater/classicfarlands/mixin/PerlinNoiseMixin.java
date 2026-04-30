package archives.tater.classicfarlands.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.synth.PerlinNoise;

@Mixin(PerlinNoise.class)
public class PerlinNoiseMixin {
    @WrapMethod(
            method = "wrap"
    )
    private static double revertPrecisionFix(double x, Operation<Double> original) {
        return x;
    }
}