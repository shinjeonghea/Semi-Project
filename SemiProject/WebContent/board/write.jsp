<%@page import="org.kosta.avg.age.model.BookMarkChannelVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="flist" value="${requestScope.flist}"></c:set>

<form action="${pageContext.request.contextPath}/WritePostController.do"
	method="post">
	<table class="table">
		<tr>
			<td>제목 &nbsp;&nbsp; <input type="text" name="title"
				placeholder="게시글 제목을 입력하세요" required="required">
			</td>
		</tr>
		<tr>
			<td>추천할 폴더 &nbsp;&nbsp; <c:forEach items="${flist}"
					varStatus="status">
					<input type="radio" name="addFolder" required="required"
						value="${flist[status.index].folderName}"> ${flist[status.index].folderName} &nbsp;
    	</c:forEach>
			</td>
		</tr>
		<tr>
			<td><textarea cols="120" rows="15" name="content"
					required="required" placeholder="본문내용을 입력하세요"></textarea></td>
		</tr>
	</table>
	<div class="btnArea">
		<button type="submit" class="btn">확인</button>
		<button type="reset" class="btn">취소</button>
	</div>
</form>




