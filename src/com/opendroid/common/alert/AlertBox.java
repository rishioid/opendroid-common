package com.opendroid.common.alert;

public interface AlertBox {
	
	public void show();
	public void dismiss();
	public void setTitle(String title);
	public String getTitle();

}
