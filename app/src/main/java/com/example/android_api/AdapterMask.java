package com.example.android_api;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdapterMask  extends BaseAdapter implements Filterable {

    private Context mContext;
    List<Mask> maskList;
    private List<Mask> MASKList;

    public AdapterMask(Context mContext, List<Mask> maskList)
    {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.zagl);
        }
        return BitmapFactory.decodeStream(input);
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.zagl);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(mContext,R.layout.activity_mask, null);
        ImageView Image=v.findViewById(R.id.activity_mask_Image);
        TextView Name=v.findViewById(R.id.activity_mask_Name);
        TextView Breed=v.findViewById(R.id.activity_mask_Breed);
        TextView Age =v.findViewById(R.id.activity_mask_Age);

        Mask mask=maskList.get(i);
        Name.setText(mask.getDog());
        Breed.setText(mask.getInfo());
        Age.setText(mask.getLife_expectancy());
        Image.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis = new Intent(mContext,Details.class);
                intenDetalis.putExtra("Dogs", mask);
                mContext.startActivity(intenDetalis);

            }
        });


        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                maskList = (ArrayList<Mask>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Mask> FilteredArrList = new ArrayList<>();

                if (MASKList == null) {
                    MASKList = new ArrayList<>(maskList);
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = MASKList.size();
                    results.values = MASKList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < MASKList.size(); i++) {
                        String data = MASKList.get(i).getDog();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Mask(MASKList.get(i).getId(),
                                    MASKList.get(i).getDog(),
                                    MASKList.get(i).getInfo(),
                                    MASKList.get(i).getLife_expectancy(),
                                    MASKList.get(i).getImage()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }


}

