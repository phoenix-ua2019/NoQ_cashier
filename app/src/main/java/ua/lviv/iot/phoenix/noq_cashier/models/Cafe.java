package ua.lviv.iot.phoenix.noq_cashier.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Splitter;
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

    private String mDrawableId;// = -700119;
    private ArrayList<Meal> mCafeMeals;


    public Cafe() { }

    /*public Cafe(String cafeName, String cafeLocation) {
        this.cafeName = cafeName;
        this.cafeLocation = cafeLocation;
    }*/

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
        this(source.readString(), source.readString(), source.readString(), source.readString(), new ArrayList<Meal>());
        source.readList(mCafeMeals, Meal.class.getClassLoader());
    }

    public Cafe (String str) {
        this((new HashMap<>(Splitter.on("], ").withKeyValueSeparator("=[").split(str))));
    }

    public Cafe (HashMap<String, ?> map) {
        HashMap<String, ?> map_ = map;
        cafeName = (String) map.get("name");
        cafeLocation = (String) map.get("location");
        mDrawableId = (String) map.get("icon");
        List<?> tempCafeMeals = (List<HashMap<String, ?>>) map.get("meals");
        mCafeMeals = (ArrayList<Meal>) tempCafeMeals.stream().map(Meal::new).collect(Collectors.toList());
    }

    public Cafe (String name, String location, String email, String iconId, ArrayList<Meal> meals) {
        cafeName = name;
        cafeLocation = location;
        mDrawableId = iconId;
        mCafeMeals = meals;
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


    @PropertyName("name")
    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    @PropertyName("location")
    public String getCafeLocation() {
        return cafeLocation;
    }

    public void setCafeLocation(String cafeLocation) {
        this.cafeLocation = cafeLocation;
    }

    public void setDrawableId(String id) {
        mDrawableId = id;
    }

    @PropertyName("icon")
    public String getDrawableId() {
        return mDrawableId;
    }

    public boolean hasImage(){
        return mDrawableId != null;
    }

    @PropertyName("meals")
    public ArrayList<Meal> getCafeMeals() {
        return mCafeMeals;
    }

    public void setCafeMeals(ArrayList<Meal> meals) {
        mCafeMeals = meals;
    }

}









