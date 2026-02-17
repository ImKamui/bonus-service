package kholmychev.danil.bonusService.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kholmychev.danil.bonusService.models.BonusCards;
import kholmychev.danil.bonusService.models.Operation;
import kholmychev.danil.bonusService.models.enums.OperationType;
import kholmychev.danil.bonusService.repositories.BonusCardsRepository;
import kholmychev.danil.bonusService.repositories.OperationRepository;

@Service
public class OperationService {

	private final OperationRepository operationRepository;
	private final BonusCardsRepository bonusCardsRepository;
	
	@Autowired
	public OperationService(OperationRepository operationRepository, BonusCardsRepository bonusCardsRepository) {
		this.operationRepository = operationRepository;
		this.bonusCardsRepository = bonusCardsRepository;
	}
	
	public List<Operation> findAll()
	{
		return operationRepository.findAll();
	}
	
	public void save(BonusCards card, BigDecimal amount, OperationType type)
	{
		Operation operation = new Operation();
		operation.setCard(card);
		operation.setAmount(amount);
		operation.setOperationType(type);
		operation.setOperationTime(LocalDate.now());
		
		operationRepository.save(operation);
	}
	
	public List<Operation> getOperationsByCardNum(String cardNum)
	{
		BonusCards card = bonusCardsRepository.findByCardNum(cardNum).orElse(null); 
		return operationRepository.findByCardOrderByOperationTime(card);
	}
	
}
