package com.seas.alfredo.ofertas.mapas;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.seas.alfredo.ofertas.R;
import com.seas.alfredo.ofertas.principal.OfertaDetail;
import com.seas.alfredo.ofertas.principal.OfertasLocation;

public class MiItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;

	public MiItemizedOverlay(Context context) {
		super(boundCenterBottom(OfertasLocation.getInstance().getResources()
				.getDrawable(R.drawable.seas)));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	// Dado una imagen la ajusta para que el punto 0,0 de éste esté en el centro
	// de la parte inferior
	public Drawable boundCenterBottomAux(Drawable marker) {
		return boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);

		Intent ofertaDetailIntent = new Intent(OfertasLocation.getInstance(), OfertaDetail.class);
		ofertaDetailIntent.putExtra("titulo", item.getTitle());
		ofertaDetailIntent.putExtra("idOferta", item.getSnippet());

		//Iniciamos la actividad que muestra el detalle de la oferta
		OfertasLocation.getInstance().startActivity(ofertaDetailIntent);
//		 mOverlays.remove(index);
		return true;
	}

}
