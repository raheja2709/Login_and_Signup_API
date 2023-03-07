package com.xr.pet.mng.sys.PetMngSys.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="countrycode")
	private int countryCode;
	@Column(name="mobilenumber")
	private Long mobileNumber;
	@Column(name="registered")
	private boolean registered;
	@Column(name="otp")
	private Long otp;
}
