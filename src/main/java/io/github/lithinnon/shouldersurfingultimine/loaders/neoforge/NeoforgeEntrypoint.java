//? if neoforge {
package io.github.lithinnon.shouldersurfingultimine.loaders.neoforge;

import io.github.lithinnon.shouldersurfingultimine.ShoulderSurfingUltimine;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod("shouldersurfingultimine")
public class NeoforgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public NeoforgeEntrypoint() {
        LOGGER.info("Hello from NeoforgeEntrypoint!");
        ShoulderSurfingUltimine.initialize();
    }
}
//?}
