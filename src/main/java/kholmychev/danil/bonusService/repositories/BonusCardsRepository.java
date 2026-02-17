package kholmychev.danil.bonusService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kholmychev.danil.bonusService.models.BonusCards;

@Repository
public interface BonusCardsRepository extends JpaRepository<BonusCards, Integer>{
	Optional<BonusCards> findByCardNum(String cardNum);
}
