package net.project.springboot.encryption;

public class GenerateEncryptionKey {
    public static byte[] salt = new String("12345678").getBytes();
    public static int iterationCount = 40000;
    public static int keyLength = 128;
}
