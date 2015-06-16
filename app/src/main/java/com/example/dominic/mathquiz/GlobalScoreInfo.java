package com.example.dominic.mathquiz;

/**
 * Created by dominic on 15/06/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GlobalScoreInfo
        implements Parcelable
{
    public static final Parcelable.Creator<GlobalScoreInfo> CREATOR = new Parcelable.Creator()
    {
        public GlobalScoreInfo createFromParcel(Parcel paramAnonymousParcel)
        {
            return new GlobalScoreInfo(paramAnonymousParcel);
        }

        public GlobalScoreInfo[] newArray(int paramAnonymousInt)
        {
            return new GlobalScoreInfo[paramAnonymousInt];
        }
    };
    int category;
    String location;
    String name;
    int score;

    public GlobalScoreInfo(int paramInt1, int paramInt2, String paramString1, String paramString2)
    {
        this.category = paramInt1;
        this.score = paramInt2;
        this.name = paramString1;
        this.location = paramString2;
    }

    public GlobalScoreInfo(Parcel paramParcel)
    {
        this.category = paramParcel.readInt();
        this.score = paramParcel.readInt();
        this.name = paramParcel.readString();
        this.location = paramParcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public int getCategory()
    {
        return this.category;
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getName()
    {
        return this.name;
    }

    public int getScore()
    {
        return this.score;
    }

    public String toString()
    {
        return this.category + "," + this.score + "," + this.name + "," + this.location + ";";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeInt(this.category);
        paramParcel.writeInt(this.score);
        paramParcel.writeString(this.name);
        paramParcel.writeString(this.location);
    }
}
