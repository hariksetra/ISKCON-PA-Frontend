package com.giridhari.preachingassistant.model;

import java.util.Map;

/**
 * Created by shyam on 10/10/16.
 */

public class UserAccountDetailResponse {

    private String username;
    private String type;
    private Boolean enabled;
    private Map<String, Map<String, String>> _links;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }
}
