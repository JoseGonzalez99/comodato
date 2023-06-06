package bo.com.tigo.comodato.shared.util;

public class StringUtils {
    
    private StringUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class!");
    }

    public static String nonNull(String string) {
        return string == null ? "" : string;
    }
}
