<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <link rel="stylesheet" href="/css/board/css.css">
<title>Insert title here</title>
<style>
	.label{
		font-weight:1000;
		background-color:gray;
		color:white;
		width:70px;
		border-radius:25px;
		text-align:center;
	}
	.fileInput{
		display:none;
	}
</style>
<script>
	$().ready(function(){
		clear();
		$("#name").val("${empName}");
	})
	function changeFileValue(obj) {
		olClear();
        console.log(obj.files);
        var files = obj.files;
        const list = document.getElementById("list");
        var cnt=1;
        for(var i=0;i<files.length;i++){
        	const li = document.createElement("li");
        	li.innerHTML=(cnt++)+"."+files[i].name;
        	list.appendChild(li);
        }
    }
	function olClear(){
        const list = document.getElementById("list");
        while(list.hasChildNodes()){
        	list.removeChild(list.firstChild);
        }
	}
	function registBoard(){
		var canRegist=new Boolean(true);
		var errorMsg = "";
		var title = $("#title").val();
		var password = $("#password").val();
		var content = $("#contents").val();
		if(title==""){
			errorMsg="제목을 입력하세요";
			canRegist=false;
		}else if(password==""){
			errorMsg="비밀번호를 입력하세요";
			canRegist=false;
		}
		if(canRegist==false){
	        Swal.fire("입력", errorMsg, "info");
	        return;
		}
		console.log(content);
		console.log(content.length);
        if(content.length>1000){
	        Swal.fire(content.length+"자 입력", "1000자 이내로 작성하여 주세요", "info");
	        return;
        }
		$("#frm").submit();
	}
	function clear(){
		$("#title").val("");
		$("#password").val("");
		$("#contents").val("");
		$("#files").val("");
	}
</script>
</head>

<body>
    <div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
            <p>공지사항을 등록해 주세요!</p>
        </div>
        <div class="board_write_wrap">
            <div class="board_write">
	    	<form id="frm" name="frm" method="POST" action="/board/insertBoard" enctype="multipart/form-data">
                <div class="title">
                    <dl>
                        <dt>제목</dt>
                        <dd><input id="title" name="title" type="text" placeholder="제목 입력"></dd>
                    </dl>
                </div>
                <div class="info">
                    <dl>
                        <dt>글쓴이</dt>
                        <dd><input id="name" name="name" type="text" placeholder="글쓴이 입력" readonly></dd>
                    </dl>
                    <dl>
                        <dt>비밀번호</dt>
                        <dd><input id="password" name="password" type="password" placeholder="비밀번호 입력"></dd>
                    </dl>
                </div>
                <div class="file">
                	<label class="label" for="files">파일선택</label>
                	<ol id="list">
                	</ol>
	    			<input type="file" id="files" name="file" multiple="multiple" onchange="changeFileValue(this)" class="fileInput"/>
                </div>
                <div class="cont">
                    <textarea id="contents" name="contents" placeholder="내용 입력"></textarea>
                </div>
            </form>
            </div>
            <div class="bt_wrap">
                <a href="javascript:void(0);" onclick="registBoard();" class="on">등록</a>
                <a href="/board/board.html">취소</a>
            </div>
        </div>
    </div>
</body>
</html>