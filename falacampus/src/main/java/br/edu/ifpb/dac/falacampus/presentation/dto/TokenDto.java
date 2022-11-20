package br.edu.ifpb.dac.falacampus.presentation.dto;

import java.util.Objects;

public class TokenDto {
	
	private String token;
	private UserDto userDto;

	
	public TokenDto(String token, UserDto userDto) {
		this.token =token;
		this.userDto = userDto;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
