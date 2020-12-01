package cl.inacap.simpsonsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.simpsonsapp.adapter.PersonajesAdapter;
import cl.inacap.simpsonsapp.dto.Personaje;

public class MainActivity extends AppCompatActivity {

    private ListView personajesListView;
    private PersonajesAdapter personajesAdapter;
    private RequestQueue queue;
    private Button btn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.personajesListView = findViewById(R.id.main_list_view);
        initSpinnerAdapter();
        initButton();
    }

    private void initSpinnerAdapter() {
        spinner = findViewById(R.id.numero_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numero, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    private void initButton() {
        btn = findViewById(R.id.solicitarbtn);
        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objetenerFrases();
            }
        });
    }

    private void initListViewAdapter(List<Personaje> personajes) {
        this.personajesAdapter = new PersonajesAdapter(this,
                R.layout.item_list_personajes, personajes);
        this.personajesListView.setAdapter(this.personajesAdapter);
    }

    private void objetenerFrases() {
        queue = Volley.newRequestQueue(this);
        String url = "https://thesimpsonsquoteapi.glitch.me/quotes?count=" + this.spinner.getSelectedItem();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", "onResponse: " + response);
                try {
                    initListViewAdapter(agarrarDataso(new JSONArray(response)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Peticion", "Error de peticion");
            }
        });

        queue.add(stringRequest);
    }

    public List<Personaje> agarrarDataso(JSONArray jsonArray) {
        List<Personaje> personajes = new ArrayList();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Personaje p = new Personaje();
                p.setCharacter(jsonObject.getString("character"));
                p.setImage(jsonObject.getString("image"));
                p.setQuote(jsonObject.getString("quote"));
                personajes.add(p);
            }
            return personajes;
        } catch (Exception ex) {
            Log.e("PERSONAJES QUOTE", "Error de dataso");
            return null;
        }
    }
}