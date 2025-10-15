package archives.tater.classicfarlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;

import static java.lang.Math.max;

public record RadiusCheck(int radius) implements DensityFunction.Base {
    private static final Codec<Double> CONSTANT_RANGE = Codec.doubleRange(-1000000.0, 1000000.0);
    public static final MapCodec<RadiusCheck> AXIS_VALUE_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.INT.fieldOf("radius").forGetter(RadiusCheck::radius)
                    )
                    .apply(instance, RadiusCheck::new)
    );
    public static final CodecHolder<RadiusCheck> CODEC_HOLDER = CodecHolder.of(AXIS_VALUE_CODEC);

    @Override
    public double sample(DensityFunction.NoisePos pos) {
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
    public CodecHolder<? extends DensityFunction> getCodecHolder() {
        return CODEC_HOLDER;
    }
}
