package archives.tater.classicfarlands;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.Interval;
import net.minecraft.world.level.levelgen.densityfunction.*;

import static java.lang.Math.max;

public record RadiusCheck(int maxDistance) implements DensityFunction {
    public static final MapCodec<RadiusCheck> CODEC = null;

    @Override
    public DensitySampler compileSampler(CompileContext context) {
        return new Sampler(maxDistance);
    }

    @Override
    public DensityFunction rewriteChildren(DfRewriteRule rule) {
        return this;
    }

    @Override
    public Interval range() {
        return Interval.of(0f, 1f);
    }

    @Override
    public @Axes int domainAxes() {
        return 0;
    }

    @Override
    public MapCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    private record Sampler(int maxDistance) implements DensitySampler {

        @Override
        public void sampleVolume(SamplerContext context, DensityBuffer outputBuffer, DensityVolume volume) {

        }

        @Override
        public float sampleValue(SamplerContext context, int blockX, int blockY, int blockZ) {
            return max(Math.abs(blockX), Math.abs(blockZ)) >= ClassicFarlands.SWITCH_DISTANCE ? 1f : 0f;
        }
    }
}
