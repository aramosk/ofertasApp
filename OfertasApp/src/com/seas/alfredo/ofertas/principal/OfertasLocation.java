package com.seas.alfredo.ofertas.principal;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.seas.alfredo.ofertas.R;
import com.seas.alfredo.ofertas.consultas.BusquedaForm;
import com.seas.alfredo.ofertas.mapas.MiItemizedOverlay;
import com.seas.alfredo.ofertas.mapas.MiOverlay;
import com.seas.alfredo.ofertas.mapas.MyLocationListener;
import com.seas.alfredo.ofertas.servicios.ServiciosOfertasLocation;

/**
 * 
 * @author Alfredo Ramos Kustron
 * 
 * Clase que implementa la actividad principal de la aplicacion
 * y muestra el mapa con las ofertas no caducadas
 *
 */
public class OfertasLocation extends MapActivity {
	private LocationManager lm = null;
    private MyLocationListener locationListener = null;
    private String best = null;

    private MapView mapView = null;
    private MapController mc = null;
    private List<Overlay> mapOverlays = null;
    private MiItemizedOverlay itemizedoverlay = null;
    private ServiciosOfertasLocation serviciosOfertasLocation = null;
    
    private static OfertasLocation ofertasLocation= null;
    public static OfertasLocation getInstance(){
    	if(ofertasLocation == null){
    		ofertasLocation = new  OfertasLocation();
    	}
    	return ofertasLocation;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    	StrictMode.setThreadPolicy(policy);

    	super.onCreate(savedInstanceState);
        // Asociamos la ventana mapa.xml al activity 
    	setContentView(R.layout.mapa);
    	ofertasLocation = this;
    	serviciosOfertasLocation = new ServiciosOfertasLocation(OfertasLocation.getInstance());  
		// Recuperamos el componente MapView que nos permite mostrar el 
    	// mapa de google en la pantalla
    	// Sólo se puede utilizar MapView en MapActivity
    	// Librería: com.google.android.maps
    	// Obtener ApiKey
        mapView = (MapView) findViewById(R.id.mapView);
        //
	    mc = mapView.getController();
	    mapView.setBuiltInZoomControls(true);
	    //mapView.setSatellite(true);
	    //mapView.setTraffic(true);
	    //mapView.setClickable(true);
	    //mapView.setStreetView(true);
	    itemizedoverlay = new MiItemizedOverlay(this);

	    // Añade las ofertas no caducadas en el mapa
	    serviciosOfertasLocation.getOfertas();
	    /////////////////////////////////////////////////////////////////////////////////
	    ////////////////////GEOLOCALIZARME
	    ////////////////////////////////////////////////////////////////////////////////
	    registraServiciosDeLocalizaciónGPS();
	    mc.setZoom(12);
	}
    
    /**
     * Despliega el menu principal
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    
    /**
     * Maneja el evento de seleccion de opciones del menu principal.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.salir:
            	finish();
                return true;
            case R.id.ofertas:
            	Intent ofertasIntent = new Intent(OfertasLocation.getInstance(), BusquedaForm.class);
            	OfertasLocation.getInstance().startActivity(ofertasIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   
    private void anadePuntoAlMapaGeoPoint(String latitud, String longitud){
	   String coordinates[] = {latitud,longitud};
       double lat = Double.parseDouble(coordinates[0]);
       double lng = Double.parseDouble(coordinates[1]);
       GeoPoint p;
       p = new GeoPoint(
               (int) (lat * 1E6), // Trabaja en microgrados para las coordenadas y por eso hay que multiplicar por un millón
               (int) (lng * 1E6));
       mc.animateTo(p);
       mc.setZoom(17);
       mapView.invalidate();
   }
    public synchronized void anadePuntoAlMapaConOverlay(String latitud, String longitud){
 	   String coordinates[] = {latitud,longitud};
 	   double lat = Double.parseDouble(coordinates[0]);
       double lng = Double.parseDouble(coordinates[1]);
       GeoPoint p;
        p = new GeoPoint(
                (int) (lat * 1E6), // Trabaja en microgrados para las coordenadas y por eso hay que multiplicar por un millón
                (int) (lng * 1E6));
        mapOverlays = mapView.getOverlays();
        mapOverlays.clear();
        MiOverlay marker = new MiOverlay(p);
    	mapOverlays.add(itemizedoverlay);
    	mapOverlays.add(marker);
    	mc.animateTo(p);
        mc.setZoom(14);
    	mapView.invalidate();
    }
    public synchronized void anadePuntoAlMapaConItemizedOverlay(String latitud, String longitud){
    	 	String coordinates[] = {latitud,longitud};
  	   		double lat = Double.parseDouble(coordinates[0]);
  	   		double lng = Double.parseDouble(coordinates[1]);
    	mapOverlays = mapView.getOverlays();
 	    //mapOverlays.clear();
 	    GeoPoint point = new GeoPoint((int) (lat * 1E6),(int) (lng * 1E6));
 	    OverlayItem overlayitem = new OverlayItem(point, "Hola, Seas!", "I'm Seas!!");
 	    overlayitem.setMarker(itemizedoverlay.boundCenterBottomAux(this.getResources().getDrawable(R.drawable.seas)));
 	    itemizedoverlay.addOverlay(overlayitem);
 	    mapOverlays.add(itemizedoverlay);
	    mc.setZoom(10);
	    mc.animateTo(point);
	    mapView.invalidate();
     }
    
    public synchronized void anadePuntoAlMapaConItemizedOverlayEImagen(OverlayItem overlayItem) throws Exception{
    		mapOverlays = mapView.getOverlays();
    		itemizedoverlay.addOverlay(overlayItem);
	   		mapOverlays.add(itemizedoverlay);
	   		mc.animateTo(overlayItem.getPoint());
		    mapView.invalidate();
     }
    
    public synchronized void anadePuntoAlMapaConItemizedOverlayEImagenLocation(OverlayItem overlayItem) throws Exception{
			mapOverlays = mapView.getOverlays();
			itemizedoverlay.addOverlay(overlayItem);
	   		mapOverlays.add(itemizedoverlay);
	   		mc.animateTo(overlayItem.getPoint());
		    mapView.invalidate();	
 }
    private void registraServiciosDeLocalizaciónGPS(){
   	 // Localización Gps
	    // LocationManager
	    // LocationListener
	    // LocationProvider
	    // Proveedor que necesitamos de Localización y nos permite acceder rápidamente 
	    lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
	    Criteria criteria = new Criteria();
	    best = lm.getBestProvider(criteria, true);
	    //a la última ubicación conocida.
	    Location location = lm.getLastKnownLocation(best);
	    if(location!=null){
	    	//anadePuntoAlMapaConItemizedOverlay(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
	    }
	    locationListener = new MyLocationListener();
	    // Cuando registres cambios de localización en:
	    // Registrar oyentes de ubicación
	    lm.requestLocationUpdates(
	    		best,  //receptor GPS, más preciso y más habitual y disponible en el emulador //nombre del proveedor
	    		//NETWORK_PROVIDER, GPS_PROVIDER
	            //
	            4000,//60000, //ms milisegundos retraso de actualización (para no recibir actualizaciones con mucha frecuencia)
	            0,//10, // metros Distancia mínima con el punto anterior// Nunca un valor inferior a 60000ms agota batería (cambios ignorados)
	            locationListener); //Escuchador de eventos
   }
    @Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    /***
 	* CONTROL DE ESTADOS DEL ACTIVITY
 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		lm.removeUpdates(locationListener); //Detener las actualizaciones
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

    /***
	* GETTER/SETTER
	* @return
	*/
	public MapView getMapView() {
		return mapView;
	}
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
	public MapController getMc() {
		return mc;
	}
	public void setMc(MapController mc) {
		this.mc = mc;
	}
	public MiItemizedOverlay getItemizedoverlay() {
		return itemizedoverlay;
	}
	public List<Overlay> getMapOverlays() {
		return mapOverlays;
	}
}	