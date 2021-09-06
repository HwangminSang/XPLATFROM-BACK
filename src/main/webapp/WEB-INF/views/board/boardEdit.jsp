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
	var bno="";
	var boardData={};
	var boardFiles=[];
	var removeFileList=[];
	$().ready(function(){
		removeFileList=[];
		bno=getParam("bno");
		$("#cancel")[0].href="/board/boardDetail.html?bno="+bno;
		getBoardDetailInfo();
	})
	
	function modifyBoard(){
		const password = $("#password").val();
		if(password==""){
	        Swal.fire("입력", "게시글 등록한 비밀번호를 입력해주세요", "info");
	        return;
		}
		if(password!=boardData.password){
	        Swal.fire("확인", "비밀번호가 다릅니다", "error");
	        return;
		}
		if($("#cont").val().length>1000){
	        Swal.fire("확인", "1000자가 넘습니다", "info");
	        return;
		}
		var form = $('#uploadForm')[0];
		console.log(form);
	    var formData = new FormData(form);
		formData.append("title",$("#title").val());
		formData.append("name",$("#writer").val());
		formData.append("contents",$("#cont").val());
		formData.append("bno",bno);
		formData.append("removeFiles",removeFileList);
		$.ajax({
			url:"/board/updateBoardDetail",
			enctype: 'multipart/form-data', 
			processData: false,    
	        contentType: false,      
	        cache: false,  
			data:formData,
			type:"POST",
			success:function(){
				location.href="/board/boardDetail.html?bno="+bno;
			}
		})
	}
	
	function getBoardDetailInfo(){
		$.ajax({
			url:"/board/getBoardDetailInfo",
			data:{	bno:bno
					},
			dataType:"json",
			success:function(obj){
				boardData=obj.json;
				$("#title").val(boardData.title);
				$("#writer").val(boardData.name);
				$("#cont").html(boardData.contents);
				boardFiles=obj.files;
				setFiles();
			}
		})
	}
	
	function getParam(sname) {
	    var params = location.search.substr(location.search.indexOf("?") + 1);
	    var sval = "";
	    params = params.split("&");
	    for (var i = 0; i < params.length; i++) {
	        temp = params[i].split("=");
	        if ([temp[0]] == sname) { sval = temp[1]; }
	    }
	    return sval;
	}
	function fileDownFunc(){
		var formObj=document.getElementById("files");
		$("#storedFileName").val(boardFiles[this.value].storedFileName);
		$("#orgFileName").val(boardFiles[this.value].orgFileName);
		formObj.action="/board/fileDown";
		formObj.submit();
	}
	function setFiles(){
		var size = boardFiles.length;
		const list = document.getElementById("list");
		for(var i=0;i<size;i++){
			const li = document.createElement("li");
			const link = document.createElement("a");
			const btn = document.createElement("button");
			btn.innerHTML="삭제";
			btn.value=boardFiles[i].fileNo;
			btn.classList.add("btn");
			btn.onclick=removeFunc;
			link.href="#";
			link.onclick=fileDownFunc;
			link.value=i;
			link.innerHTML=boardFiles[i].orgFileName+"("+boardFiles[i].fileSize+"kb)";
			li.appendChild(link);
			li.appendChild(btn);
			list.appendChild(li);
		}
	}
	function removeFunc(){
		const list = document.getElementById("list");
		list.removeChild(this.parentElement);
		console.log(this.parentElement.firstChild);
		removeFileList.push(this.value);
	}
	function changeFileValue(obj) {
		olClear();
        var files = obj.files;
        const list = document.getElementById("list2");
        var cnt=1;
        for(var i=0;i<files.length;i++){
        	const li = document.createElement("li");
        	li.innerHTML=(cnt++)+"."+files[i].name;
        	list.appendChild(li);
        }
    }
	function olClear(){
        const list = document.getElementById("list2");
        while(list.hasChildNodes()){
        	list.removeChild(list.firstChild);
        }
	}
</script>
</head>

<body>
<div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
            <p>공지사항 수정</p>
        </div>
        <div class="board_write_wrap">
            <div class="board_write">
                <div class="title">
                    <dl>
                        <dt>제목</dt>
                        <dd><input id="title" type="text" placeholder="제목 입력"></dd>
                    </dl>
                </div>
                <div class="info">
                    <dl>
                        <dt>글쓴이</dt>
                        <dd><input id="writer" type="text" placeholder="글쓴이 입력" readOnly></dd>
                    </dl>
                    <dl>
                        <dt>비밀번호</dt>
                        <dd><input id="password" type="password" placeholder="비밀번호 입력"></dd>
                    </dl>
                </div>
                <div class="fileList">
                	<dl>
                    	<div class="file">
		                	<form id="uploadForm">
			                	<label id="label" class="label" for="files">파일선택</label>
				    			<input type="file" id="files" name="file" multiple="multiple" onchange="changeFileValue(this)" class="fileInput"/>
			    			</form>
		                	<ol id="list">
		                	</ol>
		                </div>
						<dl class="fileDiv">
                    		<form id="files" role="form" method="post" >
                    			<input type="hidden" id="storedFileName" name="storedFileName"/>
                    			<input type="hidden" id="orgFileName" name="orgFileName"/>
                    		</form>
                    		<ol id="list2">
                    		</ol>
                    	</dl>
                    </dl>
                </div>
                
                <div class="cont">
                    <textarea id="cont" placeholder="내용 입력">
					</textarea>
                </div>
            </div>
            <div class="bt_wrap">
                <a id="modify" href="javascript:void(0);" onclick="modifyBoard();" class="on">수정</a>
                <a id="cancel">취소</a>
            </div>
        </div>
    </div>
</body>
</html>