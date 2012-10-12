package net.jingx.otpAuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import net.jingx.otpAuth.model.IProvider;
import net.jingx.otpAuth.model.Provider;

public class SecretFileManager {
	private static String PATH_GAUTH_SECKEY = System.getProperty("user.home")
			+ "/" + ".gauth" + "/";
	private static String SEC_FILENAME = "sec";

	public static List<IProvider> read() {
		try {
			List<IProvider> listProviders = new ArrayList<IProvider>();
			File fSec = new File(PATH_GAUTH_SECKEY + SEC_FILENAME);
			if (fSec.exists()) {
				FileReader fr = new FileReader(fSec);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					String[] sline = line.split(";");
					if (sline.length == 2) {
						IProvider provider = new Provider(sline[0], sline[1]);
						listProviders.add(provider);
					}
				}
				br.close();
			}
			return listProviders;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void write(List<IProvider> listProvider) {
		try {
			File fDir = new File(PATH_GAUTH_SECKEY);
			fDir.mkdirs();
			File fSec = new File(PATH_GAUTH_SECKEY + SEC_FILENAME);
			FileWriter fw = new FileWriter(fSec);
			BufferedWriter bw = new BufferedWriter(fw);
			for (IProvider provider : listProvider) {
				bw.write(provider.getName() + ";" + provider.getSecret() + "\n");
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
