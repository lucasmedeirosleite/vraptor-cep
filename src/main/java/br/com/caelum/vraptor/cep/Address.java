package br.com.caelum.vraptor.cep;

public interface Address {

	String getStreetType();

	String getStreet();

	String getDistrict();

	String getCity();

	String getState();

	boolean found();
	
	boolean notFound();

}
