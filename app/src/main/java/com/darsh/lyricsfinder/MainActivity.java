package com.darsh.lyricsfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edtArtist, edtSong;
    TextView txtLyrics;
    Button btnLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLyrics = findViewById(R.id.txtLyrics);
        btnLyrics = findViewById(R.id.btnLyrics);
        edtArtist = findViewById(R.id.edtArtistName);
        edtSong = findViewById(R.id.edtSongName);

        btnLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.lyrics.ovh/v1/"+edtArtist.getText().toString()+"/"+edtSong.getText().toString();
                url.replace(" ","20%");
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            txtLyrics.setText(jsonObject.getString("lyrics"));
                        }

                        catch(JSONException e){
                            Toast.makeText(MainActivity.this,"Lyrics not found!",Toast.LENGTH_LONG);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        txtLyrics.setText("Lyrics not found!");
                    }
                });
                requestQueue.add(jsonObjectRequest);
                
            }
        });

    }
}