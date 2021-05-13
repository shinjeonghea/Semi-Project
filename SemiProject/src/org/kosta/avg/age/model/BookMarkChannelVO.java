package org.kosta.avg.age.model;

public class BookMarkChannelVO {
	private String folderName;
	private String channelName;
	private String channelURL;
	public BookMarkChannelVO(String folderName, String channelName, String channelURL) {
		super();
		this.folderName = folderName;
		this.channelName = channelName;
		this.channelURL = channelURL;
	}
	public BookMarkChannelVO() {
		super();
		// TODO Auto-generated constructor stub
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
	@Override
	public String toString() {
		return "BookMarkChannelVO [folderName=" + folderName + ", channelName=" + channelName + ", channelURL="
				+ channelURL + "]";
	}
	
}

