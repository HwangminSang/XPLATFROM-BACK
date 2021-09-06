<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <link rel="stylesheet" href="/css/board/css.css">
<title>Insert title here</title>
<style>
	span.msg,
	span.choose {
	  color: #555;
	  padding: 5px 0 10px;
	  display: inherit;
	}
	.container {
	  text-align: center;
	}
	.board_title{
		margin-bottom:0;
	}
	/*Styling Selectbox*/
	.dropdown {
	  width: 150px;
	  display: inline-block;
	  background-color: #fff;
	  border-radius: 2px;
	  box-shadow: 0 0 2px rgb(204, 204, 204);
	  transition: all .5s ease;
	  position: relative;
	  font-size: 14px;
	  color: #474747;
	  height: 100%;
	  text-align: left
	}
	.dropdown .select {
	    cursor: pointer;
	    display: block;
	    padding: 10px;
	}
	.dropdown .select > i {
	    font-size: 13px;
	    color: #888;
	    cursor: pointer;
	    transition: all .3s ease-in-out;
	    float: right;
	    line-height: 20px
	}
	.dropdown:hover {
	    box-shadow: 0 0 4px rgb(204, 204, 204)
	}
	.dropdown:active {
	    background-color: #f8f8f8
	}
	.dropdown.active:hover,
	.dropdown.active {
	    box-shadow: 0 0 4px rgb(204, 204, 204);
	    border-radius: 2px 2px 0 0;
	    background-color: #f8f8f8
	}
	.dropdown.active .select > i {
	    transform: rotate(-90deg)
	}
	.dropdown .dropdown-menu {
	    position: absolute;
	    background-color: #fff;
	    width: 100%;
	    left: 0;
	    margin-top: 1px;
	    box-shadow: 0 1px 2px rgb(204, 204, 204);
	    border-radius: 0 1px 2px 2px;
	    overflow: hidden;
	    display: none;
	    max-height: 144px;
	    overflow-y: auto;
	    z-index: 9
	}
	.dropdown .dropdown-menu li {
	    padding: 10px;
	    transition: all .2s ease-in-out;
	    cursor: pointer;
	    cololr:#005BA6;
	} 
	.dropdown .dropdown-menu {
	    padding: 0;
	    list-style: none
	}
	.dropdown .dropdown-menu li:hover {
	    background-color: #f2f2f2
	}
	.dropdown .dropdown-menu li:active {
	    background-color: #e2e2e2
	}
	.replySpan{
		color:red;
		margin-left:10px;
	}
</style>
<script>
	var boardData=[];
	var input="";
	//페이지 사이즈 지정
	let pageSize=3;
	//줄 사이즈 지정
	let rowSize;
	let dbCount;
	let currPage;
	let startPage;
	let endPage;
	let totalPage;
	$().ready(function(){
		getBoardList();
		dropDownFunc();
		
	})
	function getBoardList(){
		$.ajax({
			url:"/board/getBoardList",
			dataType:"json",
			type:'GET',
			success:function(obj){
				boardData = obj.json;
				console.log(boardData);
				dbCount=boardData.length;
				currPage=getParam("pageNum")==""?1:getParam("pageNum");
				rowSize=getParam("rowSize")==""?5:getParam("rowSize");
				if(getParam("rowSize")!=""){
					choice.innerHTML=rowSize;
				}
				currPage=Number(currPage);
				rowSize=Number(rowSize);
				totalPage=Math.ceil(dbCount/rowSize);
				pageSize=pageSize>totalPage?totalPage:pageSize;
				startPage=currPage-((currPage-1)%pageSize);
				pageCount=Math.floor((dbCount-1)/rowSize+1);
				endPage=startPage+pageSize-1;
				if(endPage>pageCount){
					endpage=pageCount;
				}
				const pageDiv = document.getElementById("boardPage");
				if(currPage!=1){
					const prevLink = document.createElement("a");
					prevLink.href="/board/board.html?pageNum="+(currPage-1)+"&rowSize="+rowSize;
					prevLink.classList.add("bt");
					prevLink.classList.add("prev");
					prevLink.innerHTML="<";
					pageDiv.appendChild(prevLink)
				}
				for(let i=startPage;i<=endPage;i++){
					const pageNumLink = document.createElement("a");
					pageNumLink.innerHTML=i;
					pageNumLink.href="/board/board.html?pageNum="+i+"&rowSize="+rowSize;;
					pageNumLink.classList.add("num");
					if(currPage==i){
						pageNumLink.classList.add("on");
					}
					pageDiv.appendChild(pageNumLink);
					if(i==totalPage){
						break;
					}
				}
				if(currPage!=totalPage){
					const nextLink = document.createElement("a");
					nextLink.href="/board/board.html?pageNum="+(currPage+1)+"&rowSize="+rowSize;
					nextLink.classList.add("bt");
					nextLink.classList.add("next");
					nextLink.innerHTML=">";
					pageDiv.appendChild(nextLink)
				}
				var rowStart=(currPage-1)*rowSize;
				var rowEnd = rowStart+rowSize;
				const parent = $("#boardList")[0];
				for(var i=rowStart;i<rowEnd;i++){
					if(i==dbCount){
						break;
					}
					const div = document.createElement("div");
					const divNum = document.createElement("div");
					divNum.classList.add("num");
					divNum.innerHTML=boardData[i].bno;
					const divTitle = document.createElement("div");
					const link=document.createElement("a");
					link.href="#"
					link.onclick=userCheckByHit;
					link.innerHTML=boardData[i].title;
					if(boardData[i].replyCount!=0){
						const span = document.createElement("span");
						span.classList.add("replySpan");
						span.innerHTML="["+boardData[i].replyCount+"]";
						link.appendChild(span);
					}
					link.value=i;
					divTitle.classList.add("title");
					const divWriter = document.createElement("div");
					divWriter.innerHTML=boardData[i].name
					divWriter.classList.add("writer");
					const divDate = document.createElement("div");
					divDate.classList.add("date");
					divDate.innerHTML=boardData[i].uploadDate;
					const divCount = document.createElement("div");
					divCount.classList.add("count");
					divCount.innerHTML=boardData[i].hit;

					divTitle.appendChild(link);
					div.appendChild(divNum);
					div.appendChild(divTitle);
					div.appendChild(divWriter);
					div.appendChild(divDate);
					div.appendChild(divCount);
					parent.appendChild(div);
				}
			}
		})
	}
	function dropDownFunc(){
		$('.dropdown').click(function () {
	        $(this).attr('tabindex', 1).focus();
	        $(this).toggleClass('active');
	        $(this).find('.dropdown-menu').slideToggle(300);
	    });
	    $('.dropdown').focusout(function () {
	        $(this).removeClass('active');
	        $(this).find('.dropdown-menu').slideUp(300);
	    });
	    $('.dropdown .dropdown-menu li').click(function () {
	        $(this).parents('.dropdown').find('span').text($(this).text());
	        $(this).parents('.dropdown').find('input').attr('value', $(this).attr('id'));
	    });
		$('.dropdown-menu li').click(function () {
		  rowSize=$(this).parents('.dropdown').find('input').val();
		  input = '<strong>' + $(this).parents('.dropdown').find('input').val() + '</strong>',
		      msg = '<span class="msg">input value: ';
		  $('.msg').html(msg + input + '</span>');
		  location.href="/board/board.html?pageNum="+currPage+"&rowSize="+rowSize;
		});
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
	function userCheckByHit(){
		if("${empName}"==boardData[this.value].name){
			location.href="/board/boardDetail.html?bno="+boardData[this.value].bno;
		}else{
			$.ajax({
				url:"/board/updateBoardHit",
				type:"POST",
				data:{
					bno:boardData[this.value].bno,
					hit:boardData[this.value].hit+1
				},
				success:function(obj){
					location.href="/board/boardDetail.html?bno="+obj.bno;
				}
			})
		}
	}
	
</script>
</head>
<body>
	<div class="board_wrap">
        <div class="board_title">
            <strong>공지사항</strong>
        </div>
        <div class="container">
		    <span class="choose">게시물 갯수를 선택해주세요</span>
		      <div class="dropdown">
		        <div class="select">
		          <span id="choice">갯수 선택</span>
		          <i class="fa fa-chevron-left"></i>
		        </div>
		        <input type="hidden" name="gender">
		        <ul class="dropdown-menu">
		        	<li id="5">5</li>
		        	<li id="8">8</li>
		        	<li id="10">10</li>
		        	<li id="15">15</li>
		        </ul>
		      </div>
		  
		  <span class="msg"></span>
		</div>

		<div class="board_list_wrap">
            <div class="board_list" id="boardList">
                <div class="top">
                    <div class="num">번호</div>
                    <div class="title">제목</div>
                    <div class="writer">글쓴이</div>
                    <div class="date">작성일</div>
                    <div class="count">조회</div>
                </div>
            </div>
            <div class="board_page" id="boardPage">
            </div>
            <div class="bt_wrap">
                <a href="/board/boardWrite.html" class="on">글쓰기</a>
            </div>
        </div>
    </div>
</body>
</html>