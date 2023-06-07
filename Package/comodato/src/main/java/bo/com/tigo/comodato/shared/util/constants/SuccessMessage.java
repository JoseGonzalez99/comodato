package bo.com.tigo.comodato.shared.util.constants;

public enum SuccessMessage {

    SUCCESS_PROCESS("Proceso Satisfactorio");

    private final String msg;

    SuccessMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
