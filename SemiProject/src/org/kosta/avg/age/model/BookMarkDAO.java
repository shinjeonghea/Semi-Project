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

	public ArrayList<Integer> getFolderNameByMemberId(String id) throws SQLException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "select b.folder_no,b.folder_name	from  bookmark_folder b, member m where b.id=m.id and m.id=?";
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

	public ArrayList<BookMarkVO> getBookmarkByMemberIdAndFolderName(String id, String folderName) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select channel_name, channel_url from bookmark_user ");
			sb.append("where id=? and folder_name=?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, folderName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setFolderName(folderName);
				bvo.setChannelName(rs.getString(1));
				bvo.setChannelURL(rs.getString(2));
				list.add(bvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	public void updateMemberBookmarkByPostNo(String postNo, String folderName, String id) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sql1 = new StringBuilder();
			sql1.append("select bb.channel_name, bb.channel_url ");
			sql1.append("from board b, bookmark_board bb ");
			sql1.append("where b.post_no=bb.post_no and b.post_no=?");
			pstmt = con.prepareStatement(sql1.toString());
			pstmt.setString(1, postNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setChannelName(rs.getString(1));
				bvo.setChannelURL(rs.getString(2));
				list.add(bvo);
			}
			rs.close();
			pstmt.close();
			StringBuilder sql2 = new StringBuilder();
			sql2.append("insert into bookmark_user(no, folder_name, channel_name, channel_url, id)  ");
			sql2.append("values(bookmark_user_seq.nextval, ?, ?, ?, ?)");
			pstmt = con.prepareStatement(sql2.toString());
			for (int i = 0; i < list.size(); i++) {
				pstmt.setString(1, folderName);
				pstmt.setString(2, list.get(i).getChannelName());
				pstmt.setString(3, list.get(i).getChannelURL());
				pstmt.setString(4, id);
				pstmt.executeUpdate();
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
	}

}
