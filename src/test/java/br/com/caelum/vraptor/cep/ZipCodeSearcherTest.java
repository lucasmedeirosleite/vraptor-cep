package br.com.caelum.vraptor.cep;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.RestfulieException;
import br.com.caelum.restfulie.http.Request;
import br.com.caelum.restfulie.mediatype.MediaTypes;

public class ZipCodeSearcherTest {

	private AddressFinder searcher;
	private RestClient restfulie;
	private Request request;
	private Response response;

	@Before
	public void setUp(){
		restfulie = mock(RestClient.class);
		request = mock(Request.class);
		response = mock(Response.class);
		restfulie = mock(RestClient.class);

		when(restfulie.getMediaTypes()).thenReturn(new MediaTypes());
		searcher = new BrazilianAddressFinder(restfulie);

		when(request.accept(MediaTypes.XML)).thenReturn(request);
		when(request.get()).thenReturn(response);
	}

	@Test
	public void should_find_address_by_zip_code(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=60841220&formato=xml")).thenReturn(request);
		DefaultAddress defaultObject = new DefaultAddress("Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE", 1, "sucesso - cep completo");
		when(response.getResource()).thenReturn(defaultObject);

		String[]  resultAddress = {"Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE"};
		String[] address = searcher.findAddressByZipCode("60841220").asAddressArray();
		assertArrayEquals(resultAddress, address);
	}

	@Test
	public void should_find_address_by_zip_code_with_mask(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=60841-220&formato=xml")).thenReturn(request);
		DefaultAddress defaultObject = new DefaultAddress("Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE", 1, "sucesso - cep completo");
		when(response.getResource()).thenReturn(defaultObject);

		String[]  resultAddress = {"Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE"};
		String[] address = searcher.findAddressByZipCode("60841-220").asAddressArray();
		assertArrayEquals(resultAddress, address);
	}

	@Test
	public void should_not_find_address_by_zip_code_in_wrong_format(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=qdqwdqdqqdw&formato=xml")).thenReturn(request);
		DefaultAddress defaultObject = new DefaultAddress("", "", "", "", "", 0, "serviço indisponível/cep inválido");
		when(response.getResource()).thenReturn(defaultObject);

		String[]  resultAddress = {"", "", "", "", ""};
		String[] address = searcher.findAddressByZipCode("qdqwdqdqqdw").asAddressArray();
		assertArrayEquals(resultAddress, address);
	}

	@Test
	public void should_find_by_zip_code_and_convert_address(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=60841220&formato=xml")).thenReturn(request);
		DefaultAddress defaultObject = new DefaultAddress("Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE", 1, "sucesso - cep completo");
		when(response.getResource()).thenReturn(defaultObject);

		String[]  resultAddress = {"Rua", "Cesário Lange", "Messejana", "Fortaleza", "CE"};
		Address address = searcher.findAddressByZipCode("60841220").asAddressObject();
		assertTrue(address.found());
		assertEquals(resultAddress[0], address.getStreetType());
		assertEquals(resultAddress[1], address.getStreet());
		assertEquals(resultAddress[2], address.getDistrict());
		assertEquals(resultAddress[3], address.getCity());
		assertEquals(resultAddress[4], address.getState());
	}

	@Test
	public void should_not_find_by_zip_code_and_should_convert_address(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=whjdwegw&formato=xml")).thenReturn(request);
		DefaultAddress defaultObject = new DefaultAddress("", "", "", "", "", 0, "serviço indisponível/cep inválido");
		when(response.getResource()).thenReturn(defaultObject);

		Address address = searcher.findAddressByZipCode("whjdwegw").asAddressObject();
		assertTrue(address.notFound());
	}

	@Test
	public void should_return_empty_object_when_service_is_unavailable(){
		when(restfulie.at("http://cep.republicavirtual.com.br/web_cep.php?cep=60841220&formato=xml")).thenReturn(request);
		when(request.get()).thenThrow(new RestfulieException(""));
		Address address = searcher.findAddressByZipCode("60841220").asAddressObject();
		assertTrue(address.notFound());
	}

}
