package over.fullyrandom.mixin.core.common.util.text;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.util.text.LanguageMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import over.fullyrandom.json.Languages;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Mixin(LanguageMap.class)
public class MixinLanguageMap {

    @Shadow @Final private static final Gson field_240591_b_ = new Gson();

    @Redirect(method = "func_240593_a_", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private static Object func(Gson gson, Reader json, Class<JsonObject> classOfT) {
        JsonObject jsonObject = field_240591_b_.fromJson(json, classOfT);
        if (jsonObject.toString().contains("fullyrandom"))
            return Languages.createLanguageFile();
        return jsonObject;
    }

}
