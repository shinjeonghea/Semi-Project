package org.kosta.avg.age.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class BookMarkDAO {
	private static BookMarkDAO dao = new BookMarkDAO();
	private DataSource dataSource;
	
	private BookMarkDAO() {
		dataSource = DataSourceManager.getInstance().getDataSource();
	}
	
	public static BookMarkDAO getInstance() {
		return dao;
	}

	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException {
		closeAll(null, pstmt, con);
	}

	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (con != null)
			con.close();
	}

	public void addChannelMember(String id, String folder_name) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
		} finally {

		}
	}

	public void deleteChannelMember(int no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

		} finally {

		}
	}

	public void addFolderMember(String id, String folderName) throws SQLException {
		// 개인 계정에 추가될 폴더이름을 데이터베이스에 저장
		// 1. 해당 멤버의 아이디를 저장
		// 2. 추가될 폴더이름을 저장
		//insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '요리');
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO bookmark_folder VALUES(bookmark_folder_seq.nextval,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			pstmt.executeQuery();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void updateFolderNameMember(String id, String beforefolderName, String afterfolderName) throws SQLException {
		//update bookmark_folder set folder_name=? where id=? and folder_name=?
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con=dataSource.getConnection();
			StringBuilder sql=new StringBuilder("update bookmark_folder set folder_name=? ");
			sql.append("where id=? and folder_name=?");
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, beforefolderName);
			pstmt.setString(3, afterfolderName);
			pstmt.executeQuery();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void deleteFolderMember(String id, String folderName) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con=dataSource.getConnection();
			String sql="DELETE FROM bookmark_folder where id=? and folder_name=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}
}
