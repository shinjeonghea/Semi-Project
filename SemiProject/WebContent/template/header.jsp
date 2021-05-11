<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	//체크박스 전체 선택
	function checkAll(chk) {
		var chbx = radioGroup.elements;
		for (var i = 0; i < chbx.length; i++) {
			if (chbx[i].name == chk) {
				chbx[i].checked = true;
			} else {
				chbx[i].checked = false;
			}
		}
	}
</script>

<!-- Sidebar -->
<ul
	class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
	id="accordionSidebar">
	<!-- Sidebar - Brand -->
	<%-- href 속성 고민해보기 
      1. ${pageContext.request.contextPath}/index.jsp
      2. MainController.do
   --%>
	<a
		class="sidebar-brand d-flex align-items-center justify-content-center"
		href="MainController.do">
		<div class="sidebar-brand-icon rotate-n-15">
			<i class="fab fa-youtube"></i>
		</div>
		<div class="sidebar-brand-text mx-3">OurTube</div>
	</a>

	<!-- Divider -->
	<hr class="sidebar-divider my-0">


	<%--  Nav Item - 게시판 --%>
	<li class="nav-item active"><a class="nav-link"
		href="PostListController.do"> <i class="fas fa-chalkboard"></i> <span>게시판</span>
	</a></li>


	<%-- 즐겨찾기 폴더 추가 / 채널 추가 버튼 영역 --%>
	<%-- href 속성값으로 controller 명시하기! --%>
	<hr class="sidebar-divider">
	<div>
		<li class="nav-item"><a class="nav-link" href="#"><span>즐겨찾기
					폴더 추가</span> </a></li>
		<li class="nav-item"><a class="nav-link" href="#"><span>채널
					추가</span> </a></li>
		<li class="nav-item"><a class="nav-link" href="#"><span>즐겨찾기
					폴더 삭제</span> </a></li>
		<li class="nav-item"><a class="nav-link" href="#"><span>채널
					삭제</span> </a></li>
	</div>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<%-- 즐겨찾기 폴더 (for) --%>
	<div class="sidebar-heading">즐겨찾기 폴더</div>

	<!-- Nav Item - Utilities Collapse Menu -->
	<!-- 폴더 들고오는 foreach문 -->
	<form name="radioGroup">
		<input type='reset'
			class="btn btn btn-outline-secondary btn-sm btn-primary text-white"
			value="초기화">
		<c:forEach var="folderlist" items="${sessionScope.flist}"
			varStatus="status">
			<li class="nav-item">
				<div style="display: inline-block">
					<input type="radio" name="${folderlist.folderName}"
						onclick="checkAll(this.name)">
				</div> <%-- data-target에 id값 넣기! --%>
				<div style="display: inline-block">
					<a class="nav-link collapsed" href="#" data-toggle="collapse"
						data-target="#hello${status.index}" aria-expanded="true"
						aria-controls="#hello${status.index}"><span>${folderlist.folderName}</span>
					</a>
				</div> <%-- id값을 a태그의 data-target으로 넣어주기! --%>

				<div id="hello${status.index}" class="collapse"
					aria-labelledby="hello${status.index}"
					data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<c:forEach var="channellist" items="${sessionScope.clist}">
							<c:if test="${folderlist.folderName eq channellist.folderName}">
								<a class="collapse-item"><input type="checkbox"
									name="${folderlist.folderName}">
									${channellist.channelName}</a>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</li>
		</c:forEach>
	</form>


	<%-- 
   <!-- Divider -->
   <hr class="sidebar-divider">
   <!-- Heading -->
   <div class="sidebar-heading">Addons</div>
   <!-- Nav Item - Pages Collapse Menu -->
   
   <li class="nav-item"><a class="nav-link collapsed" href="#"
      data-toggle="collapse" data-target="#collapsePages"
      aria-expanded="true" aria-controls="collapsePages"> <i
         class="fas fa-fw fa-folder"></i> <span>Pages</span>
   </a>
      <div id="collapsePages" class="collapse"
         aria-labelledby="headingPages" data-parent="#accordionSidebar">
         <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Login Screens:</h6>
            <a class="collapse-item" href="login.html">Login</a> <a
               class="collapse-item" href="register.html">Register</a> <a
               class="collapse-item" href="forgot-password.html">Forgot
               Password</a>
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">Other Pages:</h6>
            <a class="collapse-item" href="404.html">404 Page</a> <a
               class="collapse-item" href="blank.html">Blank Page</a>
         </div>
      </div>
   </li>
    --%>

	<%-- 하단 < 버튼
   <hr class="sidebar-divider d-none d-md-block">
   <!-- Sidebar Toggler (Sidebar) -->
   <div class="text-center d-none d-md-inline">
      <button class="rounded-circle border-0" id="sidebarToggle"></button>
   </div>
    --%>

</ul>