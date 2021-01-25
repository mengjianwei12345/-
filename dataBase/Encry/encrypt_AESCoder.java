







/**
 * AES�ԳƼ����㷨��java6ʵ�֣�bouncycastleҲ֧��AES�ԳƼ����㷨
 * ���ǿ�����AES�㷨ʵ��Ϊ�ο������RC2��RC4��Blowfish�㷨��ʵ��
 * @author kongqz
 * */
public class AESCoder {
    /**
     * ��Կ�㷨
     * java6֧��56λ��Կ��bouncycastle֧��64λ
     * */
    public static final String KEY_ALGORITHM="AES";

    /**
     * ����/�����㷨/����ģʽ/��䷽ʽ
     * 
     * JAVA6 ֧��PKCS5PADDING��䷽ʽ
     * Bouncy castle֧��PKCS7Padding��䷽ʽ
     * */
    public static final String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";

    /**
     * 
     * ������Կ��java6ֻ֧��56λ��Կ��bouncycastle֧��64λ��Կ
     * @return byte[] ��������Կ
     * */
    public static byte[] initkey() throws Exception{

        //ʵ������Կ������
        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);
        //��ʼ����Կ��������AESҪ����Կ����Ϊ128λ��192λ��256λ
        kg.init(128);
        //������Կ
        SecretKey secretKey=kg.generateKey();
        //��ȡ��������Կ������ʽ
        return secretKey.getEncoded();
    }
    /**
     * ת����Կ
     * @param key ��������Կ
     * @return Key ��Կ
     * */
    public static Key toKey(byte[] key) throws Exception{
        //ʵ����DES��Կ
        //������Կ
        SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * ��������
     * @param data ����������
     * @param key ��Կ
     * @return byte[] ���ܺ������
     * */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //��ԭ��Կ
        Key k=toKey(key);
        /**
         * ʵ����
         * ʹ�� PKCS7PADDING ��䷽ʽ�������·�ʽʵ��,���ǵ���bouncycastle���ʵ��
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //��ʼ��������Ϊ����ģʽ
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //ִ�в���
        return cipher.doFinal(data);
    }
    /**
     * ��������
     * @param data ����������
     * @param key ��Կ
     * @return byte[] ���ܺ������
     * */
    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        //��ӭ��Կ
        Key k =toKey(key);
        /**
         * ʵ����
         * ʹ�� PKCS7PADDING ��䷽ʽ�������·�ʽʵ��,���ǵ���bouncycastle���ʵ��
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //��ʼ��������Ϊ����ģʽ
        cipher.init(Cipher.DECRYPT_MODE, k);
        //ִ�в���
        return cipher.doFinal(data);
    }
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String str="haha";
        System.out.println("ԭ�ģ�"+str);
        //��ʼ����Կ
        byte[] key=AESCoder.initkey();
        System.out.println("��Կ��"+Base64.encodeBase64String(key));
        //��������
        byte[] data=AESCoder.encrypt(str.getBytes(), key);
        System.out.println("���ܺ�"+Base64.encodeBase64String(data));
        //��������
        data=AESCoder.decrypt(data, key);
        System.out.println("���ܺ�"+new String(data));
    }
}