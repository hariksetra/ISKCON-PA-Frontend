package com.giridhari.preachingassistant.model;

import java.util.List;
import java.util.Map;

/**
 * Created by shyam on 19/2/17.
 */
public class UserAccountListResponse {

    private Map<String, List<UserAccountDetailResponse>> _embedded;
    private Map<String, Map<String, String>> _links;

    public Map<String, List<UserAccountDetailResponse>> get_embedded() {

        return _embedded;
    }

    public void set_embedded(Map<String, List<UserAccountDetailResponse>> _embedded) {
        this._embedded = _embedded;
    }
    
    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }
}
