package archives.tater.classicfarlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import static java.lang.Math.max;

public record RadiusCheck(int radius) implements DensityFunction.SimpleFunction {
    private static final Codec<Double> CONSTANT_RANGE = Codec.doubleRange(-1000000.0, 1000000.0);
    public static final MapCodec<RadiusCheck> AXIS_VALUE_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.INT.fieldOf("radius").forGetter(RadiusCheck::radius)
                    )
                    .apply(instance, RadiusCheck::new)
    );
    public static final KeyDispatchDataCodec<RadiusCheck> CODEC_HOLDER = KeyDispatchDataCodec.of(AXIS_VALUE_CODEC);

    @Override
    public double compute(DensityFunction.FunctionContext pos) {
        return max(Math.abs(pos.blockX()), Math.abs(pos.blockZ())) >= radius ? 1.0 : 0.0;
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
