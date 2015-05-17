package utils;

public enum AqMessage {
    EMAIL_CONF_REQ("Please confirm your email before signing in", "Confirme su email antes de iniciar sesión");


    private final String en;
    private final String es;

    AqMessage(String en, String es) {
        this.en = en;
        this.es = es;
    }

    public String en() {
        return en;
    }

    public String es() {
        return es;
    }
}
