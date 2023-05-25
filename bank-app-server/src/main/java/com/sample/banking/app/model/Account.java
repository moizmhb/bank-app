package com.sample.banking.app.model;

import com.sample.banking.app.config.ErrorMessageConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="account_id")
	private Integer id;

	/*@Column(columnDefinition = "INT UNSIGNED")*/
	private Long accountNumber;

	private String accountStatus;
	
	private String accountType;

	@NotBlank(message = ErrorMessageConstant.INVALID_AMOUNT)
	@Size(min = 3, max = 10000000, message = ErrorMessageConstant.INVALID_AMOUNT)
	private Long accountBalance;
    
    @Temporal(TemporalType.TIME)
	private Date createDateTime=new Date();
	
    @Temporal(TemporalType.TIME)
	private Date updateDateTime=new Date();

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", accountNumber=" + accountNumber +
				", accountStatus='" + accountStatus + '\'' +
				", accountType='" + accountType + '\'' +
				", accountBalance=" + accountBalance +
				", createDateTime=" + createDateTime +
				", updateDateTime=" + updateDateTime +
				'}';
	}
}
