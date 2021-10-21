import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class RSAKeyPairGenerator {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public static final byte[] readAll(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
            while(true) {
                int var2 = inputStream.read();
                if (var2 == -1) {
                    byte[] var4 = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return var4;
                }
                byteArrayOutputStream.write(var2);
            }
        } catch (Exception var3) {
            return new byte[0];
        }
    }

    public static byte[] MD5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        return messageDigest.digest();
    }
    // 将byte 写入文件
    public void byte2File(String path, byte[] data) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(data);
        fos.flush();
        fos.close();
    }
    public static String toHex(byte[] var0) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            int var3 = var0[var2] & 15;
            int var4 = var0[var2] >> 4 & 15;
            var1.append(Integer.toString(var4, 16));
            var1.append(Integer.toString(var3, 16));
        }

        return var1.toString().toLowerCase();
    }
    public static InputStream readFile(String filename) throws IOException {
        File f = new File(filename);
        InputStream in =new FileInputStream(f);
        return in;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    // 加密数据
    public byte[] encryptPri(byte[] data, PrivateKey privateKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        RSAKeyPairGenerator PairGenerator = new RSAKeyPairGenerator();
        //cs4.0 key {27, -27, -66,   82, -58, 37, 92, 51, 85, -114, -118, 28, -74, 103, -53, 6 };
        //cs4.4 key }

        //byte[] data = { -54, -2, -64, -45, 0, 43, 1, -55, -61, 127, 0, 0, 34, -112, 127, 16, 27, -27, -66, 82, -58, 37, 92, 51, 85, -114, -118, 28, -74, 103, -53, 6 };
        byte[] data =   { -54, -2, -64, -45, 0 ,43 ,1, -55, -61, 127, 0, 0, 0,    1,   50, 16,  94, -104, 25, 74,  1, -58, -76, -113, -91, -126, -90, -87, -4, -69, -110, -42};
        byte[] rsaByte = PairGenerator.encryptPri(data, PairGenerator.getPrivateKey());
        PairGenerator.byte2File("RSA/cobaltstrike.auth", rsaByte);
        PairGenerator.byte2File("RSA/authkey.private", PairGenerator.getPrivateKey().getEncoded());
        PairGenerator.byte2File("RSA/authkey.pub", PairGenerator.getPublicKey().getEncoded());
        InputStream inputStream = readFile("RSA/authkey.pub");
        byte[] bytes = readAll(inputStream);
        byte[] bytes1 = MD5(bytes);
        String s = toHex(bytes1);
        System.out.println(s);


    }
}