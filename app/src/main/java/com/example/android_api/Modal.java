package com.example.android_api;

public class Modal {
    private String Dog;
    private String Info;
    private String Life_expectancy;
    private String Image;

    public Modal(String dog, String info, String life_expectancy, String image) {
        Dog = dog;
        Info = info;
        Life_expectancy = life_expectancy;
        Image = image;
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
}
