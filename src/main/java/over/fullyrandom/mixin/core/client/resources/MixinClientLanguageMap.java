package over.fullyrandom.mixin.core.client.resources;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.resources.ClientLanguageMap;
import net.minecraft.client.resources.Language;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;
import org.spongepowered.asm.mixin.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Mixin(ClientLanguageMap.class)
public class MixinClientLanguageMap {

    @Mutable @Shadow @Final private final Map<String, String> field_239495_c_;
    @Mutable @Shadow @Final private final boolean field_239496_d_;

    private MixinClientLanguageMap(Map<String, String> p_i232487_1_, boolean p_i232487_2_) {
        this.field_239495_c_ = p_i232487_1_;
        this.field_239496_d_ = p_i232487_2_;
    }

    /**
     * @author OverInfrared
     */
    @Overwrite
    public static ClientLanguageMap func_239497_a_(IResourceManager p_239497_0_, List<Language> p_239497_1_) {
        Map<String, String> map = Maps.newHashMap();
        boolean flag = false;

        for(Language language : p_239497_1_) {
            flag |= language.isBidirectional();
            String s = String.format("lang/%s.json", language.getCode());

            for(String s1 : p_239497_0_.getResourceNamespaces()) {
                try {
                    ResourceLocation resourcelocation = new ResourceLocation(s1, s);
                    func_239498_a_(p_239497_0_.getAllResources(resourcelocation), map);
                } catch (Exception ignored) {
                }
            }
        }

        return new ClientLanguageMap(ImmutableMap.copyOf(map), flag);
    }

    private static void func_239498_a_(List<IResource> p_239498_0_, Map<String, String> p_239498_1_) {
        for(IResource iresource : p_239498_0_) {
            try (InputStream inputstream = iresource.getInputStream()) {
                LanguageMap.func_240593_a_(inputstream, p_239498_1_::put);
            } catch (IOException ignored) {
            }
        }

    }

}
