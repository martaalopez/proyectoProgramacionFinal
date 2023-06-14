package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method<T> implements AutoCloseable {


    public static boolean validarDNI(String id_client) {
        String patron = "\\d{8}[A-HJ-NP-TV-Z]";
        return id_client.matches(patron);
    }
    public  static boolean GmailValidate(String gmail) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(gmail);
        return ((Matcher) matcher).matches();
    }

    @Override
    public void close() throws Exception {
    }

    public class Constants {
        public static final String WARNING_TITLE = "Warning";
        public static final String WARNING_ID_WRONG = "The ID is wrong.";
        public static final String WARNING_ENTER_GMAIL = "Enter gmail again.";
        public static final String WARNING_COMPLETE_FIELDS = "Complete all the fields.";

        public static final String CORRECT="Username and password is correct";

        public static final String INCORRECT="Invalid username or password";

    }

}
