package controller;

import interceptor.AuthInterceptor;
import interceptor.downInterceptor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import validator.SourceValidator;
import model.Active;
import model.News;
import model.Statute;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;


public class StatuteController extends Controller{
// 客户观察视图
	public void index(){
		setAttr("statPage", Statute.stat.paginate(getParaToInt(0, 1), 10));
		render("/statute.html");
	}

//	admin管理视图
	@Before(AuthInterceptor.class)
	public void manage(){
		setAttr("statPage", Statute.stat.paginate(getParaToInt(0, 1), 10));
		render("/src/statManage.html");
	}
	
//	删除刷新
	/*
	 * 添加一个拦截器 获得 actionkey
	 * 传值，交给 重定位
	 */
	public void delete(){
		Statute.stat.deleteById(getParaToInt());
		redirect("/statute/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void add(){
		render("/src/statuteAdd.html");
	}
	
	//添加一个validator 验证信息是否有空缺
//	@Before(SourceValidator.class)
	public void upload(){
		System.out.println("upload");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		Statute s = getModel(Statute.class);
		s.save();
		
//		截取内容显示
		String content = s.getContent();
		String cont;
		if(content.length() > 100){
			cont = content.substring(0, 100) + "...";
		}else{
			cont = content.substring(0, content.length());
		}
		s.setCont(cont);
		
//		日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/statute/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void edit() {
		Statute s = Statute.stat.findById(getParaToInt());
		System.out.println("edit-s: " + s);
		
		//edit.html 不显示<br>
		String content = s.getContent();
		System.out.println(content);
		content = content.replace("<br>","\n");
		System.out.println(content);
		s.setContent(content);
		s.update();
		
//		更新cont
		String contet = s.getContent();
		String cont;
		if(contet.length() > 260){
			cont = contet.substring(0, 260) + "...";
		}else{
			cont = contet.substring(0, contet.length());
		}
		s.setCont(cont);
		s.update();
		
		setAttr("statute", Statute.stat.findById(getParaToInt()));
		render("/src/statuteEdit.html");
	}
	
//	@Before(SourceValidator.class)
	public void update() {
		System.out.println("update");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		
		Statute s = getModel(Statute.class);
		System.out.println("s: " + s);
		String docpath = getPara("statute.docpath"),
				  picpath  = getPara("statute.picpath");
		s.setDocPath(docpath);
		s.setPicPath(picpath);
		s.update();
		
//		截取内容设置
		String content = s.getContent();
		System.out.println("###contentStat2: " + content);
		String cont;
		if(content.length() > 260){
			cont = content.substring(0, 260) + "...";
		}else{
			cont = content.substring(0, content.length());
		}
		s.setCont(cont);
		s.update();
		
		redirect("/statute/manage");
	}
	
	public void detail(){
		Statute.stat.findById(getParaToInt()).countAdd(getParaToInt());
		setAttr("source", Statute.stat.findById(getParaToInt()));
		render("/detailSingleStat.html");
	}
	
	public void detailEdit(){
		setAttr("source", Statute.stat.findById(getParaToInt()));
		render("/singleStat.html");
	}
	
	/*
	 * 下载：
	 * 1.数据库中读取文件名
	 * 2.与存储位置拼接成为文件地址
	 */
		@Before(downInterceptor.class)
		public void download(){
			int id = getParaToInt();
			String docpath = Statute.stat.docPath(id);
			
			if(docpath.length() >= 1){
				String path = PathKit.getWebRootPath() + "\\upload";
				System.out.println("path: " + path);
				File file = new File(path + "\\" + docpath);
				if(file.isFile()){
					renderFile(file);
				}else{
					System.out.println("not file");
//					renderText("文件不在");
					setAttr("sList", Statute.stat.recommend());
					render("/src/statDown404.html");
				}
			}else{
//				renderText("meiyou");
				setAttr("sList", Statute.stat.recommend());
				render("/src/statDown404.html");
			}
		}
		
//		搜索
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
