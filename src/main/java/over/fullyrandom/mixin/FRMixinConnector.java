package over.fullyrandom.mixin;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;
import org.spongepowered.asm.mixin.injection.InjectionPoint;

public class FRMixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("fullyrandom.mixins.core.json");
        InjectionPoint.register(BeforeInstanceofInjectionPoint.class);
    }

}
