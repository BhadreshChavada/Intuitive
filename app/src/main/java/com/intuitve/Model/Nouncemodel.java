
package com.intuitve.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nouncemodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("controller")
    @Expose
    private String controller;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("nonce")
    @Expose
    private String nonce;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

}
