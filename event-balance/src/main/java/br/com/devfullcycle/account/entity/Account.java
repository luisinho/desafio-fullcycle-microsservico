package br.com.devfullcycle.account.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	

	@Column(name = "account_id_from", length = 255, nullable = false, unique = true)
	private String accountIdFrom; 

	@Column(name = "account_id_to", length = 255, nullable = false)
    private String accountIdTo;

	@Column(name = "balance_account_from", precision = 15, scale = 2, nullable = false)
    private BigDecimal balanceAccountFrom;

	@Column(name = "balance_account_to", precision = 15, scale = 2, nullable = false)
    private BigDecimal balanceAccountTo;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountIdFrom() {
		return accountIdFrom;
	}

	public void setAccountIdFrom(String accountIdFrom) {
		this.accountIdFrom = accountIdFrom;
	}

	public String getAccountIdTo() {
		return accountIdTo;
	}

	public void setAccountIdTo(String accountIdTo) {
		this.accountIdTo = accountIdTo;
	}

	public BigDecimal getBalanceAccountFrom() {
		return balanceAccountFrom;
	}

	public void setBalanceAccountFrom(BigDecimal balanceAccountFrom) {
		this.balanceAccountFrom = balanceAccountFrom;
	}

	public BigDecimal getBalanceAccountTo() {
		return balanceAccountTo;
	}

	public void setBalanceAccountTo(BigDecimal balanceAccountTo) {
		this.balanceAccountTo = balanceAccountTo;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}