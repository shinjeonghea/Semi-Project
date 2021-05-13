package org.kosta.avg.age.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class YoutubeDAO {
	private static YoutubeDAO dao = new YoutubeDAO();
	private DataSource dataSource;

	private YoutubeDAO() {
		dataSource = DataSourceManager.getInstance().getDataSource();
	}

	public static YoutubeDAO getInstance() {
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
	
	public String search(String search) throws IOException {
		// String search(String 검색창타입)
		
		String apiurl = "https://www.googleapis.com/youtube/v3/search"; // 검색경로이고 search부분에서 여러 api종류를 고를 수 있다
		apiurl += "?key=AIzaSyAcXPXTbiXzx6ntvCb5UVN6AZqOr0XkGmg"; // key=본인의 유튜브계정의 식별번호
		apiurl += "&part=snippet&type=channel&maxResults=5"; // type=채널, 영상, 플레이리스트 maxResult는 최대 가져오는 결과 값
		apiurl += "&q=" + URLEncoder.encode(search, "UTF-8"); // q=검색하려는 검색어를 query로 표현

		URL url = new URL(apiurl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();
		System.out.println(response.toString());
		return response.toString();
		

	}
}
