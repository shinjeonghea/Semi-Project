package org.kosta.avg.age.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	public void addFolderMember(String id, String folderName) throws SQLException {
		// 개인 계정에 추가될 폴더이름을 데이터베이스에 저장
		// 1. 해당 멤버의 아이디를 저장
		// 2. 추가될 폴더이름을 저장
		// insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '요리');
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO bookmark_folder VALUES(bookmark_folder_seq.nextval,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void updateFolderNameMember(String id, String beforefolderName, String afterfolderName) throws SQLException {
		// update bookmark_folder set folder_name=? where id=? and folder_name=?
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sql = new StringBuilder("update bookmark_folder set folder_name=? ");
			sql.append("where id=? and folder_name=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, afterfolderName);
			pstmt.setString(2, id);
			pstmt.setString(3, beforefolderName);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void deleteFolderMember(String id, String folderName) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM bookmark_folder where id=? and folder_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void addChannelMember(String id, String folderName, String channelName, String channelURL)
			throws SQLException {
		// insert into channel_member values(channel_member_seq.nextval,
		// (select folder_no from bookmark_folder where id='kgs' and folder_name='요리'),
		// '백종원의 요리비책', 'https://www.youtube.com/channel/UCyn-K7rZLXjGl7VXGweIlcA');
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sql = new StringBuilder("INSERT INTO channel_member VALUES(channel_member_seq.nextval, ");
			sql.append("(SELECT folder_no FROM bookmark_folder WHERE id=? AND folder_name=?), ");
			sql.append("?,?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			pstmt.setString(3, channelName);
			pstmt.setString(4, channelURL);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void deleteChannelMember(String channelName) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM channel_member WHERE channel_name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, channelName);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	// 신정희양 코드 시작
	public ArrayList<Integer> getFolderNameByMemberId(String id) throws SQLException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "select b.folder_no,b.folder_name   from  bookmark_folder b, member m where b.id=m.id and m.id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}
	
	public ArrayList<BookMarkVO> getChannelByMemberId(Integer i) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "select b.folder_name, c.channel_name, c.channel_url from bookmark_folder b, channel_member c where c.folder_no=b.folder_no and b.folder_no =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, i);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setFolderName(rs.getString(1));
				bvo.setChannelName(rs.getString(2));
				bvo.setChannelURL(rs.getString(3));
				list.add(bvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}
	// 신정희양 코드 끝
}
