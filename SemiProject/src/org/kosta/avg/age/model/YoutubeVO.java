package org.kosta.avg.age.model;

public class YoutubeVO {
	private String title;
	private String channelURL;
	private String thumbnailsURL;

	public YoutubeVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YoutubeVO(String title, String channelURL, String thumbnailsURL) {
		super();
		this.title = title;
		this.channelURL = channelURL;
		this.thumbnailsURL = thumbnailsURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannelURL() {
		return channelURL;
	}

	public void setChannelURL(String channelURL) {
		this.channelURL = channelURL;
	}

	public String getThumbnailsURL() {
		return thumbnailsURL;
	}

	public void setThumbnailsURL(String thumbnailsURL) {
		this.thumbnailsURL = thumbnailsURL;
	}

	@Override
	public String toString() {
		return "YoutubeVO [title=" + title + ", channelURL=" + channelURL + ", thumbnailsURL=" + thumbnailsURL + "]";
	}

}
