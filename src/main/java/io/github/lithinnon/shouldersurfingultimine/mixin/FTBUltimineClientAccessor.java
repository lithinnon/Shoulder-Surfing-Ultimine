package io.github.lithinnon.shouldersurfingultimine.mixin;

import dev.ftb.mods.ftbultimine.client.FTBUltimineClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FTBUltimineClient.class)
public interface FTBUltimineClientAccessor {
    @Accessor
    boolean getPressed();
}
