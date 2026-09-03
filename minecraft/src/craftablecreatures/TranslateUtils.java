/**
 * File created on 20:18 02.09.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.StringTranslate;
import net.minecraft.src.mod_CraftableCreatures;

public class TranslateUtils {
    private static final StringTranslate pieceOfShit = StringTranslate.getInstance();

    public static String translate(String key) {
        return pieceOfShit.func_44024_c().equals("ru_RU") ?
                mod_CraftableCreatures.ruRUTranslations.getProperty(key, "") :
                mod_CraftableCreatures.enUSTranslations.getProperty(key, "");
    }

    public static String translateFormatted(String key, Object... replacement) {
        return pieceOfShit.func_44024_c().equals("ru_RU") ?
                String.format(mod_CraftableCreatures.ruRUTranslations.getProperty(key, ""), replacement) :
                String.format(mod_CraftableCreatures.enUSTranslations.getProperty(key, ""), replacement);
    }

    public static String getCurrentLanguage() {
        return pieceOfShit.func_44024_c();
    }
}