package com.example.springboot.atm.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.classgraph.json.Id;
import lombok.Data;

@Data
@Document(collection = "atm")
public class Atm {
	
	@Id
	private String id;
	private String bankAtm;
	private String operationType;
	private String nameAccountO;
	private String numAccountO;
	private String nameAccountD;
	private String numAccountD;	
	private Double amount;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date operationDate;
	
	

}
