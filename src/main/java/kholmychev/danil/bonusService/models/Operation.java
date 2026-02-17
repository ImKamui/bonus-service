package kholmychev.danil.bonusService.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kholmychev.danil.bonusService.models.enums.OperationType;
import lombok.Data;

@Entity
@Table(name = "operations")
@Data
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private OperationType operationType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_num", referencedColumnName = "card_num")
	private BonusCards card;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "operation_time")
	private LocalDate operationTime;
}
