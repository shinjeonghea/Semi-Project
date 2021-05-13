<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<<<<<<< HEAD
<meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <link href="css/styles.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="${pageContext.request.contextPath}/css/sb-admin-2.css" rel="stylesheet">
<!-- Custom styles for this page -->
<link href="vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

<title>Youtube Research</title>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

<c:import url="/template/header.jsp"></c:import>
 <div id="content-wrapper" class="d-flex flex-column">

<!-- Main Content -->
	<div id="content">
	<c:import url="/template/header-nav.jsp"></c:import>
	<div class="container-fluid">
	 
	</div>
	
	</div>
	</div>
	</div>
	</body>
	</html>
=======

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="레이아웃 JSP입니다.">
<meta name="author" content="avg.age.26">
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous">
<!-- Custom fonts for this template-->
<link
	href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- Custom styles for this template-->
<link href="${pageContext.request.contextPath}/css/sb-admin-2.css"
	rel="stylesheet">
<!-- Custom styles for this page -->
<link
	href="${pageContext.request.contextPath}/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
<title>Ourtube</title>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
	crossorigin="anonymous"></script>
<script
	src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script
	src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>
<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>
<!-- Page level plugins -->
<script
	src="${pageContext.request.contextPath}/vendor/chart.js/Chart.min.js"></script>
<!-- Page level custom scripts -->
<script
	src="${pageContext.request.contextPath}/js/demo/chart-area-demo.js"></script>
<script
	src="${pageContext.request.contextPath}/js/demo/chart-pie-demo.js"></script>
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
    	<%-- 헤더 import --%>
		<c:import url="/template/header.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<!-- Main Content -->
			<div id="content">
				<%-- 헤더 네비게이션 import --%>
				<c:import url="/template/header-nav.jsp"></c:import>
				<div class="container-fluid">
					<%-- 내용 import --%>
					<c:import url="${ url }"></c:import>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
>>>>>>> refs/remotes/origin/main
