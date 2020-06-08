package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.exolab.castor.xml.Marshaller;
import org.junit.Test;

import com.assessment.data.TestCase;
import com.assessment.data.TestCases;

public class TestWithoutAppContext {
	
	@Test
	public void testCreateRSAKeys() throws NoSuchAlgorithmException, Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		Key pub = kp.getPublic();
		Key pvt = kp.getPrivate();
		
		String outFile = "private";
		FileOutputStream out = new FileOutputStream(outFile + ".key");
		out.write(pvt.getEncoded());
		out.close();

		out = new FileOutputStream("public" + ".pub");
		out.write(pvt.getEncoded());
		out.close();
		
		System.err.println("Private key format: " + pvt.getFormat());
		// prints "Private key format: PKCS#8" on my machine

		System.err.println("Public key format: " + pub.getFormat());
		// prints "Public key format: X.509" on my machine
	}
	
	@Test
	public void testCreateAesKey() throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        byte[] key = secKey.getEncoded();
        String secretkey = DatatypeConverter.printHexBinary(key);
        FileUtils.write(new File("secret.key"), secretkey);
	}
	
	@Test
	public void testEncryyptAndDecrypt() throws Exception{
		java.security.Security.addProvider(
		         new org.bouncycastle.jce.provider.BouncyCastleProvider()
		);
		byte[] pub = FileUtils.readFileToByteArray(new File("public.pub"));
		X509EncodedKeySpec ks = new X509EncodedKeySpec(pub);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pubkey = kf.generatePublic(ks);
		String message = "jatin.sutarin@gmail.com";
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE	, pubkey);
		byte[] encryptedessage = cipher.doFinal(message.getBytes());
		
		byte[] priv = FileUtils.readFileToByteArray(new File("private.key"));
		PKCS8EncodedKeySpec ks1 = new PKCS8EncodedKeySpec(priv);
		KeyFactory kf1 = KeyFactory.getInstance("RSA");
		PrivateKey pvt = kf.generatePrivate(ks);
		Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher1.init(Cipher.DECRYPT_MODE, pvt);
        String decryptedMessage =  new String(cipher1.doFinal(priv));
        System.out.println("decry msg "+decryptedMessage);
	}
	
	@Test
	public void testMarshalXmlToJava() throws Exception{
		TestCases testCases = new TestCases();
		TestCase case1 = new TestCase("testFindNoOfOccurrences1", "na", 2, true, "Functional", "5");
		TestCase case2 = new TestCase("testFindNoOfOccurrences2", "na", 2, true, "Functional", "1");
		TestCase case3 = new TestCase("testFindNoOfOccurrences3", "na", 2, true, "Functional", "5");
		List<TestCase> cases = new ArrayList<TestCase>();
		cases.add(case1);
		cases.add(case2);
		cases.add(case3);
		testCases.setCases(cases);
		
		FileWriter writer = new FileWriter("test.xml");
		Marshaller.marshal(testCases, writer);
//		FileReader reader = new FileReader(new File("testcases.xml"));
//		String str = FileUtils.readFileToString(new File("testcases.xml"));
//		TestCases testCases = (TestCases)Unmarshaller.unmarshal(TestCases.class, reader);
//		System.out.println(testCases.getCases().size());
	}

}
