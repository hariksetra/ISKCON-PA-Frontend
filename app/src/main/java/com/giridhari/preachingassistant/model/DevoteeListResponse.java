package com.giridhari.preachingassistant.model;

import java.util.List;
import java.util.Map;

/**
 * Created by shyam on 29/10/16.
 */
public class DevoteeListResponse {

    private Map<String, List<DevoteeDetailsResponse>> _embedded;
    private Map<String, Map<String, String>> _links;
    private Map<String, String> page;

    public Map<String, List<DevoteeDetailsResponse>> get_embedded() {
        return _embedded;
    }

    public void set_embedded(Map<String, List<DevoteeDetailsResponse>> _embedded) {
        this._embedded = _embedded;
    }

    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }

    public Map<String, String> getPage() {
        return page;
    }

    public void setPage(Map<String, String> page) {
        this.page = page;
    }
}
