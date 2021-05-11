<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
     <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">

</head>
<script type="text/javascript">
let xhr;
function joinCheckFunction() {
	//사용자 입력한 id를 받아온다 
	let mid=document.getElementById("id").value;
	if(mid==""){
		alert("아이디를 입력하세요!");
		return;//함수 실행을 중단한다 
	}
	
	xhr=new XMLHttpRequest();//Ajax 통신을 위한 자바스크립트 객체 
	//alert(xhr);
	//XMLHttpRequest의 속성에 callback 함수를 바인딩
	//readystate가 변화될 때 callback 함수가 실행 
	//서버가 응답할 때 callback 함수를 실행하기 위한 코드이다 
	xhr.onreadystatechange=callback; 
	//서버로 요청 
	xhr.open("GET","${pageContext.request.contextPath}/JoinCheckController.do?id="+mid);
	xhr.send(null); //post 방식일때 form data 명시 
}
function callback(){
	//console.log(xhr.readyState);
	// readyState 가 4 : 서버의 응답 정보를 받은 상태 
	// status 가 200 : 정상 수행 
	if(xhr.readyState==4&&xhr.status==200){
		//xhr.responseText : 서버의 응답데이터를 저장하는 변수 
		if(xhr.responseText==0){
			alert("중복된 아이디입니다.");
		}else if(xhr.responseText==1){
			alert("사용가능한 아이디입니다.");
		}
	}
}
</script>

<body class="bg-gradient-info">

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5">
                   
                        <!-- Nested Row within Card Body -->
                      
                         
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">OurTube</h1>
                                    </div>
                                    <form class="user" method="post" action="${pageContext.request.contextPath}/JoinController.do">
                              
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user"
                                                id="id" aria-describedby="emailHelp"
                                                placeholder="id" name="id" >
                                        <button type="button" onclick="joinCheckFunction()">중복체크</button>
                                        </div>
                                        
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="exampleInputPassword" placeholder="Password" name="password">
                                        </div>
                                         <div class="form-group">
                                            <input type="text" class="form-control form-control-user"
                                                id="exampleInputPassword" placeholder="Nickname" name="nickname">
                                        </div>
                                        <input type="submit" class="btn btn-primary btn-user btn-block"  value="Sign up">
                                      
                                    </form>
                                
                                </div>
                            </div>
                       
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

</body>

</html>