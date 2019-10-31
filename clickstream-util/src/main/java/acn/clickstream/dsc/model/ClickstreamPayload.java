package acn.clickstream.dsc.model;

import java.sql.Timestamp;

public class ClickstreamPayload {
    private Timestamp timestamp;
    private String session_id;
    private String user_id;
    private String ip_address;
    private String browser;
    private String page;
    private String element;
    private String action;

    public ClickstreamPayload(){}

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ClickstreamPayload(Timestamp timestamp, String session_id, String user_id, String ip_address, String browser,
            String page, String element, String action) {
        this.timestamp = timestamp;
        this.session_id = session_id;
        this.user_id = user_id;
        this.ip_address = ip_address;
        this.browser = browser;
        this.page = page;
        this.element = element;
        this.action = action;
    }

    
    
}
