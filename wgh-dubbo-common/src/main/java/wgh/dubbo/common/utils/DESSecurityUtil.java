package wgh.dubbo.common.utils;
import java.security.*;
import javax.crypto.*;
/**
 * DES加密解密工具
 * @description DES加密解密工具
 * @author chenxin
 * @date  2016－1-17
 * @version 1.0
 */
public class DESSecurityUtil {
 private static String strDefaultKey = "national";
   private Cipher encryptCipher = null;
     private Cipher decryptCipher = null;

 /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * 
      * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
      * @throws Exception
      *             本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
         // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
       StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
         // 把负数转换为正数
           while (intTmp < 0) {
                intTmp = intTmp + 256;
        }
           // 小于0F的数需要在前面补0
            if (intTmp < 16) {
               sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
       return sb.toString();
    }

   /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
    * 
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
      * @throws Exception
      *             本方法不处理任何异常，所有异常全部抛出
   * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
     public static byte[] hexStr2ByteArr(String strIn) throws Exception {
         byte[] arrB = strIn.getBytes();
         int iLen = arrB.length;

         // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
         byte[] arrOut = new byte[iLen / 2];
         for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
       return arrOut;
    }

 /**
    * 默认构造方法，使用默认密钥
     * 
     * @throws Exception
     */
    public DESSecurityUtil() throws Exception {
         this(strDefaultKey);
     }

    /**
     * 指定密钥构造方法
     * 
     * @param strKey
     *            指定的密钥
     * @throws Exception
     */
    @SuppressWarnings("restriction")
	public DESSecurityUtil(String strKey) throws Exception {
         Security.addProvider(new com.sun.crypto.provider.SunJCE());
         Key key = getKey(strKey.getBytes());
  
          encryptCipher = Cipher.getInstance("DES");
          encryptCipher.init(Cipher.ENCRYPT_MODE, key);
  
          decryptCipher = Cipher.getInstance("DES");
          decryptCipher.init(Cipher.DECRYPT_MODE, key);
      }
  
      /**
       * 加密字节数组
       * 
       * @param arrB
       *            需加密的字节数组
       * @return 加密后的字节数组
       * @throws Exception
       */
     public byte[] encrypt(byte[] arrB) throws Exception {
         return encryptCipher.doFinal(arrB);
     } 
     /**
      * 加密字符串
      * 
      * @param strIn
      *            需加密的字符串
      * @return 加密后的字符串
      * @throws Exception
      */
     public String encrypt(String strIn) throws Exception {
         return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
     }
 

     
     /**
      * 解密字节数组
      * 
      * @param arrB
      *            需解密的字节数组
      * @return 解密后的字节数组
      * @throws Exception
      */
     public byte[] decrypt(byte[] arrB) throws Exception {
         return decryptCipher.doFinal(arrB);
    }

     /**
      * 解密字符串
      * 
      * @param strIn
     *            需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) throws Exception {
       return new String(decrypt(hexStr2ByteArr(strIn)));
    }

     /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
      * 
     * @param arrBTmp
    *            构成该字符串的字节数组
      * @return 生成的密钥
     * @throws java.lang.Exception
    */
     private Key getKey(byte[] arrBTmp) throws Exception {
         // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

     // 将原始字节数组转换为8位
         for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
             arrB[i] = arrBTmp[i];
         }
 
         // 生成密钥
         Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
 
         return key;
     }
     
     
     public static void main(String[] args) throws Exception{
    	 DESSecurityUtil eESSecurityUtil = new DESSecurityUtil();
   
    	 System.out.println(eESSecurityUtil.encrypt("e91316a218673db2a2f953aa9399cfd411e9ef6caa681b6026f917be6ba6541315c91db46462eaf6bbed5ed14c74f6177005ef7e02447b8526c49a209cc3887bc6a468b3cae19621e4f27ac5373edd0e93840340a989f814b15b67c45dcb501442f04b56ff053c7ea219d4ea068068ce9ce72c0a237d2840f722497e3dd71e13a4c8c14e2195ef1c52fbf629157182f6676ff7cdfd7b58b414836348a43436a16d3dc9de2e0a120118edf1d053a64dd4756f6971575a66f2e49646ff03b57cf0e1bebf90f96862606b25c6e8bfc2516da54cde37c2d0d9c55ab382015544229d8324f844dd1930138f1571b99d3451f919657eb014f5fabc074dfd16dc34c59933979bbfe63e35c2b3a3b7ed93812666707091895308c4e0a950bc4b1ea548fc295a39301537b407fc4bc7619efacbc8984ae857c38cfd5d34a5eef8cc95e719ab8ee323d1daa59460f3a515101e74f2ea8f138ce139a574ebc121da3e4714da7852ca6cc20b58f0c89c50c76f5bb3a82e25f8259c1d78770d6a7a58ee9be69c1104669351e3a290e120cf6e1c0e322b946b06c0c01373e58eb83a9d537c1c652ac3a0392d3bf3b2206749aadcdbebebf8de4cb85959939f5b39b8e824f7326915c0b050c5c372d67197abd0ecb65a3fa9ffc943dc230501549b87a92321177f20aeafd84af79c3c379965fc7bb8cb82f11a48cb7a7de19ec6972e3fc8151acdd849fee86f305fc1d1e77e911901196c4054d92b54e295131455bb3695012d29bd9a9938c7af0993d1a10f6147b56b4035eaadb32349682c3f8284941652abb185797d66589d0ec28e77c84bfa98964d46a36b81a3f9e1ff9db6c4391b53eb71b4d55dbab2dc734294dc8244688e1c529b70efc6e021be5a97fc089c935607970634890c3c26930d4e1977b74af293ec519277dca659804835938ef785f709601e1b2072ee34881a388338eadae18391097a8f9f1903b54a3b07e1f7a80c3a750d1ca8a379ff6acd0bd2999eb610e05055f424c0436d2031abb867dc00bbdae54f86959567aadd917cb5073dd9682aa21062530f098b00dc5fae2a22ff8eddab8eb83a9d537c1c65aa5059bd9b4daced923f597031d4a1cb5cd170dc1f4859e068bef827e36562aeb5f92e4b815623f8dac98723b421bf3b2a319f8695509c82d1e77e911901196c68bef827e36562ae0b571c50de3ef25c1056008fe3602eea3f8284941652abb14ddd39ea4343f22c1399f11e6e7a1e37519277dca65980489e74587cbe1ce938d1e77e911901196c5991ffbc51cdca424eb4910663a25e321d42f9101e08b0bf142f758a1e88c2441eace161633f75b2baf9819a92897f138b25324c551b550020b871f00123452b5fa50f2c047222f1a885bdb0ab0072dd142f758a1e88c244e6dd3f21754619a54567b32a377a75a409dcaf19ed032ee8142f758a1e88c244a537eeab84a346241f9dc0b37e2bb66babc8352d526f9d94142f758a1e88c24460f91f1906178c5b5d72655f4b2b6eb69d4ac33c422f5610a54cde37c2d0d9c5142f758a1e88c244b49f9094a14b27bd866e968d5d3a6dc29841950903e414b85cd170dc1f4859e0142f758a1e88c244a6466fabbe5dffb1e4ea4bf89cb755b19111dcabef60008f6c1b9607cfd7d174d88efac0e408d1680130974a78f1c90415c0b050c5c372d681b2286a84366dc28b25324c551b550058af0b66e4e0c60c73cb11ad8e2dc0ca67f778ed0f7fc92e0785e2d1452ba1fe3fc95df56631d39346648ad0532c4dc0b0ba0ec9c7170957d4315341f348809e9937b180486762160b43ab5f9f6f9a85586a2337b710c32962c9f40c357781e4701e43140d742614c4ca39629b271d65fb2ddbec9145413e854bedc470d698a4b3315a7d97b20f2f48ea98b3b8be73c9c733846cbdec46d8a1a309e6a2e0b81607affacc89a93e349cbfb1ea744bc0bee16e5859e3d76389ebe15124746cd6a3b514e577cc6de39e4ddd39ea4343f22cb00ad8fcfc92b0ce4bbeb1d072f39bdca5b85d2b8f448362390308a75a29aa874ddd39ea4343f22cdf63a4a2fd009fbbc486e760751b8edce43f062590dbc3005b52b96324dcd27dd1e77e911901196ca2571c4f02a55099c7351c2b5d2797fbf0ef0290a5cfcd5e9b5ed313cec1239bd1e77e911901196cdae88e671f9f87c83b716115b41820752e32398faa2fe0e2ce0bf6de74ce679fdac98723b421bf3b70911c142b97a031aeeebdb1c698e94ea984bc60f0a5f2deb3fa73942aeda1c8d1e77e911901196c1cb873d27348f7c673cb11ad8e2dc0cac657a1ca1767e5f9e8f554c7bcfee14f4ddd39ea4343f22c08d77fbdbf75f697f3e9085a1671089cfc073285e940aadf62ca87e913b4ec02d1e77e911901196c67e0a76ed0a9ad7e3d08f3b63db62d7568a67b7203de6d3df306b3c53f6a72ab769f14dc31fa3e58142f758a1e88c2448ba3c103a90bd7408bc40e4ebe01024a9f55b51b761e0aee3200debc6042d6a590976d6230a9d826c6c9b97eab8bcd38a382bad97c2007ce11136965ea03e0d08fd7d0fb8de329213606c24c3a146df473cc1c7d71bdaf4dd1e77e911901196cf8a4dec3bc0d289d4e9d09c0745dd60ea2f6bbbfc5a2fd051a8ef0183bfe2d8f96eb622298ed0038a72227a3d171126d79698a0db52c2794a6cc9435696a48b54374561fd1c0e134836092188a53953adac98723b421bf3b14b8a0001d5ca38d225f941af0ca07237b4ea8aa93d19418e2054c46b95e31f5dac98723b421bf3ba58f188c51290c7fb52015821b9e5508fcfb9bd9551ec215b58f8320e738f07dd575566871b22f4221cd3e6b83392362b17d18435d1eacf01a88598adbf574264ddd39ea4343f22cf7fc4ca88e77004571be771d4e20972fdf02722ab5fbe615f095254421ff6ba29c45dae5ce44ddb9d51c6a20b3acea80d1e77e911901196c24504805e3db16078a67182392c0cd09ca29cd6fe7f99fe6d1e77e911901196c9d4ac33c422f5610796241c24f0228fc4489921c2105729dd181d215dfff7802dac98723b421bf3b0f242296395077e14ddd39ea4343f22c65c12860ae8a240bfcea4b6eb23fb1e0fb34fdbef856c6983782e8d9fcf2d01dd86867410ab64be91af6cf5f58e95cf5a72227a3d171126d3d0bd5ebc6c3dc5e2a6ddd26507c853831fcdb3872c538a7a292b35427a7456644ef14b89d480596a9d617cf321e1ecac8875d6695a190e9d14695d48bf5bcb44ddd39ea4343f22c3200debc6042d6a590976d6230a9d826c6c9b97eab8bcd38a382bad97c2007ce11136965ea03e0d007affacc89a93e341b7f7184c51b636cdaba0104a78af62f3178e6cc15735884b3315a7d97b20f2f2d7630e6b5b72bdf5835bef856afd33d075989a0ed9e136efbd21e84c71df76bd1e77e911901196c3947d95dd1b1b364205de376a6f54ab84961abfbefa970250a0042fbcfb557594ddd39ea4343f22c61150456b69e09fcab98edd289db058171be7fa704a3ba52d1e77e911901196cbdfcb3d4d94435702b8c2d7a9332c836fe315637151f531f4ddd39ea4343f22cc86851ce629831454c3392bfbaf7b24a13b2a98d2241d8ab1772b43455bfb189e52c401f02ac518c2fa856b1ab7e372d55a3666760a06adfc559a57c19d33dedbbed5ed14c74f617024896c77374449a"));
	}
 }