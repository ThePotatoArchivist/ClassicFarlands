package archives.tater.classicfarlands;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.google.gson.JsonObject;
import net.ramixin.mixson.inline.Mixson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassicFarlands implements ModInitializer {
	public static final String MOD_ID = "classicfarlands";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier OVERWORLD_NOISE = Identifier.ofVanilla("worldgen/noise_settings/overworld");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "radius_check"), RadiusCheck.AXIS_VALUE_CODEC);

//        "type": "minecraft:range_choice",
//                "input": {
//            "type": "classicfarlands:radius_check",
//                    "radius": 12550821
//        },
//        "min_inclusive": 1,
//                "max_exclusive": 2,
//                "when_in_range": "minecraft:overworld/base_3d_noise",
//                "when_out_of_range": {

        Mixson.registerEvent(
                0,
                OVERWORLD_NOISE::equals,
                "Modify Overworld Noise",
                (context) -> {
                    var noiseRouter = context.getFile().getAsJsonObject().getAsJsonObject("noise_router");
                    var oldFinalDensity = noiseRouter.get("final_density");

                    var newFinalDensity = new JsonObject();

                    newFinalDensity.addProperty("type", "minecraft:range_choice");

                    var choiceInput = new JsonObject();
                    choiceInput.addProperty("type", "classicfarlands:radius_check");
                    choiceInput.addProperty("radius", 12550821); // 12550821
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