
package com.activeitzone.activeecommercecms.Network.response;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.activeitzone.activeecommercecms.Models.Markets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketsResponse implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private List<Markets> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;
    public final static Creator<MarketsResponse> CREATOR = new Creator<MarketsResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MarketsResponse createFromParcel(Parcel in) {
            return new MarketsResponse(in);
        }

        public MarketsResponse[] newArray(int size) {
            return (new MarketsResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1982641778387852337L;

    protected MarketsResponse(Parcel in) {
        in.readList(this.data, (Markets.class.getClassLoader()));
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public MarketsResponse() {
    }

    public List<Markets> getData() {
        return data;
    }

    public void setData(List<Markets> data) {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(success);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
