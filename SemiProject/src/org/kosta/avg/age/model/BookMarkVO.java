package org.kosta.avg.age.model;

public class BookMarkVO {
	private String folderName;
	private String channelName;
	private String channelURL;
	private int folderNo;
	public BookMarkVO(String folderName, String channelName, String channelURL, int folderNo) {
		super();
		this.folderName = folderName;
		this.channelName = channelName;
		this.channelURL = channelURL;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelURL() {
		return channelURL;
	}
	public void setChannelURL(String channelURL) {
		this.channelURL = channelURL;
	}
	
	public int getFolderNo() {
		return folderNo;
	}
	public void setFolderNo(int folderNo) {
		this.folderNo = folderNo;
	}
	public BookMarkVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BookMarkVO [folderName=" + folderName + ", channelName=" + channelName + ", channelURL=" + channelURL
				+ ", folderNo=" + folderNo + "]";
	}
	
	
}
