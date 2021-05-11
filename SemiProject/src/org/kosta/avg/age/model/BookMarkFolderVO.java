package org.kosta.avg.age.model;

public class BookMarkFolderVO {
	private int folderNo;
	private String folderName;
	
	public BookMarkFolderVO() {
		super();
	}
	
	public BookMarkFolderVO(int folderNo, String folderName) {
		super();
		this.folderNo = folderNo;
		this.folderName = folderName;
	}

	public int getFolderNo() {
		return folderNo;
	}

	public void setFolderNo(int folderNo) {
		this.folderNo = folderNo;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	@Override
	public String toString() {
		return "BookMarkFolderVO [folderNo=" + folderNo + ", folderName=" + folderName + "]";
	}

	
	
}