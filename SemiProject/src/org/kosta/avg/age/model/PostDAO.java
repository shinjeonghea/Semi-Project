package org.kosta.avg.age.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class PostDAO {
	private static PostDAO instance = new PostDAO();
	private DataSource dataSource;
	private PostDAO() {
		dataSource = DataSourceManager.getInstance().getDataSource();
	}
	public static PostDAO getInstance() {
		return instance;
	} 
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	public void closeAll(PreparedStatement pstmt,
			Connection con) throws SQLException{
		closeAll(null,pstmt,con);
	}
	public void closeAll(ResultSet rs,PreparedStatement pstmt,
			Connection con) throws SQLException{
		if(rs!=null)
			rs.close();
		if(pstmt!=null)
			pstmt.close();
		if(con!=null)
			con.close();
	}
	
	/**
	 * 게시물 목록 리스트를 반환하는 메서드
	 * @author KMK
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getPostingList() throws SQLException{
		ArrayList<PostVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder("select br.post_no, br.title, m.nick ");
			sql.append(", to_char(br.time_posted, 'yyyy-mm-dd') as time_posted, br.hits ");
			sql.append("from member m, BOARD_RECOMMEND br ");
			sql.append("where m.id = br.id");
			pstmt  = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setTitle(rs.getString(2));
				pvo.setTimePosted(rs.getString(4));
				pvo.setHits(rs.getInt(5));
				MemberVO mvo = new MemberVO();
				mvo.setNick(rs.getString(3));
				pvo.setMvo(mvo);
				list.add(pvo);
			}
		}finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}
	
	public PostVO getPostingByNo(String no) throws SQLException{
		PostVO pvo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			StringBuilder sql=new StringBuilder();
			sql.append("select br.title, to_char(br.time_posted,'YYYY.MM.DD  HH24:MI:SS') as time_posted ");
			sql.append(",br.content, br.hits, br.id, m.nick ");
			sql.append("from board_recommend br, member m ");
			sql.append("where br.id=m.id and br.post_no=?");		
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs=pstmt.executeQuery();		
			if(rs.next()){
				pvo=new PostVO();
				pvo.setPostNo(no);
				pvo.setTitle(rs.getString("title"));
				pvo.setContent(rs.getString("content"));				
				pvo.setHits(rs.getInt("hits"));
				pvo.setTimePosted(rs.getString("time_posted"));
				MemberVO mvo=new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setNick(rs.getString("nick"));
				pvo.setMvo(mvo);
			}			
		}finally{
			closeAll(rs,pstmt,con);
		}
		return pvo;
	}
	
	public void updateHit(String no) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		try{
			con=getConnection(); 
			String sql="update board_recommend set hits=hits+1 where post_no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, no);	
			pstmt.executeUpdate();			
		}finally{
			closeAll(pstmt,con);
		}
	}
	
	
	
}
