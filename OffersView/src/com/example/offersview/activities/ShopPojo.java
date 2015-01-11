package com.example.offersview.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7550923528851007194L;

	private int id;
	private String name;
	private double latitube;
	private double longitube;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitube() {
		return latitube;
	}

	public void setLatitube(double latitube) {
		this.latitube = latitube;
	}

	public double getLongitube() {
		return longitube;
	}

	public void setLongitube(double longitube) {
		this.longitube = longitube;
	}

}
