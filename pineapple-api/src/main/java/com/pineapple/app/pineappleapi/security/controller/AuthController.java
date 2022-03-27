package com.pineapple.app.pineappleapi.security.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pineapple.app.pineappleapi.dto.Mensaje;
import com.pineapple.app.pineappleapi.security.dto.JwtDto;
import com.pineapple.app.pineappleapi.security.dto.LoginUsuario;
import com.pineapple.app.pineappleapi.security.dto.NuevoUsuario;
import com.pineapple.app.pineappleapi.security.entity.Rol;
import com.pineapple.app.pineappleapi.security.entity.Usuario;
import com.pineapple.app.pineappleapi.security.enums.RolNombre;
import com.pineapple.app.pineappleapi.security.jwt.JwtProvider;
import com.pineapple.app.pineappleapi.security.service.RolService;
import com.pineapple.app.pineappleapi.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/nuevo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario,
									BindingResult bindingResult) {
		if(bindingResult.hasErrors()) 
			return new ResponseEntity(new Mensaje("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
		
		if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
			return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		
		if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
			return new ResponseEntity(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
		
		Usuario usuario = 
				new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(),
						nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
		
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		
		if(nuevoUsuario.getRoles().contains("admin"))
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		
		usuario.setRoles(roles);
		
		usuarioService.save(usuario);
		
		return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) 
			return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtDto> refreshToken(@RequestBody JwtDto jwtDto) throws ParseException {
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDto jwt = new JwtDto(token);
		
		return new ResponseEntity<JwtDto>(jwt, HttpStatus.OK);
	}
	
}
