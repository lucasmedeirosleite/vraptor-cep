package br.com.caelum.restfulie;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class RestClientGenerator implements ComponentFactory<RestClient>{

	@Override
	public RestClient getInstance() {
		return Restfulie.custom();
	}

}
