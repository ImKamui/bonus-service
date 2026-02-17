package kholmychev.danil.bonusService.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bonus_cards")
@Data
public class BonusCards {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "card_num")
	private String cardNum;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
	private List<Operation> operations = new ArrayList<>();
}
