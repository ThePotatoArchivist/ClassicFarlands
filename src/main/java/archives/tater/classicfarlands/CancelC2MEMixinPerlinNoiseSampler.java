package archives.tater.classicfarlands;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class CancelC2MEMixinPerlinNoiseSampler implements MixinCanceller {

    //very cursed c2me compat :)
    @Override
    public boolean shouldCancel(List<String> list, String s) {
        if (FabricLoader.getInstance().isModLoaded("c2me")) {
            if (s.equals("com.ishland.c2me.opts.math.mixin.MixinPerlinNoiseSampler")) {
                return true;
            }
        }

        return false;
    }
}
