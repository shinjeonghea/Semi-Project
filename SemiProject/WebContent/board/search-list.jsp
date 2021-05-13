<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
   function openWritePostForm(){
      document.WritePostForm.submit();
   }
</script>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">추천 게시판</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<table class="table table-bordered table-hover">
				<colgroup>
					<col width="10%" />
					<col width="35%" />
					<col width="25%" />
					<col width="25%" />
					<col width="5%" />
				</colgroup>
				<thead class="bg-gray-200">
					<tr>
						<!-- col : 5 개 -->
						<th>글번호</th>
						<th>제목</th>
						<!-- 분류는 카테고리 입니다. -->
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ requestScope.postList }" var="pvo">
					<tr>
						<td>${ pvo.postNo }</td>
						<td>
							<c:choose>
								<c:when test="${ sessionScope.mvo!=null }">
									<a
								href="${pageContext.request.contextPath}/PostDetailController.do?postNo=${ pvo.postNo }">${ pvo.title }</a>
								</c:when>
								<c:otherwise>
									${ pvo.title }
								</c:otherwise>
							</c:choose>
						</td>
						<td>${ pvo.mvo.nick }</td>
						<td>${ pvo.timePosted }</td>
						<td>${ pvo.hits }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			 <form name="WritePostForm" action="${pageContext.request.contextPath}/WritePostFormController.do" method="post">
				 <input type="hidden" name="WritePostForm" value="${requestScope.bvo.postNo}">
			 </form>
			 <c:if test="${ sessionScope.mvo!=null }">
			 	<button type="button" class="btn btn btn-outline-secondary btn-sm btn-primary text-white" style="float: right"
			 	 onclick="openWritePostForm()"><i class="fas fa-fw fa-pencil-alt"></i>글쓰기</button>
			 </c:if>
			 
			 <%-- 
			<button type="button" class="btn btn btn-outline-secondary btn-sm btn-primary text-white"
				style="float: right">
				<i class="fas fa-fw fa-pencil-alt"></i> 글쓰기
			</button>
			--%>
		</div>
		
		<%-- 게시글 검색 start (회원만 가능)--%>
		<c:if test="${ sessionScope.mvo!=null }">
		<div>
		<form name="searchform" method="post" action="${pageContext.request.contextPath}/SearchPostController.do">
		    <select name="searchOption">
		        <option value="title">제목</option>
		        <option value="content">내용</option>
		        <option value="titleAndContent">내용+제목</option>
		        <option value="nick">작성자</option>
		
		    </select>
		    <input type="text" name="keyword" value="${ param.keyword }" required="required">
		    <input type="submit" value="조회">
		</form>
		</div>
		</c:if>
		
		
		<%-- 페이징 start --%>
		<c:set var="pb" value="${requestScope.pagingBean}"></c:set>
		<div class="pagingArea">
			<ul class="pagination justify-content-center" style="margin-top: 20px">
				<c:if test="${pb.previousPageGroup}">
					<li class="page-item"><a class="page-link" href="SearchPostController.do?pageNo=${pb.startPageOfPageGroup-1}">&laquo;</a></li>
				</c:if>
				<c:forEach var="page" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
					<c:choose>
						<c:when test="${pb.nowPage==page}">
							<li class="active page-item"><a class="page-link" href="SearchPostController.do?pageNo=${page}">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="SearchPostController.do?pageNo=${page}">${page}</a></li>
						</c:otherwise>
					</c:choose>		
				</c:forEach>
				<c:if test="${pb.nextPageGroup}">
					<li class="page-item"><a class="page-link" href="SearchPostController.do?pageNo=${pb.endPageOfPageGroup+1}">&raquo;</a></li>
				</c:if>	
			</ul>
		</div>
		<%-- 페이징 end --%>
	</div>
</div>
