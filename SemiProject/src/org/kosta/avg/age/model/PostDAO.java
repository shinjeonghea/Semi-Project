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

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
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

	/**
	 * 게시물 목록 리스트를 반환하는 메서드 페이징 적용 -> row_number()를 사용한다.
	 * 
	 * @author KMK
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getPostingList(PagingBean pagingBean) throws SQLException {
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
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pagingBean.getStartRowNumber());
			pstmt.setInt(2, pagingBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {
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
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * no로 통해서 게시물을 보는 메서드
	 * @param no
	 * @return
	 * @throws SQLException
	 */
	public PostVO getPostingByNo(String no) throws SQLException {
		PostVO pvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(no);
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select br.title, to_char(br.time_posted,'YYYY.MM.DD  HH24:MI:SS') as time_posted ");
			sql.append(",br.content, br.hits, br.id, m.nick ");
			sql.append("from board_recommend br, member m ");
			sql.append("where br.id=m.id and br.post_no=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pvo = new PostVO();
				pvo.setPostNo(no);
				pvo.setTitle(rs.getString("title"));
				pvo.setContent(rs.getString("content"));
				pvo.setHits(rs.getInt("hits"));
				pvo.setTimePosted(rs.getString("time_posted"));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setNick(rs.getString("nick"));
				pvo.setMvo(mvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return pvo;
	}
	
	// 게시물 조회시 조회수 +1 해주는 메서드
	public void updateHit(String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "update board_recommend set hits=hits+1 where post_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}
	
	/**
	 * 총 게시물 갯수 (페이징할 때 필요)
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPostCount() throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "select count(*) from board_recommend";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}

	/**
	 * 게시물 작성시 라디오 형식으로 폴더를 불러오는 메서드
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BookMarkVO> loadFolderList(String id) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder("select bf.folder_no, m.id, bf.folder_name ");
			sql.append("from BOOKMARK_FOLDER bf, MEMBER m where m.id=bf.id and m.id=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setFolderName(rs.getString(3));
				list.add(bvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 특정 계정이 가진 즐겨찾기 폴더에 해당하는 채널 리스트를 가져옴.
	 * 
	 * @param folderName
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	
	// 이건 뭔 메서드??
	public ArrayList<BookMarkVO> loadChannelList(String folderName, String id) throws SQLException {
		ArrayList<BookMarkVO> channelList = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select bf.folder_name, bf.folder_no, cm.channel_name, cm.channel_url ");
			sql.append("from bookmark_folder bf, channel_member cm ");
			sql.append("where bf.folder_no=cm.folder_no and bf.folder_name=? and bf.id=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, folderName);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setFolderName(rs.getString("folder_name"));
				bvo.setChannelName(rs.getString("channel_name"));
				bvo.setChannelURL(rs.getString("channel_url"));
				bvo.setFolderNo(rs.getInt("folder_no"));
				channelList.add(bvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}

		return channelList;
	}
	
	/**
	 * 게시물 포스팅 메서드
	 * @param folderName
	 * @param pvo
	 * @throws SQLException
	 */
	public void posting(String folderName, PostVO pvo) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			// BookmarkVO 리스트 만들기 (채널 리스트임...)
			StringBuilder sql1 = new StringBuilder(
					"select bf.folder_name, cm.channel_name, cm.channel_url ");
			sql1.append("from bookmark_folder bf, channel_member cm ");
			sql1.append("where bf.folder_no=cm.folder_no and bf.folder_name=? and bf.id=?");
			pstmt = con.prepareStatement(sql1.toString());
			pstmt.setString(1, folderName);
			pstmt.setString(2, pvo.getMvo().getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkVO bvo = new BookMarkVO();
				bvo.setFolderName(rs.getString("folder_name"));
				bvo.setChannelName(rs.getString("channel_name"));
				bvo.setChannelURL(rs.getString("channel_url"));
				list.add(bvo);
			}
			rs.close();
			pstmt.close();
			// br에 insert
			StringBuilder sql2 = new StringBuilder("insert into board_recommend(post_no,title,content,time_posted,id) ");
			sql2.append("values(board_recommend_seq.nextval, ?, ?, sysdate, ?)");
			pstmt = con.prepareStatement(sql2.toString());
			pstmt.setString(1, pvo.getTitle());	
			pstmt.setString(2, pvo.getContent());
			pstmt.setString(3, pvo.getMvo().getId());
			pstmt.executeUpdate();
			pstmt.close();
			// bb에 insert
			for (int i = 0; i < list.size(); i++) {
				StringBuilder sql3 = new StringBuilder();
				sql3.append("insert into bookmark_board(no,folder_name,channel_name,channel_url,post_no) ");
				sql3.append("values(bookmark_board_seq.nextval, ?, ?, ?, board_recommend_seq.currval)");
				pstmt = con.prepareStatement(sql3.toString());
				BookMarkVO bvo = list.get(i);
				pstmt.setString(1, bvo.getFolderName());
				pstmt.setString(2, bvo.getChannelName());
				pstmt.setString(3, bvo.getChannelURL());
				pstmt.executeUpdate();
				pstmt.close();
			}
			pstmt = con.prepareStatement("select board_recommend_seq.currval from dual");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pvo.setPostNo(rs.getString(1));
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
	}
	
	/**
	 * 
	 * @param postNo
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BookMarkVO> showUploadedChannelList(String postNo) throws SQLException {
		ArrayList<BookMarkVO> list = new ArrayList<BookMarkVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select folder_name, channel_name, channel_url from bookmark_board where post_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, postNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
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
}
