package com.xr.pet.mng.sys.PetMngSys.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMaster extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "countrycode")
	private int countryCode;
	@Column(name = "mobilenumber")
	private Long mobileNumber;
	@Column(name = "otp")
	private Long otp;
	@Column(name = "otpSentTime")
	private Long otpSentTime;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "verified")
	private boolean verified;
	@Transient
	private String token;
}
