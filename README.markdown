## vraptor-cep

Um plugin de vraptor que busca um endereço baseado em um cep

# instalação

Como esta dependência não se encontra no repositório maven, você tem que baixar o projeto para sua máquina e dar um mvn install.
No pom fica

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vraptor-cep</artifactId>
			<version>1.0.0</version>
			<scope>compile</scope>
		</dependency>
		
# configuração

Nenhuma configuração é necessária
	
# buscando um endereço por cep

		import br.com.caelum.vraptor.cep.AddressFinder;
		
		@Resource
		public class MeuController {
			
			private AddressFinder finder;
		
			public MeuController(AddressFinder finder) {
				this.finder = finder;
			}
			
			public void buscaEndereco(String cep) {
				String[] resultado = finder.findAddressByZipCode("60841220").asAddressArray();
				System.out.println("Tipo logradouro:" + resultado[0]);
				System.out.println("Logradouro:" + resultado[1]);
				System.out.println("Bairro:" + resultado[2]);
				System.out.println("Cidade:" + resultado[3]);
				System.out.println("UF:" + resultado[4]));
			}
			
			public void buscaEndereco(String cep) {
				Address resultado = finder.findAddressByZipCode("60841220").asAddressObject();
				System.out.println("Tipo logradouro:" + resultado.getStreetType());
				System.out.println("Logradouro:" + resultado.getStreet());
				System.out.println("Bairro:" + resultado.getDistrict());
				System.out.println("Cidade:" + resultado.getCity());
				System.out.println("UF:" + resultado.getState());
			}
			
		}
		