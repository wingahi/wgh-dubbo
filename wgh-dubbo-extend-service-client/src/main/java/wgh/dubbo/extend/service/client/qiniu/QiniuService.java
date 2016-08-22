package wgh.dubbo.extend.service.client.qiniu;

import javax.jws.WebService;

//七牛相关接口
@WebService
public interface QiniuService {

	/**
	 * 上传文件
	 * @param data
	 * @param fileType：1音频，2图片
	 * @param functionPrefix：功能前缀，如bill
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String uploadFileData(byte[] data, String functionPrefix, String fileName) throws Exception;

}
