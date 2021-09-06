<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <link rel="stylesheet" href="/css/board/css.css">
    <link rel="stylesheet" href="/css/board/reply.css">
<title>Insert title here</title>
<style>
	.aTag{
		cursor:pointer
	}
	.replyContainer{
		margin-top:10px;
		padding: 0 0 10px 0;
		display:flex;
		flex-direction:column;
	}
	.replyView{
		padding:20px;
		display:flex;
		flex-direction:row;
	}
	.replyImage{
		border:2px solid #9D9D9D;
		width:30px;
		height:30px;
		border-radius:50%;
		background-repeat:no-repeat;
		background-size: cover;
	}
	.content{
		border:2px solid #9D9D9D;
		width:60%;
		margin-left:10px;
		height:100%;
		padding:10px;
		position:relative;
	}
	.replyInfo{
		position:absolute;
		right:0;
		top:-10px;
		border:1px solid #FAFAFA;
		background-color:#FAFAFA;
		font-size:5px;
		height:20px;
	}
	.replyContent{
		word-break: break-all; 
		background-color:#FAFAFA;
		border:none;
	}
	.btnDiv2{
		position:absolute;
		bottom:-10px;
		border:1px solid #FAFAFA;
		padding:0px 10px;
		background-color:#FAFAFA;
		font-size:5px;
		height:20px;
	}
	.btnDiv{
		position:absolute;
		right:0;
		bottom:-10px;
		border:1px solid #FAFAFA;
		padding:0px 10px;
		background-color:#FAFAFA;
		font-size:5px;
		height:20px;
	}
	.replyUpBtn{
		margin-right:10px;
		margin-left:10px;
	}
	.replyDiv{
		margin-left:150px;
	}
	.replyViewCon{
	}
	.replyDelBtn{
		margin-right:10px;
	}
	.replyC{
		display:flex;
		flex-direction:row;
		margin-bottom:10px;
	}
	.replyInfo2{
		position:relative;
		margin-left:10px;
	}
	.replyT{
		background-color:#FAFAFA;
		width:300px;
	}
</style>
<script>
	var bno="";
	var boardData=[];
	var boardFiles=[];
	var form;
	var replyList=[];
	//board에서 a link 로 넘어올 때 url을 controller에서 받아서 세팅값들 얻어 attribute에 저장하고 jstl로 받아오면 코드가 훨씬 짧아짐
	$().ready(function(){
		bno=getParam("bno");
		getBoardDetailInfo();
		$("#replyBtn").on("click",registReply);
		passwordEventFunc();
		form = $("#replyForm")[0];
		form.reset();
		getReplyList();
	})
	var cnt=0;
	//대댓글
	function replyBtnClickFunc(){
		var replyDiv = this.parentNode.parentNode.parentNode.nextSibling;
		
		//replyDiv.appendChild(reReply);
		var replyForm = replyDiv.lastChild;
		if(replyForm.value=="not"){
			replyForm.value="do";
			replyForm.style.display=""
		}else{
			replyForm.value="not";
			replyForm.style.display="none"
		}
	}
	function getReplyList(){
		$.ajax({
			url:"/board/getReplyList",
			type:"GET",
			data:{bno:bno},
			success:function(obj){
				var replyContainer = document.getElementById("replyContainer");
				console.log(obj);
				replyList=obj.list;
				for(var i=0;i<replyList.length;i++){
					if(replyList[i].depth==0){
						var replyViewCon = document.createElement("div");
						replyViewCon.classList.add("replyViewCon");
						var replyView=document.createElement("div");
						replyView.classList.add("replyView");
						var replyImage = document.createElement("div");
						replyImage.classList.add("replyImage");
						if(replyList[i].image==""){
							replyImage.style.backgroundImage="url('/img/anony.png')";
						}else{
							replyImage.style.backgroundImage="url('"+replyList[i].image+"')";
							
						}
						var time=replyList[i].uploadDate.substr(0,16);
						var content = document.createElement("div");
						content.classList.add("content");
						var replyInfo = document.createElement("div");
						replyInfo.classList.add("replyInfo");
						var replyContent = document.createElement("textarea");
						replyContent.classList.add("replyContent");
						replyInfo.innerHTML=time+"  "+replyList[i].writer;
						replyContent.innerHTML = replyList[i].content;
						
							var btnDiv = document.createElement("div");
							var replyBtn = document.createElement("a");
							replyBtn.innerHTML="답글"
							replyBtn.onclick=replyBtnClickFunc
							replyBtn.href="#";
							btnDiv.classList.add("btnDiv");
							if(replyList[i].empCode=="${empCode}"){
								var replyUpBtn = document.createElement("a");
								replyUpBtn.href = "#";
								replyUpBtn.innerHTML = "수정";
								replyUpBtn.onclick=replyUpdateFunc;
								replyUpBtn.value=replyList[i].rno;
								replyUpBtn.classList.add("replyUpBtn");
								var replyDelBtn = document.createElement("a");
								replyDelBtn.href="#"
								replyDelBtn.onclick=replyDeleteFunc;
								replyDelBtn.innerHTML = "삭제";
								replyDelBtn.value=replyList[i].rno;
								replyDelBtn.classList.add("replyDelBtn");
								btnDiv.appendChild(replyUpBtn);
								btnDiv.appendChild(replyDelBtn);
							}
							var btnDiv2=document.createElement("div");
							btnDiv2.classList.add("btnDiv2");
							var notSeeing = document.createElement("a");
							notSeeing.href="#"
							notSeeing.onclick=notSeeingFunc;
							notSeeing.innerHTML="답글 안보기";
							notSeeing.value="do";
							
							btnDiv.appendChild(replyBtn);
							btnDiv2.appendChild(notSeeing);
							content.appendChild(btnDiv);
							content.appendChild(btnDiv2);
						var replyDiv = document.createElement("div");
						var replyForm = document.createElement("div");
						var replyText = document.createElement("input");
						var replyButton = document.createElement("button");
						replyText.type="text";
						replyText.size="40";
						replyText.id="replyText";
						replyButton.innerHTML="답변달기";
						replyButton.onclick=registReReply;
						replyButton.value=replyList[i].rno;
						replyForm.style.display="none";
						replyForm.classList.add("replyForm");
						replyForm.value="not";
						replyForm.appendChild(replyText);
						replyForm.appendChild(replyButton);
					}else if(replyList[i].depth==1){
						var replyC =document.createElement("div");
						replyC.classList.add("replyC");
						var replyInfoC = document.createElement("div");
						var replyI = document.createElement("div");
						var replyT = document.createElement("textarea");
						replyT.classList.add("replyT");
						if(replyList[i].image==""){
							replyI.style.backgroundImage="url('/img/anony.png')";
						}else{
							replyI.style.backgroundImage="url('"+replyList[i].image+"')";
							
						}
						var replyIn = document.createElement("div");
						replyIn.classList.add("replyInfo");
						replyIn.innerHTML=time+"  "+replyList[i].writer;
						
						
						var replyInfo2 = document.createElement("div");
						replyInfo2.classList.add("replyInfo2");
						replyT.innerHTML=replyList[i].content;
						replyI.classList.add("replyImage");
						replyInfo2.appendChild(replyIn);
						replyInfo2.appendChild(replyT);
						replyC.appendChild(replyI);
						replyC.appendChild(replyInfo2);
						replyDiv.appendChild(replyC);
					}
					//여기전에 댓글들 불러와야됨
					replyDiv.appendChild(replyForm);
					replyDiv.classList.add("replyDiv");
					content.appendChild(replyInfo);
					content.appendChild(replyContent);
					replyView.appendChild(replyImage);
					replyView.appendChild(content);
					replyViewCon.appendChild(replyView);
					replyViewCon.appendChild(replyDiv);
					replyContainer.appendChild(replyViewCon);
					}
			}
		})
	}
	function registReReply(){
		if(this.parentNode.firstChild.value==""){
			alert("내용이 없습니다.");
			return;
		}
		if($("#replyText").val().length>100){
			alert("글자수가 너무 많습니다.");
			return;
		}
		//password구현 귀찮
		$.ajax({
			url:"/board/replyReInsert",
			data:{
				bno:bno,
				rno:this.value,
				depth:1,
				parentNo:this.value,
				content:this.parentNode.firstChild.value,
				writer:"${empName}",
				empCode:"${empCode}",
				password:"1",
				image:"${image}"
			},
			type:"POST",
			success:function(){
				location.reload();
			}
		})
	}
	function notSeeingFunc(){
		console.log(this.parentNode.parentNode.parentNode.nextSibling);
		var replyDiv=this.parentNode.parentNode.parentNode.nextSibling;
		if(this.value=="do"){
			this.value="not";
			this.innerHTML="답글 보기";
			replyDiv.style.display="none";
		}else{
			this.value="do";
			this.innerHTML="답글 안보기";
			replyDiv.style.display="";
		}
	}
	function replyUpdateFunc(){
		$.ajax({
			url:"/board/replyUpdate",
			data:{bno:bno,rno:this.value,content:this.parentNode.parentNode.lastChild.value},
			type:"POST",
			success:function(){
				location.reload();
			}
		}) 
	}
	function replyDeleteFunc(){
		var result = confirm("정말 해당 댓글을 삭제하시겠습니까?");
		//비밀번호 검사받아도 되는데 수정 삭제 자체를 세션값과 비교해서 나타나게 했고 귀찮기도 함
		if(result==true){
			$.ajax({
				url:"/board/replyDelete",
				data:{bno:bno,rno:this.value},
				type:"POST",
				success:function(){
					location.reload();
				}
			})
		}
	}
	function registReply(){
		var replyPassword=$("#passInput").val();
		if(replyPassword==""){
	        Swal.fire("입력", "댓글 비밀번호를 입력해주세요", "info");
			return;
		}
		$("#replyWriter").val("${empName}");
		$("#replyBno").val(bno);
		$("#replyEmpCode").val("${empCode}");
		$("#parentNo").val(bno);
		$("#depth").val(0);
		$("#image").val("${image}");
		form.submit();
	}
	
	function passwordEventFunc(){
		const PassBtn = document.querySelector('#passBtn');
		PassBtn.addEventListener('click', () => {
		    const input = document.querySelector('#passInput');
		    input.getAttribute('type') === 'password' ? input.setAttribute('type', 'text') : input.setAttribute('type', 'password');
		    if (passBtn.classList.contains('fa-eye')) {
		    	PassBtn.classList.toggle('fa-eye-slash');
		    	PassBtn.classList.toggle('fa-eye');
			} else {
				PassBtn.classList.toggle('fa-eye');
				PassBtn.classList.toggle('fa-eye-slash');
			}
		}); 
	}
	
	function visibleMD(){
		if("${empName}"!=boardData.name){
			console.log(boardData.name);
			$("#modify").css("display","none");
			$("#delete").css("display","none");
		}
	}
	function modifyBoard(){
		if("${empName}"!=boardData.name){
	        Swal.fire("불가", "해당 게시물의 당사자만 수정 가능합니다.", "error");
	        return;
		}
		var value = prompt("해당 게시물의 비밀번호를 입력해주세요");
		if(value==null){
			return;
		}
		if(value==boardData.password){
			location.href="/board/boardEdit.html?bno="+bno;
		}else{
	        Swal.fire("틀림", "비밀번호가 다릅니다", "error");
	        return;
		}
	}
	function getBoardDetailInfo(){
		$.ajax({
			url:"/board/getBoardDetailInfo",
			data:{bno:bno},
			dataType:"json",
			success:function(obj){
				boardData=obj.json;
				$("#title").html(boardData.title);
				$("#num").html(boardData.bno);
				$("#writer").html(boardData.name);
				$("#count").html(boardData.hit);
				$("#date").html(boardData.uploadDate);
				$("#cont").html(boardData.contents);
				boardFiles=obj.files;
				console.log(obj);
				setFiles();
				visibleMD();
			}
		})
	}
	function fileDownFunc(){
		var formObj=document.getElementById("files");
		$("#storedFileName").val(boardFiles[this.value].storedFileName);
		$("#orgFileName").val(boardFiles[this.value].orgFileName);
		formObj.submit();
	}
	function setFiles(){
		var size = boardFiles.length;
		const filesDiv = document.getElementById("files");
		for(var i=0;i<size;i++){
			const dd = document.createElement("dd");
			const link = document.createElement("a");
			link.href="#";
			link.onclick=fileDownFunc;
			link.value=i;
			link.innerHTML=boardFiles[i].orgFileName+"("+boardFiles[i].fileSize+"kb)";
			dd.appendChild(link);
			filesDiv.appendChild(dd);
		}
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
	function deleteBoard(){
		if("${empName}"!=boardData.name){
	        Swal.fire("불가", "해당 게시물의 당사자만 삭제 가능합니다.", "error");
	        return;
		}
		var passwordInput = prompt("해당 게시물의 비밀번호를 입력해주세요");
		if(passwordInput==null){
			return;
		}
		if(passwordInput!=boardData.password){
	        Swal.fire("오류", "비밀번호가 다릅니다!", "error");
	        return;
		}
		$.ajax({
			url:"/board/deleteBoard",
			data:{bno:bno},
			type:"POST",
			success:function(){
				location.href="/board/board.html";
			}
		})
	}
</script>
</head>
<body>
    <div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
            <p>공지사항 상세정보</p>
        </div>
        <div class="board_view_wrap">
            <div class="board_view">
                <div class="title" id="title">
                </div>
                <div class="info">
                    <dl>
                        <dt>번호</dt>
                        <dd id="num">1</dd>
                    </dl>
                    <dl>
                        <dt>글쓴이</dt>
                        <dd id="writer"></dd>
                    </dl>
                    <dl>
                        <dt>작성일</dt>
                        <dd id="date"></dd>
                    </dl>
                    <dl>
                        <dt>조회</dt>
                        <dd id="count"></dd>
                    </dl>
                    <dl>
                    	<dt class="fileListTitle">파일목록</dt>
						<dl class="images">
                    		<form id="files" role="form" action="/board/fileDown" method="post" >
                    			<input type="hidden" id="storedFileName" name="storedFileName"/>
                    			<input type="hidden" id="orgFileName" name="orgFileName"/>
                    		</form>
                    	</dl>
                    </dl>
                </div>
                <div class="cont" id="cont">
                </div>
            </div>
            <div class="replyContainer" id="replyContainer">
            </div>
            </div>
            <form id="replyForm" method="POST" action="/board/insertReply">
			<div class="ui-input-container">
				<label class="ui-form-input-container">
				 <textarea class="ui-form-input" placeholder="댓글을 작성해주세요" name="content"></textarea>
					<span class="form-input-label">Message</span>
				</label>
		    </div>
		    <div class="form-wrapper">
				<input name="password" id="passInput" class="form-control replyPassword" placeholder="Your Password" name="password" type="password" size="30" aria-required="false">
				<i id="passBtn" class="fa fa-eye fa-fw eye" aria-hidden="true"></i>
			</div>
			<input type="button" class="replyBtn" id="replyBtn" value="댓글달기"/>
			<input type="hidden" name="writer" id="replyWriter"/>
			<input type="hidden" name="bno" id="replyBno"/>
			<input type="hidden" name="empCode" id="replyEmpCode"/>
			<input type="hidden" name="parentNo" id="parentNo"/>
			<input type="hidden" name="depth" id="depth"/>
			<input type="hidden" name="image" id="image"/>
			</form>
            <div class="bt_wrap">
                <a href="javascript:void(0);" onclick="modifyBoard();" id="modify" class="aTag">수정</a>
                <a href="/board/board.html" class="on">목록</a>
                <a href="javascript:void(0);" onclick="deleteBoard();" id="delete">삭제</a>
            </div>
        </div>
    </div>
</body>
</html>