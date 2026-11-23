package ar.org.proyectoFinal.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lee la configuración del entorno.
 * Prioridad: propiedad de sistema (-Dclave=valor) > archivo config/{env}.properties.
 * El entorno se elige con -Denv=qa (por defecto "qa").
 */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        String env = System.getProperty("env", "qa");
        String path = "config/" + env + ".properties";
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("No se encontró el archivo de configuración: " + path);
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo " + path, e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = System.getProperty(key, PROPS.getProperty(key));
        if (value == null) {
            throw new IllegalStateException("Falta la propiedad de configuración: " + key);
        }
        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = System.getProperty(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }
}
