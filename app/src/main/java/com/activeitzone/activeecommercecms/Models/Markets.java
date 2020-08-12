
package com.activeitzone.activeecommercecms.Models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Markets implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private String logo;
    public final static Creator<Markets> CREATOR = new Creator<Markets>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Markets createFromParcel(Parcel in) {
            return new Markets(in);
        }

        public Markets[] newArray(int size) {
            return (new Markets[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8609122570564738117L;

    protected Markets(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.logo = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Markets() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(logo);
    }

    public int describeContents() {
        return  0;
    }

}
