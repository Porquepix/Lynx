package com.lynx.core.json.model;

import com.lynx.core.json.JsonModel;

public class RangeModel extends JsonModel {

	private double min;
	private double max;

	public RangeModel() {
	}

	public RangeModel(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return this.min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return this.max;
	}

	public void setMax(double max) {
		this.max = max;
	}

}
