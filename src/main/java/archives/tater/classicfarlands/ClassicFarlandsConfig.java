package archives.tater.classicfarlands;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ClassicFarlandsConfig extends WrappedConfig {
    @Comment("The distance from world origin the Far Lands begin at, precise to within 4 blocks")
    public int distance = ClassicFarlands.BASE_DISTANCE;
}
