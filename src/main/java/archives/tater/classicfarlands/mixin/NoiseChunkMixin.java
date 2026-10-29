package archives.tater.classicfarlands.mixin;

import archives.tater.classicfarlands.ClassicFarlands;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

import static java.lang.Math.abs;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseChunkMixin {
    @ModifyReturnValue(
            method = "createFluidPicker",
            at = @At("RETURN")
    )
    private static Aquifer.FluidPicker disableAquifers(Aquifer.FluidPicker original, @Local(name = "emptyStatus") Aquifer.FluidStatus emptyStatus) {
        return ClassicFarlands.CONFIG.disableAquifers
                ? (blockX, blockY, blockZ) ->
                        abs(blockX) > ClassicFarlands.COORDINATE_DISTANCE || abs(blockZ) > ClassicFarlands.COORDINATE_DISTANCE
                                ? emptyStatus
                                : original.computeFluid(blockX, blockY, blockZ)
                : original;
    }
}
