<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>Title</title>
<style>
.mypage{text-align:right; margin-top:20px; font-size:12px;}
.mypage .ezr_num_records{color:#337ab7; font-size:12px;}
.mypage .ezr_nav_na{display:inline-block; padding:0 5px; margin:0 1px; text-align:center; min-width:24px; height:24px; line-height:24px; font-size:12px; color:#fff; background:#ccc; vertical-align:middle; border-radius:2px; cursor:default;}
.mypage a{display:inline-block; padding:0 5px; margin:0 1px; text-align:center; min-width:24px; height:24px; line-height:24px; font-size:12px; color:#fff; vertical-align:middle; border-radius:2px; text-decoration:none;}
.mypage .ezr_nav{background:#589ad3; -webkit-transition:all 0.3s ease-out; transition:all 0.3s ease-out;}
.mypage .ezr_nav:hover{background:#337ab7;}
.mypage .ezr_first_page, .mypage .ezr_back, .mypage .ezr_next, .mypage .ezr_last_page{background:#337ab7;}
.mypage .ezr_back_section, .mypage .ezr_next_section{padding:0; min-width:18px; background:url("data:image/svg+xml;charset=utf-8,%3Csvg%20viewBox%3D%220%200%201024%201024%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20d%3D%22M646.31808%20515.54304A75.22304%2075.22304%200%200%201%20721.55136%20440.32a75.22304%2075.22304%200%200%201%2075.23328%2075.22304%2075.23328%2075.23328%200%200%201-75.23328%2075.23328%2075.23328%2075.23328%200%200%201-75.23328-75.23328z%20m-209.54112%200A75.22304%2075.22304%200%200%201%20512.01024%20440.32a75.2128%2075.2128%200%200%201%2075.22304%2075.22304%2075.22304%2075.22304%200%200%201-75.22304%2075.23328%2075.23328%2075.23328%200%200%201-75.23328-75.23328z%20m-209.54112%200A75.2128%2075.2128%200%200%201%20302.45888%20440.32a75.22304%2075.22304%200%200%201%2075.23328%2075.22304%2075.23328%2075.23328%200%200%201-75.23328%2075.23328%2075.22304%2075.22304%200%200%201-75.23328-75.23328z%22%20fill%3D%22%23999999%22%3E%3C%2Fpath%3E%3C%2Fsvg%3E") no-repeat center center; background-size:18px 18px;}
.mypage .ezr_back_section:hover, .mypage .ezr_next_section:hover{background-image:url("data:image/svg+xml;charset=utf-8,%3Csvg%20viewBox%3D%220%200%201024%201024%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20d%3D%22M840.5%20750.059c14.617%2014.617%2014.617%2038.29%200%2052.90699999-14.616%2014.616-38.326%2014.616-52.943%201e-8l-264.751-264.513c-14.615-14.598-14.615-38.29%200-52.906l264.75-264.512c14.617-14.61599999%2038.327-14.616%2052.943%200%2014.617%2014.61599999%2014.617%2038.29%200%2052.906L602.22%20512%20840.5%20750.059z%20m-339.306%200c14.61599999%2014.617%2014.616%2038.29%200%2052.907-14.616%2014.616-38.327%2014.616-52.943%200L183.5%20538.453c-14.616-14.598-14.616-38.29%200-52.906L448.251%20221.035c14.616-14.616%2038.327-14.616%2052.943%200s14.616%2038.29%200%2052.906L262.914%20512l238.28%20238.059z%22%20fill%3D%22%23f59942%22%3E%3C%2Fpath%3E%3C%2Fsvg%3E");}
.mypage .ezr_next_section{-webkit-transform:rotateY(180deg); transform:rotateY(180deg);}
</style>
</head>

<body>
<table>
	<tr>
		<th>ID</th>
		<th>标题</th>
		<th>点击数</th>
	</tr>
	<%--List<Article> articles = (List<Article>)request.getAttribute("rs");
	for(Article g:articles){--%>
	<c:forEach items="${rs}" var="g">
	<tr>
		<td>${g.id}</td>
		<td>${g.title}</td>
		<td>${g.clicks}</td>
	</tr>
	</c:forEach>
</table>
<div class="mypage">
	共 ${page.getRecords(true)} 个记录 ${page.getCurrentPage()} / ${page.getNumPages(true)} 页 ${page.getFirstPage(true)} ${page.getPrevPage(true)} ${page.getNav()} ${page.getNextPage(true)} ${page.getLastPage(true)}
</div>
<form action="article" method="post" enctype="multipart/form-data">
	<input type="text" name="title" value="我们">
	<input type="text" name="clicks">
	<input type="checkbox" name="checkbox" value="2">
	<input type="checkbox" name="checkbox" value="3">
	<input type="checkbox" name="checkbox" value="4">
	<input type="file" name="filename">
	<input type="submit" value="SUBMIT">
</form>
<div>
	<c:forEach begin="1" end="10" var="i">${i} </c:forEach>
</div>
<div>
	<c:forEach begin="11" end="20" varStatus="g">${g.index} </c:forEach>
</div>
</body>
</html>
