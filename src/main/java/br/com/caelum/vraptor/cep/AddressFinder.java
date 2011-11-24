package br.com.caelum.vraptor.cep;

/**
* A Vraptor Plugin to find an address based in a zip code.
* The default implementation looks for a brazilian zip code
* at http://cep.republicavirtual.com.br.
* @author Lucas Medeiros Leite
* 
*/
public interface AddressFinder {

	AddressFinder findAddressByZipCode(String zipcode);
	
	String[] asAddressArray();
	
	Address asAddressObject();
	
}
