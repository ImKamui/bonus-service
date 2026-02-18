package kholmychev.danil.bonusService.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Value;

@Data
public class UpdateBalanceCardDto {

	private String cardNum;
	private BigDecimal amount;
}
