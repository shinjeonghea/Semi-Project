 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
       <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
   <script type="text/javascript">
	function logout(){
		var f=confirm("로그아웃하시겠습니까?");
		if(f)
			location.href="${pageContext.request.contextPath}/LogoutController.do";
	}
	//유튜브 팝업창 띄우면서 검색하기
	function youtubeSearch(){
		//선택된 체크박스 값 가져오기		
		const checkedItem = document.querySelectorAll('input[type="checkbox"]:checked');
		let checkedValue = '';
		for(let i=0;i<checkedItem.length;i++){
			//마지막 채널에는 OR 붙이기
			if(i!=checkedItem.length-1){
				checkedValue = checkedValue + "\"" + checkedItem[i].value + "\""+ "OR" ;
			//마지막 채널에는 OR 안 붙이기
			}else if (i==checkedItem.length-1){
				checkedValue = checkedValue + "\"" + checkedItem[i].value + "\"";
			}
		}
		
		//검색창에 쓴 값 가져오기
		let searchKeyword = document.getElementById("searchKeyword").value;		
		
		//팝업 띄우면서 검색하기
		if(searchKeyword==""||searchKeyword==null){
			var url = "https://www.youtube.com/results?search_query="+ checkedValue ;
	        var name = "";
	        var option = "width = 1200, height = 800, top = 100, left = 200, location = no"
	        window.open(url, name, option);
		}else{
			var url = "https://www.youtube.com/results?search_query="+searchKeyword + "AND" + "(" + checkedValue + ")";
	        var name = "";
	        var option = "width = 1200, height = 800, top = 100, left = 200, location = no"
	        window.open(url, name, option);
		}
		

	}
</script>
                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Search -->
                    <form
                        class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                        <div class="input-group">
                            <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                                aria-label="Search" aria-describedby="basic-addon2" id="searchKeyword">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="button" onclick="youtubeSearch()">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>
                    </form>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">

                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <li class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-search fa-fw"></i>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                                aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small"
                                            placeholder="Search for..." aria-label="Search"  id="searchKeyword"
                                            aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button" onclick="youtubeSearch()">
                                                <i class="fas fa-search fa-sm"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>

                        <!-- Nav Item - Messages -->
                      
						
                        <div class="topbar-divider d-none d-sm-block"></div>
						
                        <!-- Nav Item - User Information -->

                        <li class="nav-item dropdown no-arrow">
                        <c:choose>
                        <c:when test="${sessionScope.mvo==null}">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">접속하기</span>
                                <img class="img-profile rounded-circle"
                                    src="img/undraw_profile.svg">
                            </a>
                            </c:when>
                            <c:otherwise>
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${sessionScope.mvo.nick} 님</span>
                                <img class="img-profile rounded-circle"
                                    src="img/undraw_profile.svg">
                            </a>
                            </c:otherwise>
                            </c:choose>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <c:choose>
                                <c:when test="${sessionScope.mvo==null}">
                                <a class="dropdown-item" href="member/login.jsp">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                   	로그인
                                </a>
                                <a class="dropdown-item" href="member/join.jsp">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    	회원가입
                                </a>
                                </c:when>
                                <c:otherwise>
                                <a class="dropdown-item" href="javascript:logout()">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                   로그아웃
                                </a>
                                </c:otherwise>
                                </c:choose>
                               </div>
                        </li>
                  
                    </ul>

                </nav>

