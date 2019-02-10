package cn.stronglink.collection.guis.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes {
	public static String iv = "1234567890123456";
	  public static String key= "1234567890123456";
	  private final static int ENCRYPTED_BLOCK_SIZE = 16;   // 加密数据块的大小
	  public static byte[] Encrypt(byte[] srawt, String sKey) throws Exception {  
	        if (sKey == null) {  
	            System.out.print("Key为空null");  
	            return null;  
	        }  
	        // 判断Key是否16  
	        if (sKey.length() != 16) {  
	            System.out.print("Key长度不是16");  
	            return null;  
	        }  
	       // byte[] bkeyraw = sKey.getBytes();  
	        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes(), "AES");  
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"  
	        IvParameterSpec iv = new IvParameterSpec(Aes.iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强  
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
	       
	        /* = sSrc.getBytes();*/
	        int len = srawt.length;
	        /* 计算0后的长度 */
	        int m = len /16;
	        if (m <= 0)
	        {
	           m = 1;
	        }
	        else if (len % 16 != 0)
	        {
	        	m = m +1;
	        }
	        
	        while(len % ENCRYPTED_BLOCK_SIZE != 0) len ++;
	        byte[] sraw = new byte[len];
	        /* 在最后补0 */
	        for (int i = 0; i < len; ++i) {
	            if (i < srawt.length) {
	                sraw[i] = srawt[i];
	            } else {
	                sraw[i] = 0;
	            }
	        }
	        byte[] encrypted = cipher.doFinal(sraw);  
	        byte[] retbuf = new byte[m * ENCRYPTED_BLOCK_SIZE];
	       // int[] retbuf = new int [m * 16];
	        for (int i =0; i < m * ENCRYPTED_BLOCK_SIZE; i++ )
	        {
	        	if (encrypted[i] <0)
	        		retbuf[i] = (byte) (256 + encrypted[i]);
	        	else
	        		retbuf[i] = encrypted[i];
	        }
	        return retbuf; //encrypted;//new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起2次加密的作用  
	    }  
	  
	  public static byte[] Decrypt(byte[] aSrc, String sKey) throws Exception { 
		  if (sKey == null) { 
              System.out.print("Key为空null"); 
              return null; 
          } 
          // 判断Key是否16 
          if (sKey.length() != 16) { 
              System.out.print("Key长度不是16"); 
              return null; 
          } 
          byte[] raw = sKey.getBytes(); 
          SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); 
          Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding"); 
          IvParameterSpec iv = new IvParameterSpec(Aes.iv.getBytes()); 
          cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv); 

          byte[] encrypted1;
          // 如果数据长度不是16的整倍数，就补全
          int iTemp = aSrc.length%ENCRYPTED_BLOCK_SIZE;
          if(iTemp!=0){
        	  encrypted1=new byte[aSrc.length+ENCRYPTED_BLOCK_SIZE-iTemp];
        	  System.arraycopy(aSrc, 0, encrypted1, 0, aSrc.length);
          }else{
        	  encrypted1 = aSrc; 
          }

          try { 
              byte[] original = cipher.doFinal(encrypted1); 
            //  String originalString = new String(original); 
              return original; 
          } catch (Exception e) {
              e.printStackTrace();
              return null; 
          } 
	    } 
	  
	  public static String toHexString(byte[] b){
	        StringBuffer buffer = new StringBuffer();
	        for (int i = 0; i < b.length; ++i){
	            buffer.append(toHexString1(b[i]));
	        }
	        return buffer.toString();
	    }
	    public static String toHexString1(byte b){
	        String s = Integer.toHexString(b & 0xFF);
	        if (s.length() == 1){
	            return "0" + s;
	        }else{
	            return s;
	        }
	    }

	    public static byte[] hex2byte(String strhex) { 
	        if (strhex == null) { 
	            return null; 
	        } 
	        int l = strhex.length(); 
	        if (l % 2 == 1) { 
	            return null; 
	        } 
	        byte[] b = new byte[l / 2]; 
	        for (int i = 0; i != l / 2; i++) { 
	            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2,  i * 2+2), 
	                    16); 
	        } 
	        return b; 
	    } 
	  
	    public static String byte2hex(byte[] b) { 
	        String hs = ""; 
	        String stmp = ""; 
	        for (int n = 0; n < b.length; n++) { 
	            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
	            if (stmp.length() == 1) { 
	                hs = hs + "0" + stmp; 
	            } else { 
	                hs = hs + stmp; 
	            } 
	        } 
	        return hs.toUpperCase(); 
	    } 
}
