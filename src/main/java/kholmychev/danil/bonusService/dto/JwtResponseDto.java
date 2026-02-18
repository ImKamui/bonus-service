package kholmychev.danil.bonusService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JwtResponseDto {
	
	private String token;
	
	public JwtResponseDto(String token)
	{
		this.token = token;
	}
}
