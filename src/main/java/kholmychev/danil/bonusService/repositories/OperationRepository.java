package kholmychev.danil.bonusService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kholmychev.danil.bonusService.models.BonusCards;
import kholmychev.danil.bonusService.models.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer>{
	
	List<Operation> findByCardOrderByOperationTime(BonusCards card);
}
