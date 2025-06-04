package io.github.lithinnon.shouldersurfingultimine;

import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingRegistrar;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.FTBUltimineCommon;
import dev.ftb.mods.ftbultimine.client.FTBUltimineClient;
import io.github.lithinnon.shouldersurfingultimine.mixin.FTBUltimineClientAccessor;

public class ShoulderSurfingUltiminePlugin implements IShoulderSurfingPlugin {
    @Override
    public void register(IShoulderSurfingRegistrar registrar) {
        registrar.registerCameraCouplingCallback(minecraft -> {
            FTBUltimine ultimine = FTBUltimine.instance;
            if (ultimine != null) {
                FTBUltimineCommon proxy = ultimine.proxy;
                if (proxy instanceof FTBUltimineClient) {
                    return ((FTBUltimineClientAccessor) proxy).getPressed();
                }
            }
            return false;
        });
    }
}