package com.laokema.javaweb.servlet.api;

import com.laokema.javaweb.model.Article;
import com.laokema.tool.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		Common.setServlet(request, response);
		Request req = new Request(request, response);
		int offset = req.get("offset", 0);
		List<Article> list = DB.share("article").pagination(request).limit(offset, 10).order("id DESC").select(Article.class);
		request.setAttribute("rs", list);
		Common.success("/article.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Request req = new Request(request, response);
		String title = req.get("title");
		int clicks = req.get("clicks", 10);
		String[] checkbox = req.get("checkbox[]", true);
		String file = req.file("filename");
		/*
		ArticleModel model = new ArticleModel();
		if(model.login(title, clicks)){
			request.sendRedirect("/member.jsp"); //登录成功跳转到会员中心
		}else{
			response.sendRedirect("/index.jsp"); //重定向到首页
		}
		*/
	}
}
