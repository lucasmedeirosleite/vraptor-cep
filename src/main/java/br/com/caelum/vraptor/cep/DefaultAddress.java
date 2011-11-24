package br.com.caelum.vraptor.cep;

import com.google.common.base.Strings;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("webservicecep")
public class DefaultAddress implements Address{

	@XStreamAlias("tipo_logradouro")
	private String streetType;
	
	@XStreamAlias("logradouro")
	private String street;
	
	@XStreamAlias("bairro")
	private String district;
	
	@XStreamAlias("cidade")
	private String city;
	
	@XStreamAlias("uf")
	private String state;
	
	@SuppressWarnings("unused")
	@XStreamAlias("resultado")
	private int result;
	
	@SuppressWarnings("unused")
	@XStreamAlias("resultado_txt")
	private String resultText;
	
	public DefaultAddress(){
		super();
	}

	public DefaultAddress(String streetType, String street, String district, String city, String state, int result, String resultText) {
		super();
		this.streetType = streetType;
		this.street = street;
		this.district = district;
		this.city = city;
		this.state = state;
		this.result = result;
		this.resultText = resultText;
	}

	public String getStreetType() {
		return streetType;
	}

	public String getStreet() {
		return street;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String[] asAddressArray() {
		String[] addressResult = new String[5];
		addressResult[0] = this.getStreetType();
		addressResult[1] = this.getStreet();
		addressResult[2] = this.getDistrict();
		addressResult[3] = this.getCity();
		addressResult[4] = this.getState();
		return addressResult;
	}

	@Override
	public boolean found() {
		return !notFound();
	}

	@Override
	public boolean notFound() {
		return 	Strings.isNullOrEmpty(streetType) &&
				Strings.isNullOrEmpty(street) &&
			    Strings.isNullOrEmpty(district) &&
				Strings.isNullOrEmpty(city) &&
				Strings.isNullOrEmpty(state);
	}

}
