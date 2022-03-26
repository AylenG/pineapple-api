package com.pineapple.app.pineappleapi.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pineapple.app.pineappleapi.security.entity.Rol;
import com.pineapple.app.pineappleapi.security.entity.Usuario;
import com.pineapple.app.pineappleapi.security.enums.RolNombre;
import com.pineapple.app.pineappleapi.security.service.RolService;
import com.pineapple.app.pineappleapi.security.service.UsuarioService;

@Component
public class CreateRoles implements CommandLineRunner {

	@Autowired
	RolService rolService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		/*
		Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
		Rol rolUser = new Rol(RolNombre.ROLE_USER);
		
		rolService.save(rolAdmin);
		rolService.save(rolUser);
		
		Usuario usuario = new Usuario("Jhon", "Smith", "JSmith", "JhonSmith@pineapple.com", passwordEncoder.encode("12345"));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		
		usuario.setRoles(roles);
		
		usuarioService.save(usuario);
		*/
	}

}
