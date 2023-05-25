package com.sample.banking.app.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Getter
@Setter
public class BankDetail {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bank_id")
	private long id;
	
	private String name;
	
	private Integer branchCode;
	
	private String address;

}
