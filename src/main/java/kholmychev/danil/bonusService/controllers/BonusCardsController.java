package kholmychev.danil.bonusService.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kholmychev.danil.bonusService.dto.UpdateBalanceCardDto;
import kholmychev.danil.bonusService.models.BonusCards;
import kholmychev.danil.bonusService.models.Operation;
import kholmychev.danil.bonusService.services.BonusCardsService;
import kholmychev.danil.bonusService.services.OperationService;

@RestController
@RequestMapping("/api")
public class BonusCardsController {

	private final BonusCardsService bonusCardsService;
	private final OperationService operationService;
	
	@Autowired
	public BonusCardsController(BonusCardsService bonusCardsService, OperationService operationService) {
		this.bonusCardsService = bonusCardsService;
		this.operationService = operationService;
	}
	
	
	// Список всех бонусных карт
	@GetMapping("/cards")
	public @ResponseBody List<BonusCards> getAllCards()
	{
		return bonusCardsService.findAll();
	}
	
	
	// Просмотр баланса на карте
	@GetMapping("/cards/get-balance")
	public @ResponseBody String getBalance(@RequestParam String cardNum)
	{ 
		return "Баланс карты " + cardNum + ": " +  bonusCardsService.findOneByCardnum(cardNum).getBalance().toString();
	}
	
	// Списать бонусы
	@PatchMapping("/cards/down-balance")
	public ResponseEntity<BonusCards> downBalance(@RequestBody UpdateBalanceCardDto card)
	{
		String cardNum = card.getCardNum();
		BigDecimal amount = card.getAmount();
		
		BonusCards updateCard = bonusCardsService.writeOffBalance(cardNum, amount);
		if (updateCard != null)
		{
			return ResponseEntity.ok(updateCard);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	
	// Начислить бонусы
	@PatchMapping("/cards/up-balance")
	public ResponseEntity<BonusCards> upBalance(@RequestBody UpdateBalanceCardDto card)
	{
		String cardNum = card.getCardNum();
		BigDecimal amount = card.getAmount();
		
		BonusCards updateCard = bonusCardsService.increaseBalance(cardNum, amount);
		if (updateCard != null)
		{
			return ResponseEntity.ok(updateCard);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	
	// Возврат бонусов
	@PatchMapping("/cards/refund-bonus")
	public ResponseEntity<BonusCards> refundBonus(@RequestBody UpdateBalanceCardDto card)
	{
		String cardNum = card.getCardNum();
		BigDecimal amount = card.getAmount();
		
		BonusCards updateCard = bonusCardsService.refundBonus(cardNum, amount);
		if (updateCard != null)
		{
			return ResponseEntity.ok(updateCard);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	
	// История операций по номеру карты
	@GetMapping("/cards/{cardNum}/history")
	public ResponseEntity<List<Operation>> getOperationsByCardNum(@PathVariable String cardNum)
	{
		List<Operation> operations = operationService.getOperationsByCardNum(cardNum);
		if (operations.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(operations);
	}
	
}
