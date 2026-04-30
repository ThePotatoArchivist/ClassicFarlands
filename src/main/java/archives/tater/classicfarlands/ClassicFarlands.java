package archives.tater.classicfarlands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.google.gson.JsonObject;
import net.ramixin.mixson.inline.Mixson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ClassicFarlands implements ModInitializer {
	public static final String MOD_ID = "classicfarlands";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ClassicFarlandsConfig CONFIG = ClassicFarlandsConfig.createToml(
            FabricLoader.getInstance().getConfigDir(),
            "",
            MOD_ID,
            ClassicFarlandsConfig.class
    );

    public static final int BASE_DISTANCE = 12550824;

    public static int COORDINATE_DISTANCE = 0;
    public static int SWITCH_DISTANCE = 0;

    public static final Set<Identifier> OVERWORLD_NOISES = Set.of(
            Identifier.ofVanilla("worldgen/noise_settings/overworld"),
            Identifier.ofVanilla("worldgen/noise_settings/amplified"),
            Identifier.ofVanilla("worldgen/noise_settings/large_biomes")
    );

    public static int adjustCoordinate(int coordinate) {
        if (coordinate > COORDINATE_DISTANCE)
            return coordinate + BASE_DISTANCE - COORDINATE_DISTANCE;
        if (coordinate < -COORDINATE_DISTANCE)
            return coordinate - BASE_DISTANCE + COORDINATE_DISTANCE;
        return coordinate;
    }

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "radius_check"), RadiusCheck.CODEC);

//        "type": "minecraft:range_choice",
//                "input": {
//            "type": "classicfarlands:radius_check",
//                    "radius": 12550821
//        },
//        "min_inclusive": 1,
//                "max_exclusive": 2,
//                "when_in_range": "minecraft:overworld/base_3d_noise",
//                "when_out_of_range": {

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            COORDINATE_DISTANCE = 4 * (CONFIG.distance / 4);
            SWITCH_DISTANCE = 4 * (CONFIG.distance / 4) - 3;
        });

        Mixson.registerEvent(
                0,
                OVERWORLD_NOISES::contains,
                "Modify Overworld Noise",
                (context) -> {
                    var noiseRouter = context.getFile().getAsJsonObject().getAsJsonObject("noise_router");
                    var oldFinalDensity = noiseRouter.get("final_density");

                    var newFinalDensity = new JsonObject();

                    newFinalDensity.addProperty("type", "minecraft:range_choice");

                    var choiceInput = new JsonObject();
                    choiceInput.addProperty("type", "classicfarlands:radius_check");
                    newFinalDensity.add("input", choiceInput);

                    newFinalDensity.addProperty("min_inclusive", 1);
                    newFinalDensity.addProperty("max_exclusive", 2);

                    newFinalDensity.addProperty("when_in_range", "classicfarlands:old_overworld_noise");
                    newFinalDensity.add("when_out_of_range", oldFinalDensity);

                    noiseRouter.add("final_density", newFinalDensity);
                },
                false
        );
	}
}
