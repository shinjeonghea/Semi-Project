package org.kosta.avg.age.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.kosta.avg.age.model.YoutubeDAO;
import org.kosta.avg.age.model.YoutubeVO;

public class YoutubeSearchController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String search = request.getParameter("search");
		String string = YoutubeDAO.getInstance().search(search); // 해당 유튜브에 대한 정보를 string에 저장
		System.out.println(string);

		JSONObject jsonObj = new JSONObject(string);
		ArrayList<YoutubeVO> ylist = new ArrayList<YoutubeVO>();
		// System.out.println(jsonObj.getJSONArray("items").length());
		for (int i = 0; i < jsonObj.getJSONArray("items").length(); i++) {
			String title = (String) jsonObj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").get("title");
			String channelId = (String) jsonObj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").get("channelId");
			String channelURL = "https://www.youtube.com/channel/" + channelId;
			String thumbnailsURL = (String) jsonObj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").get("url");
			// System.out.println(jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").get("title"));
			// System.out.println(jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").get("channelId"));
			// System.out.println(jsonObj.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").get("url"));
			YoutubeVO yvo = new YoutubeVO();
			yvo.setTitle(title);
			yvo.setChannelURL(channelURL);
			yvo.setThumbnailsURL(thumbnailsURL);
			ylist.add(yvo);
		}
		if (string == null) {
			request.setAttribute("responsebody", search + " 에 해당하는 정보가 없습니다");
		} else {
			request.setAttribute("responsebody", string);
		}
		//System.out.println("ylist;"+ylist);
		// Ajax 방식으로 응답하기 위해 AjaxView로 forwarding 한다
		//request.setAttribute("title", title);
		//request.setAttribute("channelURL", channelURL);
		//request.setAttribute("thumbnailsURL", thumbnailsURL);
		request.setAttribute("ylist", ylist);

		
		return "member/add-channel.jsp";

	}

}