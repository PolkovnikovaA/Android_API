package com.example.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    View v;
    private AdapterMask adapterMask;
    private List<Mask> listDog = new ArrayList<>();
    Button btnObn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();   //Убирает шапку в layout

        v = findViewById(com.google.android.material.R.id.ghost_view);

        ListView listView = findViewById(R.id.listDog);
        adapterMask = new AdapterMask(MainActivity.this, listDog);
        listView.setAdapter(adapterMask);

        btnObn = findViewById(R.id.activity_main_Obn);

        new Get().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Search();
        Spinner();
    }

    public void Spinner(){
        try {
            Spinner spinner = findViewById(R.id.activity_main_Sorting);
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

            List<String> categories = new ArrayList<>();
            categories.add("Имя а-я");
            categories.add("Порода а-я");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(dataAdapter);

        }
        catch (Exception ignored){
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            ListView listViewDB = findViewById(R.id.listDog);
            String item = parent.getItemAtPosition(position).toString();
            if(item.equals("Имя а-я")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(listDog, Comparator.comparing(o -> o.getDog().toLowerCase(Locale.ROOT)));
                }
                adapterMask = new AdapterMask(MainActivity.this, listDog);
                listViewDB.setAdapter(adapterMask);
            }
            else if(item.equals("Порода а-я")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(listDog, Comparator.comparing(o -> o.getInfo().toLowerCase(Locale.ROOT)));
                }
                adapterMask = new AdapterMask(MainActivity.this, listDog);
                listViewDB.setAdapter(adapterMask);
            }
        }
        catch (Exception ex){
            Toast.makeText(parent.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    public void Search() {
        try {
            EditText Poisk = findViewById(R.id.activity_main_Poisk);

            Poisk.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    adapterMask.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class Get extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/полковниковаав/api/Dogs/");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //вызываем нашу API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /*
                BufferedReader успрощает чтение текста из потока символов
                InputStreamReader преводит поток байтов в поток символов
                connection.getInputStream() получает поток байтов
                */
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);//кладет строковое значение в потоке
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);//преоброзование строки в json массив
                for (int i = 0;i<tempArray.length();i++)
                {

                    JSONObject Json = tempArray.getJSONObject(i);//Преобразование json объекта в нашу структуру
                    Mask temp = new Mask(
                            Json.getInt("ID"),
                            Json.getString("Dog1"),
                            Json.getString("Info"),
                            Json.getString("Life_expectancy"),
                            Json.getString("Image")
                    );
                    listDog.add(temp);
                    adapterMask.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {


            }
        }
    }

    public void onClickAddendum(View v) {
        switch (v.getId()) {
            case R.id.activity_main_Addendum:
                Intent intent = new Intent(this, Addendum.class);
                startActivity(intent);
                break;
        }
    }

    public void onClickObn(View v) {
        switch (v.getId()) {
            case R.id.activity_main_Obn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}