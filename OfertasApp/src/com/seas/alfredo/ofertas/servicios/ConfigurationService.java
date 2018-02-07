package com.seas.alfredo.ofertas.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * 
 * @author ARAMOSK
 * 
 * Class for Configuration Service implementation.
 *
 */
public class ConfigurationService {

	/**
	 * Properties field
	 */
	private Properties properties;

	/**
	 * Context field
	 */
	private Context context;

	public ConfigurationService(Context context) {
		this.context = context;
		this.properties = new Properties();

		try {
			/**
			 * getAssets() Return an AssetManager instance for your
			 * application's package. AssetManager Provides access to an
			 * application's raw asset files;
			 */
			AssetManager assetManager = this.context.getAssets();
			/**
			 * Open an asset using ACCESS_STREAMING mode. This
			 */
			InputStream inputStream = assetManager.open("configuration.properties");
			/**
			 * Loads properties from the specified InputStream,
			 */
			properties.load(inputStream);

		} catch (IOException e) {
			Log.e("AssetsPropertyReader", e.toString());
		}
	}

	/**
	 * Returns the value of a property for a given key.
	 * 
	 * @param key the property key
	 * @return
	 */
	public String getProperty(String key) {

		if (properties == null) {
			return "";
		} else {
			return properties.getProperty(key);
		}
	}

}
