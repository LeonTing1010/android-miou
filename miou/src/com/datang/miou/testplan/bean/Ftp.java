package com.datang.miou.testplan.bean;

/**
 * FTP参数
 * 
 * @author suntongwei
 */
public class Ftp implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1628898556246221909L;

	// 下载还是上传
	private Boolean isDown = true;
	
	// 公共参数_主机名
	private String hostname = "222.68.172.16";
	// 公共参数_端口
	private Integer port = 23;
	// 公共参数_用户名
	private String username = "test";
	// 公共参数_密码
	private String password = "test";
	// 公共参数_测试次数
	private Integer num = 2;
	// 公共参数_线程数
	private Integer threadNum = 2;
	// 公共参数_超时时间
	private Integer timeout = 30;
	// 公共参数_间隔时间
	private Integer interval = 10;
	
	// 下载参数_是否根据时间下载
	private Boolean isDownByTime = false;
	// 下载参数_下载文件路径
	private String downFilePath = "";
	// 下载参数_下载时间
	private Integer downTime = 0;
	
	// 上传参数_是否按大小上传
	private Boolean isUploadBySize = false;
	// 上传参数_上传远程目录
	private String uploadRemotePath = "";
	// 上传参数_上传本地文件
	private String uploadLocalFile = "";
	// 上传参数_上传大小
	private Integer uploadFileSize = 0;
	
	public Boolean isDown() {
		return isDown;
	}
	public void setDown(Boolean isDown) {
		this.isDown = isDown;
	}
	public Boolean isUploadBySize() {
		return isUploadBySize;
	}
	public void setUploadBySize(Boolean isUploadBySize) {
		this.isUploadBySize = isUploadBySize;
	}
	public Boolean isDownByTime() {
		return isDownByTime;
	}
	public void setDownByTime(Boolean isDownByTime) {
		this.isDownByTime = isDownByTime;
	}
	public Boolean getIsDown() {
		return isDown;
	}
	public void setIsDown(Boolean isDown) {
		this.isDown = isDown;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(Integer threadNum) {
		this.threadNum = threadNum;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Boolean getIsDownByTime() {
		return isDownByTime;
	}
	public void setIsDownByTime(Boolean isDownByTime) {
		this.isDownByTime = isDownByTime;
	}
	public String getDownFilePath() {
		//return downFilePath;
		String aa = "3_1G.RAR";
		return aa;
	}
	public void setDownFilePath(String downFilePath) {
		this.downFilePath = downFilePath;
	}
	public Integer getDownTime() {
		return downTime;
	}
	public void setDownTime(Integer downTime) {
		this.downTime = downTime;
	}
	public Boolean getIsUploadBySize() {
		return isUploadBySize;
	}
	public void setIsUploadBySize(Boolean isUploadBySize) {
		this.isUploadBySize = isUploadBySize;
	}
	public String getUploadRemotePath() {
		return uploadRemotePath;
	}
	public void setUploadRemotePath(String uploadRemotePath) {
		this.uploadRemotePath = uploadRemotePath;
	}
	public String getUploadLocalFile() {
		return uploadLocalFile;
	}
	public void setUploadLocalFile(String uploadLocalFile) {
		this.uploadLocalFile = uploadLocalFile;
	}
	public Integer getUploadFileSize() {
		return uploadFileSize;
	}
	public void setUploadFileSize(Integer uploadFileSize) {
		this.uploadFileSize = uploadFileSize;
	}
}
