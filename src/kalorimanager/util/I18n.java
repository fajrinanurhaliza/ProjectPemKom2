package kalorimanager.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static final String BASE_NAME = "kalorimanager.resources.message";
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    static {
        // Default ke bahasa Inggris
        currentLocale = new Locale("en");
        bundle = ResourceBundle.getBundle(BASE_NAME, currentLocale);
    }

    public static void setLanguage(String langCode) {
        try {
            currentLocale = new Locale(langCode);
            bundle = ResourceBundle.getBundle(BASE_NAME, currentLocale);
        } catch (Exception e) {
            System.err.println("Gagal mengubah bahasa: " + e.getMessage());
            // Fallback ke bahasa default
            currentLocale = new Locale("en");
            bundle = ResourceBundle.getBundle(BASE_NAME, currentLocale);
        }
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "!" + key + "!"; // Return key dengan tanda seru jika tidak ditemukan
        }
    }

    public static void reload() {
        bundle = ResourceBundle.getBundle(BASE_NAME, currentLocale);
    }
}