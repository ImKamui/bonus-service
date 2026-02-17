package kholmychev.danil.bonusService.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kholmychev.danil.bonusService.dto.UpdateBalanceCardDto;
import kholmychev.danil.bonusService.models.BonusCards;
import kholmychev.danil.bonusService.models.enums.OperationType;
import kholmychev.danil.bonusService.repositories.BonusCardsRepository;

@Service
@Transactional
public class BonusCardsService {

	private final BonusCardsRepository bonusCardsRepository;
	private final OperationService operationService;
	
	@Autowired
	public BonusCardsService(BonusCardsRepository bonusCardsRepository, OperationService operationService) {
		this.bonusCardsRepository = bonusCardsRepository;
		this.operationService = operationService;
	}
	
	public List<BonusCards> findAll()
	{
		return bonusCardsRepository.findAll();
	}
	
	public BonusCards findOne(int id)
	{
		Optional<BonusCards> foundOne = bonusCardsRepository.findById(id);
		return foundOne.orElse(null);
	}
	
	public BonusCards findOneByCardnum(String cardNum)
	{
		Optional<BonusCards> foundOne = bonusCardsRepository.findByCardNum(cardNum);
		return foundOne.orElse(null);
	}
	
	@Transactional
	public void save(BonusCards bonusCard)
	{
		bonusCardsRepository.save(bonusCard);
	}
	
	@Transactional
	public void update(BonusCards updatedCard, int id)
	{
		BonusCards card = bonusCardsRepository.findById(id).orElse(null);
		card.setCardNum(updatedCard.getCardNum());
		card.setBalance(updatedCard.getBalance());
		card.setUser(updatedCard.getUser());
		bonusCardsRepository.save(card);
	}
	
	@Transactional
	public void delete(int id)
	{
		bonusCardsRepository.deleteById(id);
	}
	
	@Transactional
	public void updateBalance(BonusCards updatedCard, String cardNum)
	{
		BonusCards card = bonusCardsRepository.findByCardNum(cardNum).orElse(null);
		card.setBalance(updatedCard.getBalance());
		bonusCardsRepository.save(card);
	}
	
	@Transactional
	public BonusCards writeOffBalance(String cardNum, BigDecimal amount)
	{
		BonusCards card = bonusCardsRepository.findByCardNum(cardNum).orElse(null);
		if (card == null)
		{
			return null;
		}
		BigDecimal curBalance = card.getBalance();
		if (curBalance.compareTo(amount) < 0)
		{
			return null;
		}
		card.setBalance(curBalance.subtract(amount));
		operationService.save(card, amount, OperationType.WRITE_OFF);
		return bonusCardsRepository.save(card);
	}
	
	@Transactional
	public BonusCards increaseBalance(String cardNum, BigDecimal amount)
	{
		BonusCards card = bonusCardsRepository.findByCardNum(cardNum).orElse(null);
		if (card == null)
		{
			return null;
		}
		BigDecimal curBalance = card.getBalance();
		BigDecimal resultBalance = curBalance.add(amount);
		card.setBalance(resultBalance);
		operationService.save(card, amount, OperationType.INCREASE);
		return bonusCardsRepository.save(card);
	}
	
	@Transactional
	public BonusCards refundBonus(String cardNum, BigDecimal amount)
	{
		BonusCards card = bonusCardsRepository.findByCardNum(cardNum).orElse(null);
		if (card == null)
		{
			return null;
		}
		BigDecimal curBalance = card.getBalance();
		BigDecimal resultBalance = curBalance.add(amount);
		card.setBalance(resultBalance);
		operationService.save(card, amount, OperationType.REFUND);
		return bonusCardsRepository.save(card);
	}
}
