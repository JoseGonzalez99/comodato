package bo.com.tigo.comodato.shared.util;


public class AppConfiguration {

   // @Autowired
   // private Environment env;
    public String apiUrl;

    private String as400Ip;

    private String as400User;

    private String as400Password;

    private Integer as400SoTimeout;

    private Integer as400MaxInactivity;

    private Integer as400ConnectionTimeout;

    private Integer as400MaxConnections;

    private Boolean as400PretestConnection;

    private String as400Message;

    private String InvoiveComodato;

    /*Propiedades de response*/
    private String invoise_completionMethod;
    private Boolean invoise_isMandatory;
    private String invoise_relatedEntity_href;
    private String invoise_relatedEntity_name;
    private String invoise_relatedEntity_role;
    private String invoise_relatedEntity_type;
    private String invoise_type;
    private String invoise_baseType;

    public AppConfiguration() {
    }

    /*
        public AppConfiguration(Environment env) {
            this.apiUrl =env.getProperty("api.url");
            this.as400Ip = env.getProperty("as400.plex.ip");
            this.as400User = env.getProperty("as400.plex.user");
            this.as400Password = env.getProperty("as400.plex.pass");
            this.as400SoTimeout = Integer.getInteger(env.getProperty("as400.plex.connectionTimeout "));
            this.as400MaxInactivity = Integer.getInteger(env.getProperty("as400.plex.maxConnections"));
            this.as400ConnectionTimeout = Integer.getInteger(env.getProperty("as400.plex.socketTimeout"));
            this.as400MaxConnections = Integer.getInteger(env.getProperty("as400.plex.maxInactivity"));
            this.as400PretestConnection = Objects.equals(env.getProperty("as400.plex.pretestConnection"), "true");
            this.as400Message = env.getProperty("as400.db.driver");
            this.InvoiveComodato = env.getProperty("as400.db.url");
        }
    */

    public String getInvoiveComodato() {
        return InvoiveComodato;
    }

    public void setInvoiveComodato(String invoiveComodato) {
        InvoiveComodato = invoiveComodato;
    }

    public String getInvoise_completionMethod() {
        return invoise_completionMethod;
    }

    public void setInvoise_completionMethod(String invoise_completionMethod) {
        this.invoise_completionMethod = invoise_completionMethod;
    }

    public Boolean getInvoise_isMandatory() {
        return invoise_isMandatory;
    }

    public void setInvoise_isMandatory(Boolean invoise_isMandatory) {
        this.invoise_isMandatory = invoise_isMandatory;
    }

    public String getInvoise_relatedEntity_href() {
        return invoise_relatedEntity_href;
    }

    public void setInvoise_relatedEntity_href(String invoise_relatedEntity_href) {
        this.invoise_relatedEntity_href = invoise_relatedEntity_href;
    }

    public String getInvoise_relatedEntity_name() {
        return invoise_relatedEntity_name;
    }

    public void setInvoise_relatedEntity_name(String invoise_relatedEntity_name) {
        this.invoise_relatedEntity_name = invoise_relatedEntity_name;
    }

    public String getInvoise_relatedEntity_role() {
        return invoise_relatedEntity_role;
    }

    public void setInvoise_relatedEntity_role(String invoise_relatedEntity_role) {
        this.invoise_relatedEntity_role = invoise_relatedEntity_role;
    }

    public String getInvoise_relatedEntity_type() {
        return invoise_relatedEntity_type;
    }

    public void setInvoise_relatedEntity_type(String invoise_relatedEntity_type) {
        this.invoise_relatedEntity_type = invoise_relatedEntity_type;
    }

    public String getInvoise_type() {
        return invoise_type;
    }

    public void setInvoise_type(String invoise_type) {
        this.invoise_type = invoise_type;
    }

    public String getInvoise_baseType() {
        return invoise_baseType;
    }

    public void setInvoise_baseType(String invoise_baseType) {
        this.invoise_baseType = invoise_baseType;
    }

    public String getAs400Message() { return as400Message; }

    public void setAs400Message(String as400Message) { this.as400Message = as400Message; }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAs400Ip() {
        return as400Ip;
    }

    public void setAs400Ip(String as400Ip) {
        this.as400Ip = as400Ip;
    }

    public String getAs400User() {
        return as400User;
    }

    public void setAs400User(String as400User) {
        this.as400User = as400User;
    }

    public String getAs400Password() {
        return as400Password;
    }

    public void setAs400Password(String as400Password) {
        this.as400Password = as400Password;
    }

    public Integer getAs400SoTimeout() {
        return as400SoTimeout;
    }

    public void setAs400SoTimeout(Integer as400SoTimeout) {
        this.as400SoTimeout = as400SoTimeout;
    }

    public Integer getAs400MaxInactivity() {
        return as400MaxInactivity;
    }

    public void setAs400MaxInactivity(Integer as400MaxInactivity) {
        this.as400MaxInactivity = as400MaxInactivity;
    }

    public Integer getAs400ConnectionTimeout() {
        return as400ConnectionTimeout;
    }

    public void setAs400ConnectionTimeout(Integer as400ConnectionTimeout) {
        this.as400ConnectionTimeout = as400ConnectionTimeout;
    }

    public Integer getAs400MaxConnections() {
        return as400MaxConnections;
    }

    public void setAs400MaxConnections(Integer as400MaxConnections) {
        this.as400MaxConnections = as400MaxConnections;
    }

    public Boolean getAs400PretestConnection() {
        return as400PretestConnection;
    }

    public void setAs400PretestConnection(Boolean as400PretestConnection) {
        this.as400PretestConnection = as400PretestConnection;
    }

    @Override
    public String toString() {
        return "AppConfiguration{" +
                "apiUrl='" + apiUrl + '\'' +
                ", as400Ip='" + as400Ip + '\'' +
                ", as400User='" + as400User + '\'' +
                ", as400Password='" + as400Password + '\'' +
                ", as400SoTimeout=" + as400SoTimeout +
                ", as400MaxInactivity=" + as400MaxInactivity +
                ", as400ConnectionTimeout=" + as400ConnectionTimeout +
                ", as400MaxConnections=" + as400MaxConnections +
                ", as400PretestConnection=" + as400PretestConnection +
                ", as400Message='" + as400Message + '\'' +
                ", as400UpgradeOrDowngrade='" + InvoiveComodato + '\'' +
                '}';
    }
}
