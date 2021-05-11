package org.kosta.avg.age.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class MemberDAO {
	private static MemberDAO dao = new MemberDAO();
	private DataSource dataSource;

	private MemberDAO() {
		dataSource = DataSourceManager.getInstance().getDataSource();
	}

	public static MemberDAO getInstance() {
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
	
	//로그인
	public MemberVO login(String id, String password) throws SQLException {
		MemberVO mvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con=dataSource.getConnection();
			String sql = "select nick from member where id=? and password=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mvo=new MemberVO();
				mvo.setId(id);
				mvo.setPassword(null);
				mvo.setNick(rs.getString(1));
			}
		}finally {
			closeAll(rs, pstmt, con);
		}
		return mvo;
	}

	//회원가입
	public boolean join(String id, String password, String nickname) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
				con=dataSource.getConnection();
				String sql = "insert into member values(?,?,?)";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				pstmt.setString(3,nickname);
				pstmt.executeUpdate();
			} 
			catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally {
				closeAll(pstmt, con);
			}
		return true;
	}

	public int joinIdCheck(String userID) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con=dataSource.getConnection();
			String sql = "select count(*) from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return 0;//이미 존재
			}else {
				return 1;//사용가능
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll(rs, pstmt, con);
		}
		return 2;
	}
}