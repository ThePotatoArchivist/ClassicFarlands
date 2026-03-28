package archives.tater.classicfarlands;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import static java.lang.Math.max;

public class RadiusCheck implements DensityFunction.SimpleFunction {
    public static final RadiusCheck INSTANCE = new RadiusCheck();
    public static final MapCodec<RadiusCheck> CODEC = MapCodec.unit(INSTANCE);
    public static final KeyDispatchDataCodec<RadiusCheck> CODEC_HOLDER = KeyDispatchDataCodec.of(CODEC);

    private RadiusCheck() {}

    @Override
    public double compute(DensityFunction.FunctionContext pos) {
        return max(Math.abs(pos.blockX()), Math.abs(pos.blockZ())) >= ClassicFarlands.CONFIG.distance ? 1.0 : 0.0;
    }

    @Override
    public double minValue() {
        return 0.0;
    }

    @Override
    public double maxValue() {
        return 1.0;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC_HOLDER;
    }
}
