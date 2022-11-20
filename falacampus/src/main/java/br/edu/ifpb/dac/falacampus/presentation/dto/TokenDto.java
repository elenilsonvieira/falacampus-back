package br.edu.ifpb.dac.falacampus.presentation.dto;



public class TokenDto {
	
	private String token;
	
	private UserDto user;

	
	public TokenDto(String token) {
		this.token =token;

	}
	
	public TokenDto (String token, UserDto systemUserDTO) {
		super();
		this.token = token;
		this.user = systemUserDTO;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	
	

	
	
	
	
	

}
