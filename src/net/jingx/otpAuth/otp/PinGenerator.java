package net.jingx.otpAuth.otp;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.jingx.otpAuth.otp.Base32String.DecodingException;

public class PinGenerator {

	public static String computePin(String secret, Long counter)
			throws GeneralSecurityException, DecodingException {
		try {
			final byte[] keyBytes = Base32String.decode(secret);
			Mac mac = Mac.getInstance("HMACSHA1");
			mac.init(new SecretKeySpec(keyBytes, ""));
			PasscodeGenerator pcg = new PasscodeGenerator(mac);
			if (counter == null) { // time-based totp
				return pcg.generateTimeoutCode();
			} else { // counter-based hotp
				return pcg.generateResponseCode(counter);
			}
		} catch (GeneralSecurityException e) {
			throw e;
		} catch (DecodingException e) {
			throw e;
		}
	}
}
