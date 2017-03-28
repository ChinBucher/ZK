package controller;

import interceptor.downInterceptor;

import java.io.File;
import java.util.List;

import model.Source;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class SourceController extends Controller {
	public void index() {
		setAttr("srcPage", Source.src.paginate(getParaToInt(0, 1), 10));
//		测试新页面的数据渲染
//		render("srcList.html");
		
		/*
		 * 1.html 中 涉及到文件的下载，下载按照传入参数 id 的条件下获取文件
		 */
//		render("1.html");
		/*
		 * 2.html 中 涉及到图片的加载，以下是对图片资源的路径的获取
		 */
		String p = "upload";
		setAttr("p", p);
		//path: E:\Eclipse-JavaEE-test\HX_System\WebRoot
		render("2.html");
	}
	
	public void add() {
		
	}
	
	public void save() {
		getModel(Source.class).save();
		redirect("/src");
	}
	
	public void edit() {
		Source s = Source.src.findById(getParaToInt());
		System.out.println("s: " + s);
		setAttr("source", Source.src.findById(getParaToInt()));
	}
	
//	@Before(SourceValidator.class)
	public void update() {
		System.out.print("upload");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		Source s = getModel(Source.class);
		System.out.println("s: " + s);
		String docpath = getPara("source.docpath"),
				  picpath  = getPara("source.picpath");
		s.setDocPath(docpath);
		s.setPicPath(picpath);
		s.update();
		//使用update存在对 path 的赋值为 null
		
		/*
		 * ###
		 * jfinal 自带update方式中文存储问题----jdbc配置
		 * ###
		 */
//		try {
////			URLDecoder.decode(,"UTF-8");
//			int id = Integer.parseInt(getPara("source.id"));
//			System.out.println("\nid: " + id);
//			String title = URLDecoder.decode(getPara("source.title"),"UTF-8"),
//					  type= URLDecoder.decode(getPara("source.type"),"UTF-8"),
//					  content = URLDecoder.decode(getPara("source.content"),"UTF-8"),
//					  docpath = URLDecoder.decode(getPara("source.docpath"),"UTF-8"),
//					  picpath = URLDecoder.decode(getPara("source.picpath"),"UTF-8");
//			System.out.println("title: " + title + "\ntype: " + type + "\ncontent: " + content + "\ndocpath: " + docpath + "\npicpath: " + picpath);
//			s.setTitle(title);
//			s.setType(type);
//			s.setContent(content);
//			s.setDocPath(docpath);
//			s.setPicPath(picpath);
//			System.out.println("s: " + s);
//			s.update();
//			System.out.println("s: " + s);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		redirect("/src");
	}
	
	public void delete() {
		Source.src.deleteById(getParaToInt());
		redirect("/src");
	}
	
	public void srcDetail(){
		System.out.println("detail src");
		int id = getParaToInt();
		System.out.println("id:"+id);
		Source src = Source.src.findById(id);
		setAttr("source", src);
		setAttr("p", "upload");
		render("/src/srcDetail.html");
	}
	
	//传入 id 获取数据
	public void detail(){
		System.out.println("detail src");
		int id = getParaToInt();
		System.out.println("id:"+id);
		List<Source> src = Source.src.find("select * from src where id = "+id+";");
		setAttr("source",src);
		render("/src/detail.html");
	}
	
//	文件上传与下载
	public void upload(){
		System.out.print("upload");
		UploadFile file = this.getFile();
		String fileName = getFile().getFileName();
		System.out.println("fileName:"+fileName);
		String uploadPath = file.getUploadPath();
		System.out.println("文件上传路径："+uploadPath);
		String path = uploadPath + "\\" + fileName;
		System.out.println("文件下载路径："+path);
//		将上传文件路径保存到数据库
//		Record src = new Record().set(column, value)
	    setAttr("fileName", fileName);
	    renderJson();
	}
	
	//测试使用
/*
 * 多文件上传：将文件名分别存储到数据库
 */
//	@Before(SourceValidator.class)
	public void up(){
		System.out.print("upload");
		List<UploadFile> files = this.getFiles();
		for(int i=0;i<files.size();i++){
			String fileName = files.get(i).getFileName();
			System.out.println("filename: " + fileName);
			String uploadPath = files.get(i).getUploadPath();
			System.out.println("文件上传路径："+uploadPath);
			String path = uploadPath + "\\" + fileName;
			System.out.println("文件下载路径："+path);
		}
		Source s = getModel(Source.class);
		s.save();
		System.out.println("s: " + s);
		redirect("/src");
	}
/*
 * 下载：
 * 1.数据库中读取文件名
 * 2.与存储位置拼接成为文件地址
 */
	@Before(downInterceptor.class)
	public void down(){
		int id = getParaToInt();
		System.out.println("id:" + id);
		Source sd = Source.src.findById(id);
		System.out.println("sd: " + sd);
		String fileName = sd.getDocPath();
		System.out.println("filename:" + fileName);
		System.out.println("filename.length: " + fileName.length());
		
		if(fileName.length() != 1){
//			String path = PathKit.getWebRootPath();
//			System.out.println(path);
//			path: E:\Eclipse-JavaEE-test\HX_System\WebRoot
			String path = PathKit.getWebRootPath() + "\\upload";
			System.out.println("path: " + path);
//			File file = new File(path + File.pathSeparator  + fileName);
			File file = new File(path + "\\" + fileName);
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
	
	public void downLoad(){
		int id = getParaToInt();
		System.out.println("id:" + id);
		Source sd = Source.src.findById(id);
		System.out.println("sd:" + sd);
//		使用将地址存储到数据表中 字段path
//		暂时使用type代替地址， 后续数据表会进行修改
//		String path = sd.getType();
		
		String fileName = sd.getTitle();
		System.out.println("filename:" + fileName);
//		String path = PathKit.getWebRootPath();
//		System.out.println(path);
//		path: E:\Eclipse-JavaEE-test\HX_System\WebRoot
		String path = PathKit.getWebRootPath() + "\\upload";
		File file = new File(path + File.pathSeparator  + fileName);
		renderFile(file);
	}

	//测试使用	
	public void download() {
		String fileName = "新建文本文档.txt";
		System.out.println("fileName: " + fileName);
		String path = PathKit.getWebRootPath() + "\\upload";
		System.out.println("path: " + path);
//		File file = new File(path + File.pathSeparator  + fileName);
		File file = new File(path + "\\"  + fileName);
		if(file.isFile()){
			System.out.println("isFile");
		}else{
			System.out.println("not");
		}
		renderFile(file);
		}
	
}



