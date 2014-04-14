package br.com.caelum.restfulie;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class RestClientGenerator {

	@Produces
	public RestClient getInstance() {
		return Restfulie.custom();
	}

}
