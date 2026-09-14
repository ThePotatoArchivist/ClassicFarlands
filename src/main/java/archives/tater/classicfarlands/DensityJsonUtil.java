package archives.tater.classicfarlands;

import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface DensityJsonUtil {
    static JsonObject modifyFinalDensity(JsonElement finalDensity, int switchDistance) {
        var newFinalDensity = new JsonObject();
        newFinalDensity.addProperty("type", "minecraft:range_choice");

        newFinalDensity.add("input", max(
                max(
                        gradient("x", switchDistance),
                        gradient("x", -switchDistance)
                ),
                max(
                        gradient("z", switchDistance),
                        gradient("z", -switchDistance)
                )
        ));

        newFinalDensity.addProperty("min_inclusive", -DensityFunctions.MAX_REASONABLE_NOISE_VALUE);
        newFinalDensity.addProperty("max_exclusive", 1f);

        newFinalDensity.add("when_in_range", finalDensity);
        newFinalDensity.addProperty("when_out_of_range", "classicfarlands:farlands_final_density");

        return newFinalDensity;
    }

    static JsonElement max(JsonElement left, JsonElement right) {
        var obj = new JsonObject();
        obj.addProperty("type", "minecraft:max");
        obj.add("left", left);
        obj.add("right", right);
        return obj;
    }

    static JsonElement gradient(String axis, int toCoordinate) {
        var obj = new JsonObject();
        obj.addProperty("type", "minecraft:gradient");
        obj.addProperty("axis", axis);
        obj.addProperty("tiling", "clamp_to_edge");
        obj.addProperty("from_coordinate", 0);
        obj.addProperty("to_coordinate", toCoordinate);
        obj.addProperty("from_value", 0);
        obj.addProperty("to_value", 0);
        return obj;
    }
}
