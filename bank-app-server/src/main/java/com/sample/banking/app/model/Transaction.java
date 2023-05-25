package com.sample.banking.app.model;

import com.sample.banking.app.config.ErrorMessageConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transaction_id")
	private Integer id;
	
	private Long accountNumber;

	private Date transactionDateTime = new Date();
	
	private String transactionType;

	@NotBlank(message = ErrorMessageConstant.INVALID_AMOUNT)
	@Size(min = 3, max = 10000000, message = ErrorMessageConstant.INVALID_AMOUNT)
	private Long transactionAmount;

	private Long balance;
}
