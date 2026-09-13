package archives.tater.classicfarlands.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.SharedConstants;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    @Definition(id = "DEBUG_ENABLE_FARLANDS", field = "Lnet/minecraft/SharedConstants;DEBUG_ENABLE_FARLANDS:Z")
    @Expression("DEBUG_ENABLE_FARLANDS = @(?)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static boolean revertPrecisionFix(boolean original) {
        return true;
    }
}