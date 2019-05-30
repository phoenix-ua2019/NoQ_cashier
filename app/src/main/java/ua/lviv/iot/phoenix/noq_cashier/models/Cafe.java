package ua.lviv.iot.phoenix.noq_cashier.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@IgnoreExtraProperties
public class Cafe implements Parcelable {
    private String cafeName;
    private String cafeLocation;
    private String cafeEmail;
    private String mDrawableId;
    private String Cid;
    private ArrayList<Meal> mCafeMeals;


    public Cafe() {
    }

    public static final Creator<Cafe> CREATOR =
            new Creator<Cafe>() {
                @Override
                public Cafe createFromParcel(Parcel source) {
                    return new Cafe(source);
                }

                @Override
                public Cafe[] newArray(int size) {
                    return new Cafe[size];
                }
            };

    public Cafe (Object o) {
        this((HashMap<String, ?>) o);
    }

    public Cafe (Parcel source) {
        this(source.readString(), source.readString(), source.readString(), source.readString(), new ArrayList<>());
        source.readList(mCafeMeals, Meal.class.getClassLoader());
    }

    public Cafe (HashMap<String, ?> map) {
        HashMap<String, ?> map_ = map;
        cafeName = (String) map.get("name");
        cafeLocation = (String) map.get("location");
        mDrawableId = (String) map.get("icon");
        Cid = (String) map.get("cid");
        List<?> tempCafeMeals = (List<HashMap<String, ?>>) map.get("meals");
        mCafeMeals = (ArrayList<Meal>) tempCafeMeals.stream().map(Meal::new).collect(Collectors.toList());
    }

    public Cafe (String name, String location, String email, String iconId) {
        cafeName = name;
        cafeLocation = location;
        cafeEmail = email;
        mDrawableId = iconId;
    }

    public Cafe (String name, String location, String email, String iconId, ArrayList<Meal> meals) {
        this(name, location, email, iconId);
        mCafeMeals = meals;
    }

    public Cafe (String name, String location, String email, String iconId, String Cid) {
        this(name, location, email, iconId);
        this.Cid = Cid;
    }

    @Override
    public String toString() {
        return "name=[" + cafeName +
                "], location=[" + cafeLocation +
                "], icon=[" + mDrawableId +
                "], meals=[" + mCafeMeals + "] ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(cafeName);
        out.writeString(cafeLocation);
        out.writeString(mDrawableId);
        out.writeList(mCafeMeals);
    }

    public String getName() {
        return cafeName;
    }

    public String getLocation() {
        return cafeLocation;
    }

    public String getIcon() {
        return mDrawableId;
    }

    public String getEmail() {
        return cafeEmail;
    }

    public String getCid() {
        return Cid;
    }

    @PropertyName("meals")
    public ArrayList<Meal> getCafeMeals() {
        return mCafeMeals;
    }

}









