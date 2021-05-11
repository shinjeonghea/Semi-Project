<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function deletePost() {
		if (confirm("게시글을 삭제하시겠습니까?")) {
			document.deleteForm.submit();
		}
	}
	function updatePost() {
		if (confirm("게시글을 수정하시겠습니까?")) {
			document.updateForm.submit();
		}
	}
</script>
<table class="table">
	<tr>
		<td>글번호 ${requestScope.pvo.postNo }</td>
		<td>제목: ${requestScope.pvo.title}</td>
		<td>작성자 : ${requestScope.pvo.mvo.nick }</td>
		<td>조회수 : ${requestScope.pvo.hits }</td>
		<td>${requestScope.pvo.timePosted }</td>
	</tr>
	<tr>
		<td colspan="5"><pre>${requestScope.pvo.content}</pre></td>
	</tr>
	<%-- <c:if test="${requestScope.pvo.memberVO.id==sessionScope.mvo.id}">
		<tr>
			<td colspan="5" class="btnArea">
				<form name="deleteForm"
					action="${pageContext.request.contextPath}/DeletePostController.do" method="post">
					<input 	type="hidden" name="no" value="${requestScope.pvo.no}">
				</form>				
				<form name="updateForm"
					action="${pageContext.request.contextPath}/UpdatePostFormController.do" method="post">
					<input 	type="hidden" name="no" value="${requestScope.pvo.no}">
				</form>
				<button type="button" class="btn" onclick="deletePost()">삭제</button>
				<button type="button" class="btn" onclick="updatePost()">수정</button>
			</td>
		</tr>
	</c:if> --%>
	
	
</table>

<br><br>
<hr>

<h5>추천 채널</h5>

<table>
	<c:forEach items="${ requestScope.channelList }" var="ch">
		<tr>
			<td>${ ch.channelName }</td>
		</tr>
	</c:forEach>
	<tr>
		<form action="" method="post">
			<input class="btn btn btn-outline-secondary btn-sm btn-primary text-white" style="float: right" type="submit" value="채널 가져오기">
		</form>
			 
	</tr>
</table>


<br><br><br>
<%-- 삭제 버튼 --%>
<c:if test="${ requestScope.pvo.mvo.id==sessionScope.mvo.id }">
		<form name="deleteForm"
				action="${pageContext.request.contextPath}/DeletePostController.do" method="post">
			<input 	type="hidden" name="postNo" value="${requestScope.pvo.postNo}">
		</form>	
		<button type="button" onclick="deletePost()" class="btn btn btn-outline-secondary btn-sm btn-primary text-white" style="float: right">글 삭제</button>
</c:if>





