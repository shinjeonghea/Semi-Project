<%@page import="org.kosta.avg.age.model.BookMarkChannelVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="flist" value="${requestScope.flist}"></c:set>
<form
	action="${pageContext.request.contextPath}/UpdatePostController.do"
	method="post">

	<input type="hidden" name="postNo" value="${pvo.postNo}"></input>

	<table class="table">
		<tr>
			<td>제목 &nbsp;&nbsp; <input type="text" name="title"
				value="${ pvo.title }" required="required">
			</td>
		</tr>
		<tr>
			<td><h5>추천된 폴더 및 채널</h5> &nbsp;&nbsp;
				<div>${ requestScope.folderName } 폴더</div>
			 	<c:forEach
					items="${ requestScope.channelList }" var="ch">
					<div>${ ch.channelName }</div>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td><textarea cols="120" rows="15" name="content"
					required="required" placeholder="본문내용을 입력하세요">${ pvo.content }</textarea></td>
		</tr>
	</table>
	<div class="btnArea">
		<button type="submit" class="btn">확인</button>
		<button type="reset" class="btn">취소</button>
	</div>
</form>




