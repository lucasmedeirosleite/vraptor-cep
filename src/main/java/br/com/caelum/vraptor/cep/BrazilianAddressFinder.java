package br.com.caelum.vraptor.cep;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.RestfulieException;
import br.com.caelum.restfulie.mediatype.MediaTypes;
import br.com.caelum.restfulie.mediatype.XmlMediaType;

@RequestScoped
public class BrazilianAddressFinder implements AddressFinder {

	private static final String ZIP_CODE_SERVICE = "http://cep.republicavirtual.com.br/web_cep.php?cep=";

	enum RESPONSE_TYPE {
		XML
	}

	private RESPONSE_TYPE responType = RESPONSE_TYPE.XML;

	private final RestClient restfulie;
	private DefaultAddress address;

	@Deprecated
	public BrazilianAddressFinder() {
		this(null);
	}

	@Inject
	public BrazilianAddressFinder(RestClient restfulie){
		this.restfulie = restfulie;
	}

	@PostConstruct
	public void setUp(){
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
		return ZIP_CODE_SERVICE + zipCode + "&formato=" + responType.name().toLowerCase();
	}

	@Override
	public String[] asAddressArray() {
		return address.asAddressArray();
	}

	@Override
	public Address asAddressObject() {
		return address;
	}

	public void setResponType(RESPONSE_TYPE responType) {
		this.responType = responType;
	}

}
