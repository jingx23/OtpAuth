package net.jingx.otpAuth.model;

public interface IProvider {

	void setName(String name);

	String getName();

	void setSecret(String secret);

	String getSecret();
}
