package br.senai.lab365.LABMedical;

import br.senai.lab365.LABMedical.services.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VitalCareApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VitalCareApplication.class, args);


		UsuarioService usuarioService = context.getBean(UsuarioService.class);
		usuarioService.criaUsuarioAdminSeNaoExiste();
	}

}
