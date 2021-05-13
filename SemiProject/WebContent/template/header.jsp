<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	//체크박스 전체 선택
	function checkAll(chk){
	   var chbx = radioGroup.elements;
	   for (var i=0;i<chbx.length ;i++ ){
	      if (chbx[i].name==chk){
	         chbx[i].checked = true ;
	      }else{
	          chbx[i].checked = false;
	      } 
	   }
	}
	//체크박스 전체 선택 2차 수정
	function selectAll(checkname){
		   const checkboxes = document.querySelectorAll('input[type="checkbox"]');
		   for(var i=0;i<checkboxes.length;i++){
			   if(checkname==checkboxes[i].value){
				   checkboxes[i].checked = true;
			   }else{
				   checkboxes[i].checked = false;
			   }
		   }
		 }
	function addFolder(form) {
		let addfolderName= document.getElementById("addfolderName");
		
		var arr=new Array();
		<c:forEach items="${sessionScope.flist}" var="folderNameList">
			arr.push("${folderNameList.folderName}");
		</c:forEach>
 		
  		for(let i=0;i<arr.length;i++){
			if(addfolderName.value==arr[i]){
				alert(addfolderName.value+" 폴더가 존재합니다.");
				document.getElementById("addfolderName").value=null;
				return false;
				
			}
		} 
  		alert(addfolderName.value+" 폴더가 추가되었습니다.");
	}
	
	function delFolder(form) {
		let delfolderName= document.getElementById("delfolderName");
		
		var arr=new Array();
		<c:forEach items="${sessionScope.flist}" var="folderNameList">
			arr.push("${folderNameList.folderName}");
		</c:forEach>
 		let j=0; 
  		for(let i=0;i<arr.length;i++){
			if(delfolderName.value==arr[i]){
				alert(delfolderName.value+" 폴더가 삭제되었습니다.");
				j+=1;
			}
		} 
 		if(j==0){
			alert(delfolderName.value+" 폴더가 없습니다.");
			return false;
 		}
 
	}
	
	function addChannel() {
		let url = "${pageContext.request.contextPath}/member/add-channel.jsp";
		let name = "addChannel";
		let specs = "width=450,height=600";
		var ret = window.open(url, name, specs);
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
   <a class="sidebar-brand d-flex align-items-center justify-content-center"
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
   <c:choose>
   <c:when test="${sessionScope.mvo!=null}">
	<li class="nav-item">
  	<%-- data-target에 id값 넣기! --%> <a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#hello" aria-expanded="true"
		aria-controls="#hello"> <i class="fas fa-fw fa-wrench"></i> <span>
				즐겨찾기폴더 </a> <%-- id값을 a태그의 data-target으로 넣어주기! --%>

		<div id="hello" class="collapse"
			aria-labelledby="hello${status.index}"
			data-parent="#accordionSidebar" >
				<form method="post"
				action="${pageContext.request.contextPath}/AddFolderController.do" onsubmit="return addFolder();">
				<input type="text" name="addfolderName" placeholder="폴더이름" size="10" id="addfolderName" required="required">
				<input type="submit" value="폴더추가" class="btn btn-primary btn-sm" >
			</form>
			<form method="post" 
				action="${pageContext.request.contextPath}/DeleteFolderController.do" onsubmit="return delFolder();">
				<input type="text" name="delfolderName" placeholder="폴더이름" size="10" id="delfolderName" required="required">
				<input type="submit" value="폴더삭제" class="btn btn-primary btn-sm" >
			</form>
		</div>

	</li>
	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collaps1" aria-expanded="true"
		aria-controls="collaps1"> <i class="fas fa-fw fa-wrench"></i> <span>채널관리</span>
	</a>
		<div id="collaps1" class="collapse" aria-labelledby="headingUtilities"
			data-parent="#accordionSidebar">
			
			
				<form>
					<input type="button"  class="btn btn-primary btn-sm" value="채널추가" onclick="addChannel()">
				</form>
		
		</div></li>
   
   <!-- Divider -->
   <hr class="sidebar-divider">
   
   <%-- 즐겨찾기 폴더 (for) --%>
   <div class="sidebar-heading">즐겨찾기 폴더</div>
   
   <!-- Nav Item - Utilities Collapse Menu -->
   <!-- 폴더 들고오는 foreach문 -->
   <div class="radioGroup">
   <input type='reset'class="btn btn btn-outline-secondary btn-sm btn-primary text-white"
					style="margin-left: 10px" value="초기화">
   <c:forEach var="folderlist" items="${sessionScope.flist}" varStatus="status">
   <li class="nav-item">
      <%-- data-target에 id값 넣기! --%>
      <div style="display:inline-block">
  
      <div style="vertical-align:middle">
     
      <a class="nav-link collapsed" href="#"
      <%-- folder 보여w주기 --%>
      data-toggle="collapse" data-target="#hello${status.index}" aria-expanded="true"
      aria-controls="#hello${status.index}"><span>${folderlist.folderName}</span>
   </a> 
   </div>
   </div>
    <%-- id값을 a태그의 data-target으로 넣어주기! --%>
   	
      <div id="hello${status.index}" class="collapse" aria-labelledby="hello${status.index}"
         data-parent="#accordionSidebar">
         <div class="bg-white py-2 collapse-inner rounded">
		 <a class="collapse-item" name="${folderlist.folderName}" href="javascript:void(0);" onClick="selectAll(this.name);" attr-a="onClick:attr-a">전체선택</a>
         <%-- channel보여주기--%>
         <c:forEach var="channellist" items="${sessionScope.clist}" >
   		 <c:if test="${folderlist.folderName eq channellist.folderName}">
           <form method="post"	action="${pageContext.request.contextPath}/DeleteChannelController.do">
				<a class="collapse-item"><input type="checkbox"
					name="${folderlist.folderName}" value="${folderlist.folderName}">${channellist.channelName}
				<input type="hidden" name="folderNo" value="${folderlist.folderNo}">
				<input type="hidden" name="channelName" value="${channellist.channelName}">
				<input type="submit" style="border-radius: 1rem; background-color: #ffffff;border:0;coutline:0;color:#4e73df; font-weight:bold;"  value="x"> </a> 
			</form>
         </c:if>
         </c:forEach>
         </div>
      </div>
   </li>
   </c:forEach>
 	</div>
   </c:when>
   <c:otherwise>
   	<div class="sidebar-heading">로그인 하셔야 <br>이용하실 수 있는 메뉴입니다.</div>
   </c:otherwise>
   </c:choose>
    
</ul>