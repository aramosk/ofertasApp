package com.seas.alfredo.ofertas.consultas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.seas.alfredo.ofertas.R;
import com.seas.alfredo.ofertas.principal.OfertasLocation;

/**
 * 
 * @author Alfredo Ramos Kustron
 * 
 * Clase que implementa la actividad del buscador de ofertas.
 *
 */
public class BusquedaForm extends Activity implements OnItemSelectedListener {
	private EditText tituloEdit;
	private Spinner ciudadesSpinner;
	private String ciudadSeleccionada = "";
	
    public final static String CIUDAD = "ciudad";
    public final static String TITULO = "titulo";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		final Button button = (Button) findViewById(R.id.BuscarButton);
		tituloEdit = (EditText) findViewById(R.id.tituloEdit);
		ciudadesSpinner = (Spinner) findViewById(R.id.ciudadesSpinner);
		ciudadesSpinner.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.ciudades_array,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ciudadesSpinner.setAdapter(adapter);

		//Maneja el evento click del boton de busqueda de ofertas
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent ofertasIntent = new Intent(
						OfertasLocation.getInstance(), OfertasActivity.class);
				ofertasIntent.putExtra(TITULO, tituloEdit.getText()
						.toString());
				ofertasIntent.putExtra(CIUDAD, ciudadSeleccionada);
				
				//Inicia la actividad que muestra la lista de ofertas no caducadas
				OfertasLocation.getInstance().startActivity(ofertasIntent);
			}
		});
	}

	// Maneja la seleccion de items del spinner de ciudades 
	// y actualiza el valor de la variable ciudadSeleccionada
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long arg3) {
		ciudadSeleccionada = (String) parent.getItemAtPosition(pos);
		Log.i("log_tag", ciudadSeleccionada);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
