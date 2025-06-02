//? if !vanilla {
package io.github.lithinnon.shouldersurfingultimine.mixin;

import io.github.lithinnon.shouldersurfingultimine.ShoulderSurfingUltimine;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("HEAD"))
    public void initMixinExample(CallbackInfo ci) {
        String baseString = "Hello from %LOADER% on Minecraft %MINECRAFT%";

        /// https://stonecutter.kikugie.dev/stonecutter/guide/comments

        //? if fabric {
        /*baseString = baseString.replace("%LOADER%", "Fabric Loader");
        *///?} else if neoforge {
        baseString = baseString.replace("%LOADER%", "NeoForge");
        //?}

        //? if 1.21.1 {
        baseString = baseString.replace("%MINECRAFT%", "1.21.1");
        //?} else if 1.20.1 {
        /*baseString = baseString.replace("%MINECRAFT", "1.20.1");
        *///?}

        ShoulderSurfingUltimine.LOGGER.info(baseString);
    }
}
//?}
