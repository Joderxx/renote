package top.renote.util.digestUtil;

import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import java.security.*;
/**
 * Created by Joder_X on 2017/11/26.
 */
public class EncryptUtil {

    public static String md5(String s){
        return DigestUtils.md5DigestAsHex((s+SaltUtil.MD5_SALT).getBytes());
    }

    public static String SHA256(String s){
        return SHA(s+SaltUtil.SHA256_SALT,"SHA-256");
    }

    public static String SHA512(String s){
        return SHA(s+SaltUtil.SHA512_SALT,"SHA-512");
    }

    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static String base64Encode(String s){
        return new String(Base64Utils.encode(s.getBytes()));
    }

    public static String base64Decode(String s){
        return new String(Base64Utils.decode(s.getBytes()));
    }


    public static void main(String[] args) {
        System.out.println(SHA256("123").length());
        System.out.println(SHA512("123").length());

    }

}
