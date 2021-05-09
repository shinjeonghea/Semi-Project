<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
						<td><a
								href="${pageContext.request.contextPath}/PostDetailController.do?postNo=${ pvo.postNo }">${ pvo.title }</a></td>
						<td>${ pvo.mvo.nick }</td>
						<td>${ pvo.timePosted }</td>
						<td>${ pvo.hits }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<button type="button" class="btn btn btn-outline-secondary btn-sm btn-primary text-white" 
				style="float: right">
				<i class="fas fa-fw fa-pencil-alt"></i> 글쓰기
			</button>
		</div>
		<%-- 페이징 --%>
		<%-- 
		<ul class="pagination justify-content-center" style="margin-top: 50px">
			<li class="page-item"><a class="page-link" href="#">Previous</a></li>
			<li class="page-item"><a class="page-link" href="#">1</a></li>
			<li class="page-item active"><a class="page-link" href="#">2</a></li>
			<li class="page-item"><a class="page-link" href="#">3</a></li>
			<li class="page-item"><a class="page-link" href="#">Next</a></li>
		</ul>
		 --%>
		<%-- /페이징 end --%>
		
		<c:set var="pb" value="${requestScope.pagingBean}"></c:set>
		<div class="pagingArea">
			<ul class="pagination justify-content-center" style="margin-top: 20px">
				<c:if test="${pb.previousPageGroup}">
					<li class="page-item"><a class="page-link" href="PostListController.do?pageNo=${pb.startPageOfPageGroup-1}">&laquo;</a></li>
				</c:if>
				<c:forEach var="page" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
					<c:choose>
						<c:when test="${pb.nowPage==page}">
							<li class="active page-item"><a class="page-link" href="PostListController.do?pageNo=${page}">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="PostListController.do?pageNo=${page}">${page}</a></li>
						</c:otherwise>
					</c:choose>		
				</c:forEach>
				<c:if test="${pb.nextPageGroup}">
					<li class="page-item"><a class="page-link" href="PostListController.do?pageNo=${pb.endPageOfPageGroup+1}">&raquo;</a></li>
				</c:if>	
			</ul>
		</div>
	</div>
</div>
