package com.example.springboot.atm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BankingDto {

	private String id;
	private String bank;
	private String productName;
	private String clientType;
	private String numAccount;
	private String nameOwner;
	private String numDoc;
	private Double amount;
	private Double amountAvailable;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date jointAt;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateAt;
}
