package br.com.caelum.vraptor.cep;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class AddressTest {

	@Test
	public void should_address_be_converted_in_string_array(){
		String[] addressArray = {"Rua", "dos Bobos", "Ali", "Fortaleza", "CE"};
		assertArrayEquals(addressArray, new DefaultAddress("Rua", "dos Bobos", "Ali", "Fortaleza", "CE", 1, "sucesso - cep completo").asAddressArray());
	}
	
	@Test
	public void should_address_be_converted_in_empty_string_array_when_zip_code_invalid_or_not_found(){
		String[] addressArray = {"", "", "", "", ""};
		assertArrayEquals(addressArray, new DefaultAddress("", "", "", "", "", 0, "serviço indisponível/cep inválido").asAddressArray());
	}
	
	@Test
	public void should_address_be_converted_to_address_object(){
		String[] addressArray = {"Rua", "dos Bobos", "Ali", "Fortaleza", "CE"};
		DefaultAddress address = new DefaultAddress("Rua", "dos Bobos", "Ali", "Fortaleza", "CE", 1, "sucesso - cep completo");
		assertThat(address, instanceOf(Address.class));
		Address converted = (Address) address;
		assertEquals(addressArray[0], converted.getStreetType());
		assertEquals(addressArray[1], converted.getStreet());
		assertEquals(addressArray[2], converted.getDistrict());
		assertEquals(addressArray[3], converted.getCity());
		assertEquals(addressArray[4], converted.getState());
	}
	
	@Test
	public void should_address_be_found_with_success(){
		Address address = new DefaultAddress("Rua", "dos Bobos", "Ali", "Fortaleza", "CE", 1, "sucesso - cep completo");
		assertTrue(address.found());
		assertFalse(address.notFound());
	}
	
	@Test
	public void should_address_not_be_found(){
		Address address = new DefaultAddress("", "", "", "", "", 0, "serviço indisponível/cep inválido");
		assertTrue(address.notFound());
		assertFalse(address.found());
	}
	
}
