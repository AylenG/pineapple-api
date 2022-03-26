package com.pineapple.app.pineappleapi.security.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails {
	
	private String nombre;
	private String apellido;
	private String nombreUsuario;
	private String email;
	private String password;
	private Date fechaCreacion;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UsuarioPrincipal(String nombre, String apellido, String nombreUsuario, String email, String password, Date fechaCreacion,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.fechaCreacion = fechaCreacion;
		this.authorities = authorities;
	}

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities =
				usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
						.getRolNombre().name())).collect(Collectors.toList());
		
		return new UsuarioPrincipal(usuario.getNombre(), usuario.getApellido(), usuario.getNombreUsuario(), 
									usuario.getEmail(), usuario.getPassword(), usuario.getFechaCreacion(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return nombreUsuario;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getEmail() {
		return email;
	}
	
}
