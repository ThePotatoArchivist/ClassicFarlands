package archives.tater.classicfarlands.mixin;

import archives.tater.classicfarlands.ClassicFarlands;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.levelgen.synth.SmearedPerlinNoise;

@SuppressWarnings("deprecation")
@Mixin(SmearedPerlinNoise.class)
public class SmearedPerlinNoiseMixin {
    @Definition(id = "wrap", method = "Lnet/minecraft/world/level/levelgen/synth/SmearedPerlinNoise;wrap(D)D")
    @Definition(id = "_x", local = @Local(type = double.class, name = "_x", argsOnly = true))
    @Definition(id = "_z", local = @Local(type = double.class, name = "_z", argsOnly = true))
    @Expression("wrap(_x)")
    @Expression("wrap(_z)")
    @WrapOperation(
            method = "get",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private double modifyCoordinate(double v, Operation<Double> original) {
        return ClassicFarlands.adjustCoordinate(v);
    }

    @Definition(id = "wrap", method = "Lnet/minecraft/world/level/levelgen/synth/SmearedPerlinNoise;wrap(D)D")
    @Definition(id = "xzScale", local = @Local(type = double.class, name = "xzScale", argsOnly = true))
    @Expression("wrap(@(?) * xzScale)")
    @ModifyExpressionValue(
            method = "addToVolume",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private double modifyCoordinate2(double original) {
        return ClassicFarlands.adjustCoordinate(original);
    }

    @Definition(id = "wrap", method = "Lnet/minecraft/world/level/levelgen/synth/SmearedPerlinNoise;wrap(D)D")
    @Definition(id = "xzScale", local = @Local(type = double.class, name = "xzScale", argsOnly = true))
    @Expression("wrap(? * xzScale)")
    @WrapOperation(
            method = "addToVolume",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private double noWrap(double v, Operation<Double> original) {
        return v;
    }
}
