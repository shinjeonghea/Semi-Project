<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myhome.css">
<title>채널 추가</title>
<script type="text/javascript">
function close1(){
   //alert("Asdfasdf");
   window.opener.location.reload();
   window.close();
}
</script>
</head>
<body>

<div  align="center">
완료되었습니다.<br>
<input type="button" value="창닫기" class="btn btn-primary btn-xs"  onclick="close1()">
</div>
</body>
</html>