package ua.lviv.iot.phoenix.noq_cashier.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import ua.lviv.iot.phoenix.noq_cashier.activities.Useful;

public class Order implements Parcelable {

    private Date mDate = new Date();
    private String mTime;
    private Cafe mCafe;
    private double mSum;
    private int status;
    private int pos;
    private int userPos;
    private int position;
    private String Uid;
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            try {
                return new Order(source);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Order();
            }
        }
        @Override
        public Order[] newArray(int size) {
         return new Order[size];
        }
     };

    public Order() {

    }

    public Order (Object o) {
        this((Map<String, ?>) o);
    }

    public Order (Map<String, ?> map) {
        mCafe = new Cafe(map.get("cafe"));
        mTime = (String) map.get("time");
        Uid = (String) map.get("uid");
        pos = ((Long) map.get("pos")).intValue();
        try {
            mSum = (Double) map.get("sum");
        } catch (Exception e){
            mSum = (Long) map.get("sum");
        }
        status = map.containsKey("status") ? ((Long) map.get("status")).intValue() : 0;
        userPos = ((Long) map.get("userPos")).intValue();

    }

    public Order(String time, double sum, Date date, Cafe cafe){
        mTime = time;
        mSum = sum;
        mDate = date;
        mCafe = cafe;
    }

    Order(String time, double sum, String id, Date date, Cafe cafe) {
        this(time, sum, date, cafe);
        Uid = id;
    }

    Order(@NotNull Parcel source) throws ParseException {
        this(source.readString(), source.readInt(), source.readString(),
                DateFormat.getDateInstance().parse(source.readString()),
                source.readParcelable(Cafe.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTime);
        out.writeDouble(mSum);
        out.writeString(Uid);
        out.writeString(mDate.toString());
        out.writeParcelable(mCafe,1);
    }

    public double getSum() {
        return mSum;
    }
    public Date getDate() {
        return mDate;
    }
    public Cafe getCafe() {
        return mCafe;
    }
    public String getTime() {
        return mTime;
    }
    public int getStatus() {
        return status;
    }
    @Exclude
    public boolean isDone() {
        return status == 1;
    }
    @Exclude
    public boolean isNew() {
        return status == 0;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Order setPos(int pos) {
        this.pos = pos;
        return this;
    }
    public int getPos() {
        return pos;
    }
    public int getUserPos() {
        return userPos;
    }
    public String getUid() {
        return Uid;
    }

    @Override
    public String toString() {
        return "time:"+mTime+", sum:"+mSum+", cafe:"+mCafe+", pos's:("+pos+";"+position+";"+userPos+") .";
    }
}
