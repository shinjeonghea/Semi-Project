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
	 * 
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
	 * 
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
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BookMarkChannelVO> loadFolderList(String id) throws SQLException {
		ArrayList<BookMarkChannelVO> list = new ArrayList<BookMarkChannelVO>();
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
				BookMarkChannelVO bvo = new BookMarkChannelVO();
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
	public ArrayList<BookMarkChannelVO> loadChannelList(String folderName, String id) throws SQLException {
		ArrayList<BookMarkChannelVO> channelList = new ArrayList<BookMarkChannelVO>();
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
				BookMarkChannelVO bvo = new BookMarkChannelVO();
				bvo.setFolderName(rs.getString("folder_name"));
				bvo.setChannelName(rs.getString("channel_name"));
				bvo.setChannelURL(rs.getString("channel_url"));
				channelList.add(bvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}

		return channelList;
	}

	/**
	 * 게시물 포스팅 메서드
	 * 
	 * @param folderName
	 * @param pvo
	 * @throws SQLException
	 */
	public void posting(String folderName, PostVO pvo) throws SQLException {
		ArrayList<BookMarkChannelVO> list = new ArrayList<BookMarkChannelVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			// 수동 커밋 모드로 변경
			con.setAutoCommit(false);
			// BookMarkChannelVO 리스트 만들기 (채널 리스트임...)
			StringBuilder sql1 = new StringBuilder("select bf.folder_name, cm.channel_name, cm.channel_url ");
			sql1.append("from bookmark_folder bf, channel_member cm ");
			sql1.append("where bf.folder_no=cm.folder_no and bf.folder_name=? and bf.id=?");
			pstmt = con.prepareStatement(sql1.toString());
			pstmt.setString(1, folderName);
			pstmt.setString(2, pvo.getMvo().getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkChannelVO bvo = new BookMarkChannelVO();
				bvo.setFolderName(rs.getString("folder_name"));
				bvo.setChannelName(rs.getString("channel_name"));
				bvo.setChannelURL(rs.getString("channel_url"));
				list.add(bvo);
			}
			rs.close();
			pstmt.close();
			// br에 insert
			StringBuilder sql2 = new StringBuilder(
					"insert into board_recommend(post_no,title,content,time_posted,id) ");
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
				BookMarkChannelVO bvo = list.get(i);
				pstmt.setString(1, bvo.getFolderName());
				pstmt.setString(2, bvo.getChannelName());
				pstmt.setString(3, bvo.getChannelURL());
				pstmt.executeUpdate();
				pstmt.close();
			}

			// 3가지 작업이 모두 끝나면 커밋
			con.commit();

			pstmt = con.prepareStatement("select board_recommend_seq.currval from dual");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pvo.setPostNo(rs.getString(1));
			}
		} catch (Exception e) {
			con.rollback();
			System.out.println("게시물 등록 작업 중, 문제 발생하여 rollback");
			throw e; // 호출 측으로 Exception 정보를 전달하고자 할 때, throw를 이용해 다시 발생시키면 됨.
			// (catch로 잡았지만, ui츠게서도 알게하기위해서! throw로 또 발생시켜버림.)
			/*
			 * DAO 예외발생 -> DAO의 catch 수행 -> DAO의 캐치구문에서 throw e 발생 -> 메인에서 캐치
			 */
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
	public ArrayList<BookMarkChannelVO> showUploadedChannelList(String postNo) throws SQLException {
		ArrayList<BookMarkChannelVO> list = new ArrayList<BookMarkChannelVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select folder_name, channel_name, channel_url from bookmark_board where post_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, postNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkChannelVO bvo = new BookMarkChannelVO();
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

	/**
	 * 글번호에 해당하는 게시물을 삭제하는 메서드 트랜잭션 처리
	 * 
	 * @param no
	 * @throws SQLException
	 */
	public void deletePosting(int postNo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			// 수동 커밋 모드로 변경
			con.setAutoCommit(false);

			pstmt = con.prepareStatement("delete from bookmark_board where post_no=?");
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = con.prepareStatement("delete from board_recommend where post_no=?");
			pstmt.setInt(1, postNo);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			con.rollback();
			System.out.println("게시물 삭제 작업 중, 문제 발생하여 rollback");
			throw e;
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 게시물 정보 업데이트하는 메서드
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public void updatePosting(String folderName, PostVO pvo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		try {
			con = getConnection();

			pstmt = con.prepareStatement("update board_recommend set title=?,content=? where post_no=?");
			pstmt.setString(1, pvo.getTitle());
			pstmt.setString(2, pvo.getContent());
			pstmt.setString(3, pvo.getPostNo());
			pstmt.executeUpdate();
			pstmt.close();

		}finally {
			closeAll(pstmt, con);
		}
	}

	public String getFolderNameByPostNo(String postNo) throws SQLException {
		String folderName = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql = "select distinct folder_name from bookmark_board where post_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, postNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				folderName = rs.getString(1);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}

		return folderName;
	}
	
	/**
	 * 게시글 검색하기
	 * 제목, 내용
	 * @param title
	 * @param pagingBean
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getPostingListByOption(String searchOption, String keyword, PagingBean pagingBean) throws SQLException{
		ArrayList<PostVO> list=new ArrayList<PostVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select br.rnum, br.post_no, br.title, m.nick, br.time_posted, br.hits, br.content ");
			sql.append("from (select row_number() over(order by post_no desc) as rnum, id, post_no, ");
			sql.append("title, to_char(time_posted, 'yyyy-mm-dd') as time_posted, hits, content ");
			sql.append("from BOARD_RECOMMEND where ");
			sql.append(searchOption);
			sql.append(" like '%'||?||'%' ");
			sql.append(") br, member m ");
			sql.append("where br.id=m.id and ");
			sql.append(searchOption);
			sql.append(" like '%'||?||'%' ");
			sql.append("and rnum between ? and ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, pagingBean.getStartRowNumber());
			pstmt.setInt(4, pagingBean.getEndRowNumber());
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
	
	/**
	 * 게시글 검색하기
	 * 제목, 내용
	 * @param title
	 * @param pagingBean
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getPostingListByNick(String nick, String keyword, PagingBean pagingBean) throws SQLException{
		ArrayList<PostVO> list=new ArrayList<PostVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select br.rnum, br.post_no, br.title, m.nick, br.time_posted, br.hits, br.content ");
			sql.append("from (select row_number() over(order by post_no desc) as rnum, id, post_no, ");
			sql.append("title, to_char(time_posted, 'yyyy-mm-dd') as time_posted, hits, content ");
			sql.append("from BOARD_RECOMMEND ) br, member m ");
			sql.append("where br.id=m.id and ");
			sql.append(nick);
			sql.append(" like '%'||?||'%' ");
			sql.append("and rnum between ? and ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, pagingBean.getStartRowNumber());
			pstmt.setInt(3, pagingBean.getEndRowNumber());
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
		
	public ArrayList<PostVO> getPostingListByTitleAndContent(String title, String content, PagingBean pagingBean) throws SQLException{
		ArrayList<PostVO> list=new ArrayList<PostVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select br.post_no, br.rnum, br.title, m.nick, br.time_posted, br.hits, br.content ");
			sql.append("from (select row_number() over(order by post_no desc) as rnum, id, post_no, ");
			sql.append("title, to_char(time_posted, 'yyyy-mm-dd') as time_posted, hits, content ");
			sql.append("from BOARD_RECOMMEND where title like '%'||?||'%' ");
			sql.append("and content like '%'||?||'%' ) br, MEMBER m ");
			sql.append("where br.id=m.id and title like '%'||?||'%' and content like '%'||?||'%' ");
			sql.append("and rnum between ? and ?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setInt(5, pagingBean.getStartRowNumber());
			pstmt.setInt(6, pagingBean.getEndRowNumber());
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
	

	/**
	 * searchOption으로 을 골라~
	 * keyword로 검색했을 때 나오는 post의 개수
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPostCountByOption(String searchOption, String keyword) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) ");
			sql.append("from BOARD_RECOMMEND br, MEMBER m ");
			sql.append("where br.id=m.id and ");
			sql.append(searchOption);
			sql.append(" like '%'||?||'%'");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}
	public int getTotalPostCountByTitleAndContent(String keyword) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) ");
			sql.append("from ( ");
			sql.append("select row_number() over(order by post_no desc) as rnum, id, post_no, ");
			sql.append("title, to_char(time_posted, 'yyyy-mm-dd') as time_posted, hits, content ");
			sql.append("from BOARD_RECOMMEND where title like '%'||?||'%' ");
			sql.append("and content like '%'||?||'%' ");
			sql.append(" ) br, MEMBER m ");
			sql.append("where br.id=m.id and title like '%'||?||'%' and content like '%'||?||'%'");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			pstmt.setString(4, keyword);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}
}
