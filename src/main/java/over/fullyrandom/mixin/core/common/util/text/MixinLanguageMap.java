package over.fullyrandom.mixin.core.common.util.text;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.LanguageMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import over.fullyrandom.Fullyrandom;
import over.fullyrandom.json.Languages;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

@Mixin(LanguageMap.class)
public class MixinLanguageMap {

    @Shadow @Final private static final Gson field_240591_b_ = new Gson();
    @Shadow @Final private static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");

    /**
     * @author OverInfrared
     */
    @Overwrite
    public static void func_240593_a_(InputStream p_240593_0_, BiConsumer<String, String> p_240593_1_) {
        JsonObject jsonobject = field_240591_b_.fromJson(new InputStreamReader(p_240593_0_, StandardCharsets.UTF_8), JsonObject.class);
        if (jsonobject.toString().contains("fullyrandom"))
            jsonobject = Languages.createLanguageFile();
        for(Map.Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            String s = NUMERIC_VARIABLE_PATTERN.matcher(JSONUtils.getString(entry.getValue(), entry.getKey())).replaceAll("%$1s");
            p_240593_1_.accept(entry.getKey(), s);
        }

    }


}
