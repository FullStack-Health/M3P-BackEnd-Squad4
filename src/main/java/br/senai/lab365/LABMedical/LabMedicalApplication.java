package br.senai.lab365.LABMedical;

import br.senai.lab365.LABMedical.services.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LabMedicalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LabMedicalApplication.class, args);


		UsuarioService usuarioService = context.getBean(UsuarioService.class);
		usuarioService.criaUsuarioAdminSeNaoExiste();
	}

}
