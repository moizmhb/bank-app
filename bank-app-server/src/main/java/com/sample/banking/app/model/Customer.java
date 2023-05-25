package com.sample.banking.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="customer_id")
    private Integer id;
    
    private String firstName;
    
    private String lastName;

/*    @Column(columnDefinition = "INT UNSIGNED")*/
    private Long customerNumber;
    
    private String status;

    private String address;

    private String email;

    @Temporal(TemporalType.TIME)
	private Date createDateTime=new Date();
	
    @Temporal(TemporalType.TIME)
	private Date updateDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
	
}
