package com.xr.pet.mng.sys.PetMngSys.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@Column(name = "countrycode")
	private int countryCode;

	@JsonIgnore
	@Column(name = "mobilenumber")
	private Long mobileNumber;

	@JsonIgnore
	@Column(name = "otp")
	private Long otp;

	@JsonIgnore
	@Column(name = "otpSentTime")
	private Long otpSentTime;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@JsonIgnore
	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "verified")
	private boolean verified;

	@JsonIgnore
	@OneToMany(targetEntity = Like.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<Like> likes = new ArrayList<>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<Comment> postComments;

	@Transient
	@JsonIgnore
	private String token;
}
