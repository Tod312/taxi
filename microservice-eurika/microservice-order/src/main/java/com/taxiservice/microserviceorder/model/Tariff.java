package com.taxiservice.microserviceorder.model;

import java.math.BigDecimal;

public enum Tariff {
	
	KOMFORT(25, 9, 0, 100, 129),
	CHILD_RATE(20, 8, 30, 80, 110);
	
	private final int COST_PER_KM;
	private final int COST_PER_MINUTE;
	private final int COST_PER_BABY_CHAIR;
	private final int COST_PER_ANIMAL;
	private final int MINIMUM_COST;
	
	private Tariff(int cOST_PER_KM, int cOST_PER_MINUTE,
			int cOST_PER_BABY_CHAIR, int cOST_PER_ANIMAL, int mINIMUM_COST) {
		COST_PER_KM = cOST_PER_KM;
		COST_PER_MINUTE = cOST_PER_MINUTE;
		COST_PER_BABY_CHAIR = cOST_PER_BABY_CHAIR;
		COST_PER_ANIMAL = cOST_PER_ANIMAL;
		MINIMUM_COST = mINIMUM_COST;
	}
	
	public BigDecimal getCost(int number_animals, int number_baby_chairs, int number_km, int number_minutes) {
		int sum_km = COST_PER_KM * number_km;
		int sum_minutes = COST_PER_MINUTE * number_minutes;
		int sum_baby_chairs = COST_PER_BABY_CHAIR * number_baby_chairs;
		int sum_animals = COST_PER_ANIMAL * number_animals;
		int cost = sum_km + sum_minutes + sum_baby_chairs + sum_animals + MINIMUM_COST;
		return new BigDecimal(cost);
	}
}
