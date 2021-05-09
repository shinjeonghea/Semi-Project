<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<form
		action="${pageContext.request.contextPath}/DeleteFolderController.do?folderName=요리">
		<input type="submit" value="폴더삭제하기">
	</form>
	<form
		action="${pageContext.request.contextPath}/AddFolderController.do">
		<input type="submit" value="폴더추가하기">
	</form>
	<form
		action="${pageContext.request.contextPath}/UpdateFolderNameController.do?">
		<input type="submit" value="폴더수정하기">
	</form>
	<form
		action="${pageContext.request.contextPath}/AddChannelController.do?">
		<input type="submit" value="채널추가하기">
	</form>
	<form
		action="${pageContext.request.contextPath}/DeleteChannelController.do?">
		<input type="submit" value="채널삭제하기">
	</form>
</body>
</html>