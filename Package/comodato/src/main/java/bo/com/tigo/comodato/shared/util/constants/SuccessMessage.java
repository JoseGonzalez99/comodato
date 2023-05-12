package bo.com.tigo.comodato.shared.util.constants;

public enum SuccessMessage {

    ACCOUNT_UPGRATED("La cuenta ha elevado el grado de su suscripcion"),
    ACCOUNT_DOWNGRATED("La cuenta ha bajado el grado de su suscripcion"),

    SUCCESS_PROCESS("Proceso Satisfactorio");

    private final String msg;

    SuccessMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
