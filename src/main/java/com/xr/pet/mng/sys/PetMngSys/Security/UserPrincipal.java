package com.xr.pet.mng.sys.PetMngSys.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * implementation class of UserPrincipal for JWT.
 * 
 * @author Mamta Chovatia
 *
 */
@Data
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * @param id id of user.
	 */
	public UserPrincipal(long id) {
		this.id = id;
	}

	/**
	 * This method is used to set user principal by id.
	 * 
	 * @param id id of user.
	 * @return {@link UserPrincipal}
	 */
	public static UserPrincipal createUser(long id) {
		return new UserPrincipal(id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
