package com.seas.alfredo.ofertas.servicios;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.seas.alfredo.ofertas.conexion.Post;
import com.seas.alfredo.ofertas.principal.OfertaBO;
import com.seas.alfredo.ofertas.principal.OfertasLocation;

/**
 * 
 * @author Alfredo Ramos Kustron
 * 
 *         Clase que implementa los servicios de la aplicacion
 *
 */
public class ServiciosOfertasLocation {

	public ConfigurationService configuration = null;

	public ServiciosOfertasLocation(Context context) {
		if (configuration == null) {
			configuration = new ConfigurationService(context);
		}
	}

	private Bitmap downloadFile(String imageHttpAddress) {
		URL imageUrl = null;
		Bitmap loadedImage = null;
		HttpURLConnection conn = null;
		try {
			imageUrl = new URL(imageHttpAddress);
			conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
		} catch (IOException e) {
			Toast.makeText(OfertasLocation.getInstance().getApplicationContext(),
					"Error cargando la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return loadedImage;
	}

	private OverlayItem getOverlayItem(String titulo, String snippet, String latitud, String longitud, Drawable drawable) {
		String coordinates[] = { latitud, longitud };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);
		GeoPoint point = new GeoPoint((int) (lng * 1E6), (int) (lat * 1E6));
		OverlayItem overlayitem = new OverlayItem(point, titulo, snippet);
		overlayitem.setMarker(OfertasLocation.getInstance().getItemizedoverlay().boundCenterBottomAux(drawable));
		return overlayitem;
	}

	/*
	 * Realiza la operación de compra de la oferta
	 */
	public boolean comprar(OfertaBO oferta, String codigo) {
		boolean result = false;

		final String mCodigo = "codigo";
		final String mIdOwner = "idOwner";
		final String mIdOferta = "idOferta";

		Post post = null;

		try {
			// INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();

			// Parametros de la llamada
			parametros.add(mCodigo);
			parametros.add(codigo);
			parametros.add(mIdOwner);
			parametros.add(oferta.getOwnerId());
			parametros.add(mIdOferta);
			parametros.add(oferta.getId());

			Log.i("log_tag", "Datos de la compra. Codigo: " + codigo + ", IdOwner: " + oferta.getOwnerId()
					+ ", IdOferta: " + oferta.getId());

			// Llamada a Servidor Web PHP para el alta de la compra
			post = new Post();
			JSONArray datos = post.getServerData(parametros,
					configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.comprar"));

			Log.i("log_tag", "datos: " + datos);
			if (datos != null && datos.length() > 0) {

				JSONObject json_data = datos.getJSONObject(0);

				Log.i("log_tag", "Resultado de la operacion de compra: " + json_data.getString("RESULT"));
				if (json_data.getString("RESULT").equals("insertado")) {
					result = true;
					Log.i("log_tag", "oferta comprada con el codigo: " + codigo);
				} else {
					result = false;
				}

			}

		} catch (Exception e) {
			Toast.makeText(OfertasLocation.getInstance().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return result;
	}

	/**
	 * Consulta las ofertas a partir de la ciudad y/o titulo.
	 * 
	 * @param ciudad
	 *            la ciudad
	 * @param titulo
	 *            el el titulo
	 * @return la lista de ofertas
	 */
	public List<OfertaBO> getOfertas(String ciudad, String titulo) {
		List<OfertaBO> result = new ArrayList<OfertaBO>();

		final String mCiudad = "ciudad";
		final String mTitulo = "titulo";

		Post post = null;

		try {
			//ImageView imagenView = new ImageView(OfertasLocation.getInstance().getBaseContext());
			// INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();

			// Parametros de la llamada
			parametros.add(mCiudad);
			parametros.add(ciudad);
			parametros.add(mTitulo);
			parametros.add(titulo);

			// Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,
					configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.ofertasfiltro"));
			if (datos != null && datos.length() > 0) {
				for (int i = 0; i < datos.length(); i++) {
					JSONObject json_data = datos.getJSONObject(i);

					OfertaBO ofertaBO = new OfertaBO();

					ofertaBO.setId(json_data.getString("ID"));
					ofertaBO.setTitulo(json_data.getString("TITULO"));
					ofertaBO.setDescripcion(json_data.getString("DESCRIPCION"));
					ofertaBO.setNegocio(json_data.getString("NEGOCIO"));
					ofertaBO.setDireccion(json_data.getString("DIRECCION"));
					ofertaBO.setCiudad(json_data.getString("CIUDAD"));
					ofertaBO.setDescuento(json_data.getString("DESCUENTO"));
					ofertaBO.setPrecio(json_data.getString("PRECIO"));
					ofertaBO.setFechaCaducidad(json_data.getString("FECHA_CADUCIDAD"));
					ofertaBO.setNegocio(json_data.getString("NEGOCIO"));
					ofertaBO.setOwnerId(json_data.getString("OWNER_ID"));

					StringBuffer bufImageURL = new StringBuffer(
							configuration.getProperty("com.seas.alfredo.ofertas.serverdomain")).append("/images/");
					String imagen = json_data.getString("IMAGEN");
					if (imagen.equals("null")) {
						bufImageURL.append("oferta.png");
					} else {
						bufImageURL.append(imagen);
					}

					Bitmap loadedImage = downloadFile(bufImageURL.toString());
					
					if(loadedImage==null){
						loadedImage = downloadFile(configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.images.oferta"));
					}
					
					ofertaBO.setImagen(loadedImage);
					if (!ofertaCaducada(ofertaBO.getFechaCaducidad())) {
						result.add(ofertaBO);
					} else {
						Log.i("log_tag", "Oferta caducada el dia: " + ofertaBO.getFechaCaducidad());
					}

				}
			}

		} catch (Exception e) {
			Toast.makeText(OfertasLocation.getInstance().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return result;
	}

	/**
	 * Consulta el detalle de la oferta.
	 * 
	 * @param idOferta
	 *            el identificador de la oferta
	 * @return el BO con el detalle de la oferta
	 */
	public OfertaBO getOferta(String idOferta) {
		OfertaBO result = new OfertaBO();

		Post post = null;

		try {
			// INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();

			// Parametros de la llamada
			parametros.add("idOferta");
			parametros.add(idOferta);

			// Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,
					configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.ofertadetail"));
			if (datos != null && datos.length() > 0) {
				for (int i = 0; i < datos.length(); i++) {
					JSONObject json_data = datos.getJSONObject(i);

					result.setId(json_data.getString("ID"));
					result.setTitulo(json_data.getString("TITULO"));
					result.setDescripcion(json_data.getString("DESCRIPCION"));
					result.setNegocio(json_data.getString("NEGOCIO"));
					result.setDireccion(json_data.getString("DIRECCION"));
					result.setCiudad(json_data.getString("CIUDAD"));
					result.setDescuento(json_data.getString("DESCUENTO"));
					result.setPrecio(json_data.getString("PRECIO"));
					result.setFechaCaducidad(json_data.getString("FECHA_CADUCIDAD"));
					result.setNegocio(json_data.getString("NEGOCIO"));
					result.setOwnerId(json_data.getString("OWNER_ID"));

					StringBuffer bufImageURL = new StringBuffer(
							configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.images"));
					String imagen = json_data.getString("IMAGEN");
					if (imagen.equals("null")) {
						bufImageURL.append("oferta.png");
					} else {
						bufImageURL.append(imagen);
					}

					Bitmap loadedImage = downloadFile(bufImageURL.toString());
					
					if(loadedImage==null){
						loadedImage = downloadFile(configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.images.oferta"));
					}
					result.setImagen(loadedImage);
				}
			}

		} catch (Exception e) {
			Toast.makeText(OfertasLocation.getInstance().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return result;
	}

	/**
	 * Consulta las ofertas no caducadas para pintarlas en el mapa
	 */
	public void getOfertas() {
		Post post = null;
		try {
			ImageView imagenView = new ImageView(OfertasLocation.getInstance().getBaseContext());
			// INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();

			// Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,
					configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.ofertas"));
			if (datos != null && datos.length() > 0) {
				for (int i = 0; i < datos.length(); i++) {
					JSONObject json_data = datos.getJSONObject(i);

					String idOferta = json_data.getString("ID");
					String titulo = json_data.getString("TITULO");
					String longitud = json_data.getString("LATITUD");
					String latitud = json_data.getString("LONGITUD");
					String fechaCaducidad = json_data.getString("FECHA_CADUCIDAD");

					String urlImagen = configuration.getProperty("com.seas.alfredo.ofertas.serverdomain.images.restaurant");
					Bitmap loadedImage = downloadFile(urlImagen);
					imagenView.setImageBitmap(loadedImage);
					OverlayItem overlay = getOverlayItem(titulo, idOferta, latitud, longitud, imagenView.getDrawable());
					if (!ofertaCaducada(fechaCaducidad)) {
						OfertasLocation.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay);
					} else {
						Log.i("log_tag", "Oferta caducada el dia: " + fechaCaducidad);
					}

				}
			}
			// Act6SeasTuristicLocationZaragoza.getInstance().anadePuntoAlMapaConItemizedOverlay("41.746726","-0.862427");
		} catch (Exception e) {
			Toast.makeText(OfertasLocation.getInstance().getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	} // FIN Llamada a Servidor Web PHP

	/**
	 * Verifica si la oferta está caducada
	 * 
	 * @param fechaCaducidad
	 *            fecha de caducidad de la oferta
	 * 
	 * @return el booleano con el resultado de la verificación de caducidad de
	 *         la oferta
	 */
	private boolean ofertaCaducada(String fechaCaducidad) {
		boolean result = false;

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date now = new Date();
			// Log.i("log_tag", "Fecha de hoy: " +
			// simpleDateFormat.format(now));
			Date dateCaducidad = simpleDateFormat.parse(fechaCaducidad);

			Calendar calendarNow = Calendar.getInstance();
			Calendar calendarFechaCaducidad = Calendar.getInstance();

			calendarNow.setTime(now);
			calendarFechaCaducidad.setTime(dateCaducidad);

			if (calendarNow.after(calendarFechaCaducidad)) {
				result = true;
			} else {
				result = false;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String periodoValidezOferta(String fechaCaducidad) {
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos al
															// día

		String result = "";

		StringBuffer periodoBuf = new StringBuffer();

		try {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date now = new Date();
			// Log.i("log_tag", "Fecha de hoy: " +
			// simpleDateFormat.format(now));
			Date dateCaducidad = simpleDateFormat.parse(fechaCaducidad);

			long diferencia = (dateCaducidad.getTime() - now.getTime());

			long dias = diferencia / MILLSECS_PER_DAY;
			long horas = ((diferencia / 1000) / 3600) % 24;
			long minutos = (diferencia / (1000 * 60)) % 60;

			result = periodoBuf.append(dias).append(" dias ").append(horas).append(" horas ").append(minutos)
					.append(" minutos ").toString();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Genera el código alfanumérico de la compra
	 * 
	 * @return el código alfanumérico de la compra
	 */
	public String generarCodigoCompra() {
		String result = "";

		Random r = new Random();

		result = Long.toString(Math.abs(r.nextLong()), 36);

		return result.toUpperCase();
	}

}
