package com.xr.pet.mng.sys.PetMngSys.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "user_master")
public class UserMaster extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "country_code")
    @JsonIgnore
    private int countryCode;

    @Column(name = "mobile_number")
    @JsonIgnore
    private Long mobileNumber;

    @Column(name = "otp")
    @JsonIgnore
    private Long otp;

    @Column(name = "otp_sent_time")
    @JsonIgnore
    private Long otpSentTime;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @JsonIgnore
    private String email;

    @Column(name = "verified")
    @JsonIgnore
    private boolean verified;

    @Transient
    private String token;
}
