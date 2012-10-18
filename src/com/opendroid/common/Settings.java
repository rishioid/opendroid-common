package com.opendroid.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class Settings {
	
	private static final String SENSITIVITY = "sensitivity";
	private static final String MAX_STEPS = "sensitivity";
	Context context;
	private float maxSteps;
	
	private SharedPreferences preferences;

	public Settings(Context context) {
		this.context = context;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public void clear()
	{
		preferences.edit().clear();
		preferences.edit().commit();
	}
	
	
	
	public void setSensitivity(String sensitivity) {
		Editor editor = preferences.edit();
		editor.putInt(SENSITIVITY, Integer.parseInt(sensitivity==null?"2":sensitivity));
		editor.commit();
	}

	public int getSensitivity() {
		return preferences.getInt(SENSITIVITY, 2);
	}
	
	
	
	public float getMaxSteps() {
		return preferences.getFloat(MAX_STEPS, 0.0F);
	}

	public void setMaxSteps(float maxSteps) {
		Editor editor = preferences.edit();
		editor.putFloat(MAX_STEPS, maxSteps);
		editor.commit();
	}

}
