package net.jingx.otpAuth;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class BarcodeDecoderTest extends TestCase {

	private static final String PATH_PREFIX = "test/data/";

	public void test1_decodeOtpQrCode() throws IOException, NotFoundException,
			ChecksumException, FormatException {
		BufferedImage img = ImageIO.read(new File(PATH_PREFIX
				+ "otp_qrcode.png"));
		LuminanceSource source = new BufferedImageLuminanceSource(img);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result = reader.decode(bitmap);
		assertEquals(
				"otpauth://totp/Dropbox:test@domain.com?secret=1TVRG2C7VG2ECS54XGBOGB3GQX",
				result.getText());
	}
}
