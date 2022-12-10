package com.example.android_api;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {
    private int Id;
    private String Dog;
    private String Info;
    private String Life_expectancy;
    private String Image;

    protected Mask(Parcel in) {
        Id = in.readInt();
        Dog = in.readString();
        Info = in.readString();
        Life_expectancy = in.readString();
        Image = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public void setId(int id)
    {
        this.Id = id;
    }
    public void setDog(String dog)
    {
        Dog = dog;
    }
    public void setInfo(String info)
    {
        Info = info;
    }
    public void setLife_expectancy(String life_expectancy)
    {
        Life_expectancy = life_expectancy;
    }
    public void setImage(String image)
    {
        Image = image;
    }

    public int getId()
    {
        return Id;
    }
    public String getDog()
    {
        return Dog;
    }
    public String getInfo()
    {
        return Info;
    }
    public String getLife_expectancy()
    {
        return Life_expectancy;
    }
    public String getImage()
    {
        return Image;
    }

    public Mask(int id, String dog, String info, String life_expectancy, String image)
    {
        this.Id = id;
        Dog = dog;
        Info = info;
        Life_expectancy = life_expectancy;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Dog);
        parcel.writeString(Info);
        parcel.writeString(Life_expectancy);
        parcel.writeString(Image);
    }
}
