
package com.activeitzone.activeecommercecms.Network.response;

import java.util.List;

import com.activeitzone.activeecommercecms.Models.Shops;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopsResponse {

    @SerializedName("data")
    @Expose
    private List<Shops> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Shops> getData() {
        return data;
    }

    public void setData(List<Shops> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
