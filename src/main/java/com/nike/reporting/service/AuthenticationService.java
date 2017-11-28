package com.nike.reporting.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.UserDAO;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Nrt_user;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.LoginUtil;

public class AuthenticationService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	private UserDAO userdao;

	public void setUserdao(UserDAO userdao) {
		this.userdao = userdao;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		Nrt_user u = null;
		try {
			u = userdao.getUserByName(user);
			if (u == null) {
				throw new UsernameNotFoundException(ErrorMessages.NO_DB_USER_EXCEPTION);
			}

			return buildUserFromUserEntity(u);
		} catch (Exception e) {
			throw new UsernameNotFoundException(ErrorMessages.NO_DB_USER_EXCEPTION);
		}

	}

	@SuppressWarnings("deprecation")
	private User buildUserFromUserEntity(Nrt_user userEntity) throws NikeException {
		// convert model user to spring security user
		String username = userEntity.getNikeid();
		// String username = userEntity.getFname();

		String password = LoginUtil.decrypt(userEntity.getPassword());
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		GrantedAuthorityImpl[] authorities = new GrantedAuthorityImpl[1];
		authorities[0] = new GrantedAuthorityImpl(userEntity.getRole());
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(authorities[0]);
		User springUser = null;

		springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, auths);

		logger.info("User :" + springUser.getUsername() + springUser.getPassword() + springUser.getAuthorities());

		return springUser;
	}

}
