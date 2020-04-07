package com.example.springboot.atm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreditDto {
	
	private String id;
	private String bank;
	private String typeOwner;
	private String productName;
	private String numberAccount;
	private String nameOwner;
	private String dniOwner;
	private Double creditAmount;
	private Double balance;
	private Double consume;
		
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joinDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expirationDate;
	
	
	

}
