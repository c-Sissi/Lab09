package it.polito.tdp.borders.model;

public class Border {

	private Country c1 ;
	private Country c2 ;
	private int year ;

	
	public Border(Country c1, Country c2, int year) {
		this.c1 = c1;
		this.c2 = c2;
		this.year = year;
	}

	public Country getC1() {
		return c1;
	}

	public Country getC2() {
		return c2;
	}

	public int getYear() {
		return year;
	}
	
}
