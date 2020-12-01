package cl.inacap.simpsonsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.simpsonsapp.R;
import cl.inacap.simpsonsapp.dto.Personaje;

public class PersonajesAdapter extends ArrayAdapter<Personaje> {

    private List<Personaje> personajes;
    private int resourceLayout;
    private Context context;

    public PersonajesAdapter(@NonNull Context context, int resource, @NonNull List<Personaje> personajes) {
        super(context, resource, personajes);
        this.context = context;
        this.resourceLayout = resource;
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(resourceLayout, null);
        }

        TextView quoteTxt = view.findViewById(R.id.quote_list_view);
        TextView nombreTxt = view.findViewById(R.id.nombre_list_view);
        ImageView imageView = view.findViewById(R.id.image_list_view);

        quoteTxt.setText(personajes.get(position).getQuote());
        nombreTxt.setText(personajes.get(position).getCharacter());
        Picasso.get().load(this.personajes.get(position).getImage())
                .resize(200, 200)
                .centerInside().into(imageView);

        return view;
    }
}
