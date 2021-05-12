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
		
		//return "{  \"kind\": \"youtube#searchListResponse\",  \"etag\": \"psk8TU10o21SP-ENvbkVvlMlG64\",  \"nextPageToken\": \"CAUQAA\",  \"regionCode\": \"KR\",  \"pageInfo\": {    \"totalResults\": 11063,    \"resultsPerPage\": 5  },  \"items\": [    {      \"kind\": \"youtube#searchResult\",      \"etag\": \"-GvenuvI0BUj1f-aCZ_-EskfbZ8\",      \"id\": {        \"kind\": \"youtube#channel\",        \"channelId\": \"UCF4Wxdo3inmxP-Y59wXDsFw\"      },      \"snippet\": {        \"publishedAt\": \"2006-11-05T21:58:51Z\",        \"channelId\": \"UCF4Wxdo3inmxP-Y59wXDsFw\",        \"title\": \"MBCNEWS\",        \"description\": \"MBC 뉴스 공식 유튜브 채널입니다. 시청자 여러분의 의견과 제보를 항상 기다립니다. 세상과 소통하는 시간, MBC 뉴스와 함께 하세요!\",        \"thumbnails\": {          \"default\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnir3d-5dZP3X_6iSyzMohHJFqqHOUV2rQJoe2YvbQ=s88-c-k-c0xffffffff-no-rj-mo\"          },          \"medium\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnir3d-5dZP3X_6iSyzMohHJFqqHOUV2rQJoe2YvbQ=s240-c-k-c0xffffffff-no-rj-mo\"          },          \"high\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnir3d-5dZP3X_6iSyzMohHJFqqHOUV2rQJoe2YvbQ=s800-c-k-c0xffffffff-no-rj-mo\"          }        },        \"channelTitle\": \"MBCNEWS\",        \"liveBroadcastContent\": \"live\",        \"publishTime\": \"2006-11-05T21:58:51Z\"      }    },    {      \"kind\": \"youtube#searchResult\",      \"etag\": \"sg2Q3MhAqdvm0CI-yU5-qTiWlVY\",      \"id\": {        \"kind\": \"youtube#channel\",        \"channelId\": \"UCiBr0bK06imaMbLc8sAEz0A\"      },      \"snippet\": {        \"publishedAt\": \"2009-07-30T00:42:50Z\",        \"channelId\": \"UCiBr0bK06imaMbLc8sAEz0A\",        \"title\": \"MBCentertainment\",        \"description\": \"Welcome to the official YouTube page of MBC, MBCentertainment A perfect channel full of entertainment and fun! Enjoy our exclusive content, Subscribe and ...\",        \"thumbnails\": {          \"default\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwni7qBszEiGBkcxnJbOKY6KN8sk39FWybDHkvceERQ=s88-c-k-c0xffffffff-no-rj-mo\"          },          \"medium\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwni7qBszEiGBkcxnJbOKY6KN8sk39FWybDHkvceERQ=s240-c-k-c0xffffffff-no-rj-mo\"          },          \"high\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwni7qBszEiGBkcxnJbOKY6KN8sk39FWybDHkvceERQ=s800-c-k-c0xffffffff-no-rj-mo\"          }        },        \"channelTitle\": \"MBCentertainment\",        \"liveBroadcastContent\": \"none\",        \"publishTime\": \"2009-07-30T00:42:50Z\"      }    },    {      \"kind\": \"youtube#searchResult\",      \"etag\": \"Dh3JuVuo4VpFFsSqy2bnlRm5DV4\",      \"id\": {        \"kind\": \"youtube#channel\",        \"channelId\": \"UCKhcMXQSFhY__0_SBSip3eA\"      },      \"snippet\": {        \"publishedAt\": \"2015-08-28T05:51:57Z\",        \"channelId\": \"UCKhcMXQSFhY__0_SBSip3eA\",        \"title\": \"여수MBC News+\",        \"description\": \"여수MBC 뉴스데스크/뉴스투데이/MBC토론 갑론을박 등 광주전남의 뉴스와 시사 프로그램을 제공하고 있습니다. 이제 언제 어디서나 여수MBC News+ 유튜브채널 ...\",        \"thumbnails\": {          \"default\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwngN_HUX_ZXpl2Xf3LgTTCEo_vdSZTuayJVhuvV3=s88-c-k-c0xffffffff-no-rj-mo\"          },          \"medium\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwngN_HUX_ZXpl2Xf3LgTTCEo_vdSZTuayJVhuvV3=s240-c-k-c0xffffffff-no-rj-mo\"          },          \"high\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwngN_HUX_ZXpl2Xf3LgTTCEo_vdSZTuayJVhuvV3=s800-c-k-c0xffffffff-no-rj-mo\"          }        },        \"channelTitle\": \"여수MBC News+\",        \"liveBroadcastContent\": \"upcoming\",        \"publishTime\": \"2015-08-28T05:51:57Z\"      }    },    {      \"kind\": \"youtube#searchResult\",      \"etag\": \"3QchLXIaUQOG5FvN3SI10IhqlfM\",      \"id\": {        \"kind\": \"youtube#channel\",        \"channelId\": \"UCKNZsAeQXpvI-Mpoc0ZKhsA\"      },      \"snippet\": {        \"publishedAt\": \"2018-02-22T08:01:55Z\",        \"channelId\": \"UCKNZsAeQXpvI-Mpoc0ZKhsA\",        \"title\": \"MBC Radio봉춘라디오\",        \"description\": \"라디오는 MBC   우리의 일상은 언제나 on air www.imbc.com/broad/radio www.facebook.com/radiombc www.instagram.com/radiombc www.twitter.com/radiombc.\",        \"thumbnails\": {          \"default\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnhLFsMUoLqzzKOcyGsKb1UF_WZqaJwm5ZOgMjW1_Q=s88-c-k-c0xffffffff-no-rj-mo\"          },          \"medium\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnhLFsMUoLqzzKOcyGsKb1UF_WZqaJwm5ZOgMjW1_Q=s240-c-k-c0xffffffff-no-rj-mo\"          },          \"high\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnhLFsMUoLqzzKOcyGsKb1UF_WZqaJwm5ZOgMjW1_Q=s800-c-k-c0xffffffff-no-rj-mo\"          }        },        \"channelTitle\": \"MBC Radio봉춘라디오\",        \"liveBroadcastContent\": \"live\",        \"publishTime\": \"2018-02-22T08:01:55Z\"      }    },    {      \"kind\": \"youtube#searchResult\",      \"etag\": \"s-0lJDynP3uoDRpBdFwl6r79R0E\",      \"id\": {        \"kind\": \"youtube#channel\",        \"channelId\": \"UCe52oeb7Xv_KaJsEzcKXJJg\"      },      \"snippet\": {        \"publishedAt\": \"2011-12-14T08:08:18Z\",        \"channelId\": \"UCe52oeb7Xv_KaJsEzcKXJJg\",        \"title\": \"MBCkpop\",        \"description\": \"Welcome to the official YouTube page of MBC, MBC Kpop Enjoy \\\"Show! Music Core\\\" the hottest K-pop program and the essence of live music on \\\"The Masked ...\",        \"thumbnails\": {          \"default\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnh8a4Y2P98vF8ndFf8mAcoRsKOCxWKp_LIe5hDPsA=s88-c-k-c0xffffffff-no-rj-mo\"          },          \"medium\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnh8a4Y2P98vF8ndFf8mAcoRsKOCxWKp_LIe5hDPsA=s240-c-k-c0xffffffff-no-rj-mo\"          },          \"high\": {            \"url\": \"https://yt3.ggpht.com/ytc/AAUvwnh8a4Y2P98vF8ndFf8mAcoRsKOCxWKp_LIe5hDPsA=s800-c-k-c0xffffffff-no-rj-mo\"          }        },        \"channelTitle\": \"MBCkpop\",        \"liveBroadcastContent\": \"none\",        \"publishTime\": \"2011-12-14T08:08:18Z\"      }    }  ]}";
	}
}
