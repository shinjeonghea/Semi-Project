<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myhome.css">
<title>Insert title here</title>
<script type="text/javascript">
	function addChannel() {
		//선택된 체크박스 값 가져오기		
		let checked = document.querySelectorAll("input[type='checkbox']:checked");
		if(checked[0]==null){
			alert("채널을 선택해주세요~");
		}else{
		alert(checked[0].value);
		let select = document.getElementById("selectFolder");
		let selectFolder = select.options[select.selectedIndex].value;
		alert(selectFolder);
		document.addChannel1.submit();
		//opener.document.location.reload();
		//window.close();
		}
	}
</script>
</head>
<body>
   <br>

   <div class="container-fluid" align="center">
      <form action="${pageContext.request.contextPath}/YoutubeSearchController.do">
         <input type="text" id="search" name="search" placeholder="채널명을 입력해주세요">
         <input type="submit" value="검색하기">
      </form>
      <br>

      <c:set var="ylist" value="${requestScope.ylist }"></c:set>
      <%-- ${ylist} --%>
      <div>
         <c:choose>
            <c:when test="${ylist==null }">

            </c:when>
            <c:otherwise>

               <form action="AddChannelController.do" method="post" name="addChannel1">
                  어느 폴더에 추가하실 건가요~? <br>
                  <select name="folder" id="selectFolder" name="selectFolder">
                     <option value="">폴더 선택</option>
                     <c:forEach items="${sessionScope.flist }" varStatus="status">
                        <option value="${sessionScope.flist[status.index].folderName }">${sessionScope.flist[status.index].folderName }</option>
                     </c:forEach>
                  </select>
                  
                  
                
                  <input type="button" value="추가하기" onclick="addChannel()">
                  <br> <br>
                  <!--  찾으시는 채널이 안 나온다면<br> 조금 더 정확한 채널명을 입력해주세요~<br> -->
                  <br>
                  <c:forEach items="${ylist}" varStatus="status">
                     <table class="table">
                        <tr>
                           <td>
                              <input type="checkbox" name="checkChannel" value="${ylist[status.index]}">
                           </td>
                           <td>
                              <a href="${ylist[status.index].channelURL}">
                                 <img src="${ylist[status.index].thumbnailsURL}">
                              </a>
                           </td>
                           <td>
                              <a href="${ylist[status.index].channelURL}">${ylist[status.index].title}</a>
                           </td>
                        </tr>
                     </table>
                  </c:forEach>
               </form>
            </c:otherwise>
         </c:choose>
      </div>
      <br>
   </div>
</body>
</html>