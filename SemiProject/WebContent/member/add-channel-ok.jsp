<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function close1(){
	window.opener.location.reload();
	window.close();
}
</script>
</head>
<body>
완료되었습니다.
<button onclick="close1()">창닫기</button>
</body>
</html>