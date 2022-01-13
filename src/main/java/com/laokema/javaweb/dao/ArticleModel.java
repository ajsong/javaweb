package com.laokema.javaweb.dao;

import com.laokema.javaweb.model.Article;
import com.laokema.tool.DB;

import java.sql.*;
import java.util.List;

public class ArticleModel {
	public List<Article> getList(){
		return DB.share("article").select(Article.class);
	}

	public boolean update(int id, String title) {
		boolean flag = false;
		int count = DB.share("article").where("", id).update(new String[]{"title"}, title);
		if (count > 0) flag = true;
		return flag;
	}

	public boolean delete(int id) {
		boolean flag = false;
		int count = DB.share("article").where("", id).delete();
		if (count > 0) flag = true;
		return flag;
	}

	public boolean login(String title, int clicks) {
		boolean flag = false;
		try {
			ResultSet rs = DB.query("select * from ws_article where title='"+title+"'");
			while (rs.next()) {
				if (rs.getString("title").equals(title) && rs.getInt("clicks") == clicks) {
					flag = true;
				}
			}
			DB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean register(Article article) {
		boolean flag = false;
		int count = DB.execute("insert into ws_article(title, clicks) values('"+article.title+"','"+article.clicks+"','"+article.clicks+"')");
		if (count > 0) flag = true;
		return flag;
	}
}
