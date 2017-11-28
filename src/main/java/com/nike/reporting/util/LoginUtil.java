/**
 * @author Sachin_Ainapure
 */
package com.nike.reporting.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nike.reporting.exceptions.NikeException;

public class LoginUtil {
	private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

	private static final String ALGORITHM = "AES";

	// OUR 128 bit key
	private static byte[] KEY = new byte[] { 'N', 'R', 'T', 's', 'K', 'E', 'Y', 'J', 'A', 'N', '2', '5', '1', '9', '6', '4' };

	public static String encrypt(String plainText) throws NikeException {
		try {
			Key aesKey = new SecretKeySpec(KEY, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
			return new Base64().encodeAsString(encrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			throw new NikeException(e, e.getMessage());
		}
	}

	@SuppressWarnings("static-access")
	public static String decrypt(String encryptedText) throws NikeException {
		try {
			byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);
			Key aesKey = new SecretKeySpec(KEY, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);

			byte[] decrypted = cipher.doFinal(encryptedTextBytes);
			return new String(decrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			throw new NikeException(e, e.getMessage());
		}
	}

	public static boolean hasRole(String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

	public static boolean setValidSession() {

		try {
			// Create Session
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			String name = auth.getName(); // get logged in username

			if (name == null) {
				return false;
			} else {
				if (session.getAttribute("user_id") == null) {
					String user_id = userDetails.getUsername();
					session.setAttribute("AuthenticatedUser", name);
					// TODO: Fetch from properties file
					session.setAttribute("environment", "Production");
					session.setAttribute("user_id", user_id);
					session.setAttribute("isAdmin", LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN));
					session.setAttribute("userObj", userDetails);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		return userDetails.getUsername();
	}

}
