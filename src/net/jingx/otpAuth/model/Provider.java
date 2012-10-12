package net.jingx.otpAuth.model;

public class Provider implements IProvider {

	private String name;
	private String secret;

	public Provider(String name, String secret) {
		this.name = name;
		this.secret = secret;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String getSecret() {
		return secret;
	}

}
