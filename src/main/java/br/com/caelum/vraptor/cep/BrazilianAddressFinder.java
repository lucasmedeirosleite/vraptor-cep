package br.com.caelum.vraptor.cep;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.RestfulieException;
import br.com.caelum.restfulie.mediatype.MediaTypes;
import br.com.caelum.restfulie.mediatype.XmlMediaType;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@RequestScoped
@Component
public class BrazilianAddressFinder implements AddressFinder {

	private static final String ZIP_CODE_SERVICE = "http://cep.republicavirtual.com.br/web_cep.php?cep=";
	private static final String RESPONSE_FORMAT = "xml";
	
	private RestClient restfulie;
	private DefaultAddress address;
	
	public BrazilianAddressFinder(RestClient restfulie){
		super();
		this.restfulie = restfulie;
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(DefaultAddress.class));
	}
	
	@Override
	public AddressFinder findAddressByZipCode(String zipCode) {
		try{
			Response response = restfulie.at(buildUrlWithZipCode(zipCode)).accept(MediaTypes.XML).get();
			address = response.getResource();
		}catch(RestfulieException exception){
			address = new DefaultAddress("", "", "", "", "", 0, "sem conexão");
		}
		
		return this;
	}
	
	private String buildUrlWithZipCode(String zipCode){
		return ZIP_CODE_SERVICE + zipCode + "&formato=" + RESPONSE_FORMAT;
	}

	@Override
	public String[] asAddressArray() {
		return address.asAddressArray();
	}

	@Override
	public Address asAddressObject() {
		return address;
	}

}
