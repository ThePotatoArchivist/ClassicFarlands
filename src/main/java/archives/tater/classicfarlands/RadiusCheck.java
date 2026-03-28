package archives.tater.classicfarlands;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;

import static java.lang.Math.max;

public class RadiusCheck implements DensityFunction.Base {
    public static final RadiusCheck INSTANCE = new RadiusCheck();
    public static final MapCodec<RadiusCheck> CODEC = MapCodec.unit(INSTANCE);
    public static final CodecHolder<RadiusCheck> CODEC_HOLDER = CodecHolder.of(CODEC);

    private RadiusCheck() {}

    @Override
    public double sample(DensityFunction.NoisePos pos) {
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
    public CodecHolder<? extends DensityFunction> getCodecHolder() {
        return CODEC_HOLDER;
    }
}
