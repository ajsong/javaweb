package com.laokema.javaweb.servlet.api;

import com.laokema.tool.Common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OtherServlet", value = "/other/*")
public class OtherServlet extends CoreServlet {
	//上传文件
	public void uploadfile() {
		int UPLOAD_LOCAL = Integer.parseInt(Common.get_properties().get("upload.local"));
		String name = this.request.get("name", "filename");
		String dir = this.request.get("dir", "pic");
		int local = this.request.get("local", UPLOAD_LOCAL);
		int detail = this.request.get("detail", 0);
		String type = this.request.get("type", "jpg,jpeg,png,gif,bmp");
		if (detail == 1) {
			Map<String, Object> files = this.request.file(dir, type, true);
			if (files == null) Common.error("请选择文件");
			Common.success(files);
		} else {
			String file = this.request.file(name, dir, type);
			if (file.length() == 0) Common.error("请选择文件");
			Common.success(file);
		}
	}
}
