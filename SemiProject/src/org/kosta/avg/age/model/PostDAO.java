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
	 * 페이징 적용 ->  row_number()를 사용한다.
	 * @author KMK
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getPostingList(PagingBean pagingBean) throws SQLException{
		ArrayList<PostVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder("select rnum, br.post_no, br.title, time_posted, br.hits, m.nick ");
			sql.append("from ( ");
			sql.append("select  row_number() over(order by post_no desc) as rnum, br.post_no, br.title ");
			sql.append(", to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, br.id ");
			sql.append("from board_recommend br ");
			sql.append(") br, member m ");
			sql.append(" where m.id=br.id AND rnum BETWEEN ? AND ?");
			pstmt  = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pagingBean.getStartRowNumber());
			pstmt.setInt(2, pagingBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString("post_no"));
				pvo.setTitle(rs.getString("title"));
				pvo.setTimePosted(rs.getString("time_posted"));
				pvo.setHits(rs.getInt("hits"));
				MemberVO mvo = new MemberVO();
				mvo.setNick(rs.getString("nick"));
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
	
	public int getTotalPostCount() throws SQLException{
		int totalCount=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=dataSource.getConnection();
			String sql="select count(*) from board_recommend";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				totalCount=rs.getInt(1);
		}finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}
	
	
	
}
