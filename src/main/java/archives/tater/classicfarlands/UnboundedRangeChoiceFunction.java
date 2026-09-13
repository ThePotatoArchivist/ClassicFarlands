package archives.tater.classicfarlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Interval;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.DensitySampler;
import net.minecraft.world.level.levelgen.densityfunction.DfRewriteRule;
import net.minecraft.world.level.levelgen.densityfunction.op.RangeChoiceFunction;

public record UnboundedRangeChoiceFunction(RangeChoiceFunction rangeChoice) implements DensityFunction {

    public static final MapCodec<RangeChoiceFunction> RANGE_CHOICE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DensityFunction.CODEC.fieldOf("input").forGetter(RangeChoiceFunction::input),
            Codec.FLOAT.fieldOf("min_inclusive").forGetter(RangeChoiceFunction::minInclusive),
            Codec.FLOAT.fieldOf("max_exclusive").forGetter(RangeChoiceFunction::maxExclusive),
            DensityFunction.CODEC.fieldOf("when_in_range").forGetter(RangeChoiceFunction::whenInRange),
            DensityFunction.CODEC.fieldOf("when_out_of_range").forGetter(RangeChoiceFunction::whenOutOfRange)
    ).apply(instance, RangeChoiceFunction::new));

    public static final MapCodec<UnboundedRangeChoiceFunction> CODEC = RANGE_CHOICE_CODEC.xmap(UnboundedRangeChoiceFunction::new, UnboundedRangeChoiceFunction::rangeChoice);

    @Override
    public @Axes int domainAxes() {
        return rangeChoice.domainAxes();
    }

    @Override
    public MapCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    @Override
    public Interval range() {
        return rangeChoice.range();
    }

    @Override
    public DensitySampler compileSampler(CompileContext context) {
        return rangeChoice.compileSampler(context);
    }

    @Override
    public DensityFunction rewriteChildren(DfRewriteRule rule) {
        return new UnboundedRangeChoiceFunction((RangeChoiceFunction) rangeChoice.rewriteChildren(rule));
    }
}
