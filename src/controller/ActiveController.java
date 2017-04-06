package controller;

import interceptor.AuthInterceptor;
import interceptor.downInterceptor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Active;
import model.News;
import model.Statute;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class ActiveController extends Controller{
//	客户观察视图
	@ActionKey("/active")
	public void index(){
		setAttr("actPage",Active.act.paginate(getParaToInt(0,1), 10));
		render("/active.html");
	}

//	admin管理视图
	@Before(AuthInterceptor.class)
	public void manage(){
		setAttr("actPage", Active.act.paginate(getParaToInt(0, 1), 10));
		render("/src/actManage.html");
	}
	
//	删除刷新
	/*
	 * 添加一个拦截器 获得 actionkey
	 * 传值，交给 重定位
	 */
	public void delete(){
		Active.act.deleteById(getParaToInt());
		redirect("/active/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void add(){
		render("/src/actAdd.html");
	}
	
	//添加一个validator 验证信息是否有空缺
	public void upload(){
		System.out.println("upload");
		List<UploadFile> files = this.getFiles("./active");
		
		Active s = getModel(Active.class);
		String basePath = PathKit.getWebRootPath(),
				  folder = "\\upload\\statute",
				  fname =  s.getDocPath();
		String path = basePath + folder + fname;
		String[] filename = new String[2];
		System.out.println("路径："+path);
		
		for(int i=0;i<files.size();i++){
			filename[i] = files.get(i).getFileName();
		}
		if(null !=filename[0]){
			s.setDocPath(filename[0]);
		}
		if(null != filename[1]){
			s.setPicPath(filename[1]);
		}
		s.save();
		
//		截取内容设置
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length()) + "...";
		}
		s.setCont(cont);
		
//		日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/active/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void edit() {
		Active s = Active.act.findById(getParaToInt());
		System.out.println("edit-s: " + s);
		
		String content = s.getContent();
		System.out.println(content);
		content = content.replace("<br>","\n");
		System.out.println(content);
		s.setContent(content);
		s.update();
		
		setAttr("active", Active.act.findById(getParaToInt()));
		render("/src/actEdit.html");
	}
	
	@Before(AuthInterceptor.class)
	public void update() {
		System.out.println("update");
		List<UploadFile> files = this.getFiles("./active");
		Active s = getModel(Active.class);
		String basePath = PathKit.getWebRootPath(),
				  folder = "\\upload\\statute",
				  fname =  s.getDocPath();
		String path = basePath + folder + fname;
		String[] filename = new String[2];
		System.out.println("路径："+path);
		
		for(int i=0;i<files.size();i++){
			filename[i] = files.get(i).getFileName();
		}
		if(null !=filename[0]){
			s.setDocPath(filename[0]);
		}
		if(null != filename[1]){
			s.setPicPath(filename[1]);
		}
		s.update();
		
//		截取内容设置
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length()) + "...";
		}
		System.out.println("cont:" + cont);
		s.setCont(cont);
		
//		设置日期
		Date currentDate = new Date(System.currentTimeMillis());
		String[] dateStr = currentDate.toString().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.setDay( dateStr[2]);
		s.setMonth(dateStr[1]);
		
		s.update();
		redirect("/active/manage");
	}
	
	public void detail(){
		Active.act.findById(getParaToInt()).countAdd(getParaToInt());
		setAttr("source", Active.act.findById(getParaToInt()));
		render("/detailSingleAct.html");
	}
	
	public void detailEdit(){
		setAttr("source", Active.act.findById(getParaToInt()));
		render("/singleAct.html");
	}
	
	/*
	 * 下载：
	 * 1.数据库中读取文件名
	 * 2.与存储位置拼接成为文件地址
	 */
		@Before(downInterceptor.class)
		public void download(){
			int id = getParaToInt();
			String docpath = Active.act.docPath(id);
			
			if(docpath.length() != 1){
				String path = PathKit.getWebRootPath() + "\\upload\\active";
				System.out.println("path: " + path);
				File file = new File(path + "\\" + docpath);
				if(file.isFile()){
					renderFile(file);
				}else{
					System.out.println("not file");
					renderText("文件不在");
				}
			}else{
				renderText("meiyou");
			}
		}
		
		public void search(){
			System.out.println("search");
			String title = getPara("search");
			System.out.println("title: " + title);
			setAttr("statPage", Statute.stat.paginate(1, 10, title));
			setAttr("actPage", Active.act.paginate(1, 10, title));
			setAttr("newsPage", News.news.paginate(1, 10, title));
			render("/src/search.html");
		}
	
}
