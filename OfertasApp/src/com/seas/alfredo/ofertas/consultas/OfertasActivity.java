package com.seas.alfredo.ofertas.consultas;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seas.alfredo.ofertas.R;
import com.seas.alfredo.ofertas.principal.OfertaBO;
import com.seas.alfredo.ofertas.principal.OfertaDetail;
import com.seas.alfredo.ofertas.principal.OfertasLocation;
import com.seas.alfredo.ofertas.servicios.ServiciosOfertasLocation;

/**
 * 
 * @author Alfredo Ramos Kustron
 * 
 * Clase que implementa la actividad que muestra  la lista de ofertas no caducadas.
 *
 */
public class OfertasActivity extends ListActivity {
	
	private IconListViewAdapter m_adapter;
	
	private ServiciosOfertasLocation serviciosOfertasLocation = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ofertas);
		
		Intent intent = getIntent();
		String titulo = intent.getStringExtra(BusquedaForm.TITULO);
		String ciudad = intent.getStringExtra(BusquedaForm.CIUDAD);
		
        this.serviciosOfertasLocation = new ServiciosOfertasLocation(this);		
        
        //Obtiene las ofertas a partir de la ciudad y/o el titulo
		List<OfertaBO> ofertas = serviciosOfertasLocation.getOfertas(ciudad, titulo);
		
		this.m_adapter = new IconListViewAdapter(this, R.layout.iconrow,
				(ArrayList<OfertaBO>) ofertas);
		setListAdapter(this.m_adapter);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		OfertaBO oferta = (OfertaBO) l.getItemAtPosition(position);

		Intent ofertaDetailIntent = new Intent(OfertasLocation.getInstance(), OfertaDetail.class);
		ofertaDetailIntent.putExtra("idOferta", oferta.getId());

		//Inicia la actividad que muestra el detalle de la oferta
		OfertasLocation.getInstance().startActivity(ofertaDetailIntent);

	}
	
	public class IconListViewAdapter extends ArrayAdapter<OfertaBO> {

		private ArrayList<OfertaBO> items;

		public IconListViewAdapter(Context context, int textViewResourceId,
				ArrayList<OfertaBO> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.iconrow, null);
			}
			OfertaBO o = items.get(position);
			if (o != null) {

				// poblamos la lista de elementos (ofertas)

				TextView tt = (TextView) v.findViewById(R.id.title);
				TextView td = (TextView) v.findViewById(R.id.description);
				ImageView im = (ImageView) v.findViewById(R.id.icon);

				if (im != null) {
					im.setImageBitmap(o.getImagen());
				}
				if (tt != null) {
					tt.setText(o.getTitulo());
				}
				if (td != null) {
					td.setText(o.getNegocio() + ": Ahorra " + o.getDescuento() + " en " + o.getCiudad());
				}
			}
			return v;
		}
	}

}
