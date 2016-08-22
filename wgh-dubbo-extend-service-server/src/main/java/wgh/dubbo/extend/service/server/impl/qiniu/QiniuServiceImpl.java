package wgh.dubbo.extend.service.server.impl.qiniu;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;







import wgh.dubbo.common.exception.DataException;
import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.extend.service.client.qiniu.QiniuService;
import wgh.dubbo.extend.service.server.BaseService;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuServiceImpl extends BaseService implements QiniuService{

	@Value("${qiniu.access_key}")
	String accessKey;
	@Value("${qiniu.secret_key}")
	String secretKey;
	@Value("${qiniu.picture_bucket}")
    String pictureBucket;
    @Value("${qiniu.picture_domain}")
    String pictureDomain;

	@Override
	public String uploadFileData(byte[] data, String functionPrefix, String fileName) {
		if(Utils.isNull(data) || Utils.isEmpty(functionPrefix) || Utils.isEmpty(fileName)){
			log.error("data or functionPrefix or fileName is null");
			throw new DataException("参数错误");
		}
		String fileUrl = "";
		try {
			fileName = URLDecoder.decode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}
		// 获取扩展名
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 新的文件名 = 获取时间戳 + "." + 扩展名
        fileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
        String bucket = pictureBucket;
        String domain = pictureDomain;
        Auth auth = Auth.create(accessKey, secretKey);

		String key = functionPrefix + "/" + fileName;
		String uploadToken = auth.uploadToken(bucket, key, 3600, null, false);

		Response resp = null;
		try {
			UploadManager uploader = new UploadManager();
			resp = uploader.put(data, key, uploadToken, null, null, false);
			if(resp.isOK()){
				fileUrl = domain + key;
			}else{
				log.error(JSON.toJSONString(resp));
			}
		} catch (QiniuException e) {
			Response rs = e.response;
			System.out.println(rs.statusCode);
			System.out.println(rs.contentType());
			try {
				System.out.println(rs.bodyString());
			} catch (QiniuException e1) {
				log.error("NO!!!" +e1.getMessage(),e1);
			}
			log.error(e.getMessage(),e);
		}
		return fileUrl;
	}

}
