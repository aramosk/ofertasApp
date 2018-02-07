package com.seas.alfredo.ofertas.principal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seas.alfredo.ofertas.R;
import com.seas.alfredo.ofertas.servicios.ServiciosOfertasLocation;

/**
 * 
 * @author Alfredo Ramos Kustron
 * 
 * Clase que implementa la actividad que muestra el detalle de la oferta.
 *
 */
public class OfertaDetail extends Activity {

	private ServiciosOfertasLocation serviciosOfertasLocation = null;
	private OfertaBO oferta = null;
	private static OfertaDetail ofertaDetail = null;

	public static OfertaDetail getInstance() {
		if (ofertaDetail == null) {
			ofertaDetail = new OfertaDetail();
		}
		return ofertaDetail;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oferta);

		ofertaDetail = this;

		Intent intent = getIntent();
		String idOferta = intent.getStringExtra("idOferta");

		serviciosOfertasLocation = new ServiciosOfertasLocation(OfertaDetail.getInstance());

		//Obtenemos la ofertas a partir de su identificador
		oferta = serviciosOfertasLocation.getOferta(idOferta);

		TextView tituloView = (TextView) findViewById(R.id.Titulo);
		TextView descripcionView = (TextView) findViewById(R.id.descripcion);
		TextView descuentoView = (TextView) findViewById(R.id.descuento);
		TextView negocioView = (TextView) findViewById(R.id.negocio);
		TextView direccionView = (TextView) findViewById(R.id.direccion);
		TextView precioView = (TextView) findViewById(R.id.precio);
		ImageView imageView = (ImageView) findViewById(R.id.imagen);

		tituloView.setText(oferta.getTitulo());
		descripcionView.setText(oferta.getDescripcion());
		negocioView.setText(oferta.getNegocio());
		direccionView.setText(oferta.getDireccion());
		descuentoView.setText(oferta.getDescuento());
		precioView.setText(oferta.getPrecio());
		imageView.setImageBitmap(oferta.getImagen());

		final Button button = (Button) findViewById(R.id.comprarButton);
		
		//Maneja el evento click del boton de compra
		//Al presionar el boton de compra despliega un dialogo 
		//con el periodo de validez y el codigo de la compra
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//Calculamos el periodo de validez de la oferta
				String periodoValidez = serviciosOfertasLocation
						.periodoValidezOferta(oferta.getFechaCaducidad());
				String codigoCompra = serviciosOfertasLocation.generarCodigoCompra();

				Context context = OfertaDetail.getInstance();

				StringBuffer mensaje = new StringBuffer("");
				
				Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.compra_dialog);
				dialog.setTitle("Datos de la compra");

				TextView text = (TextView) dialog.findViewById(R.id.text);
				ImageView image = (ImageView) dialog.findViewById(R.id.image);				
				
				if (serviciosOfertasLocation.comprar(oferta, codigoCompra)) {
					mensaje.append("Periodo de validez: " + periodoValidez + "\n\n");
					mensaje.append("Codigo compra: " + codigoCompra);
					image.setImageResource(R.drawable.check);
				} else {
					mensaje.append("Error al realizar la compra");
					image.setImageResource(R.drawable.error);
				}
		
				text.setText(mensaje.toString());
				
				dialog.show();

			}
		});

	}
}
