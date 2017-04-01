package controller;

import interceptor.AuthInterceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Doctor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class ServicesController extends Controller{
	public void index(){
		setAttr("doctorPage", Doctor.doctor.paginate(getParaToInt(0, 1), 4));
		render("/services.html");
	}
	
	// 添加后台
	public void doctor(){
		setAttr("doctorPage", Doctor.doctor.paginate(getParaToInt(0, 1), 12));
		render("/doctor.html");
	}
	
	@Before(AuthInterceptor.class)
	public void manage(){
		setAttr("doctorPage", Doctor.doctor.paginate(getParaToInt(0, 1), 10));
		render("/src/doctorManage.html");
	}
	
	public void delete(){
		Doctor.doctor.deleteById(getParaToInt());
		redirect("/services/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void add(){
		render("/src/doctorAdd.html");
	}
	
	//添加一个validator 验证信息是否有空缺
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
		Doctor s = getModel(Doctor.class);
		s.save();
//		截取内容显示
		String title = s.getTitle();
		String titlec;
		if(title.length() > 50){
			titlec = title.substring(0, 50) + "...";
		}else{
			titlec = title.substring(0, title.length()) + "...";
		}
		s.setTitlec(titlec);
		
//		日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/services/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void edit() {
		Doctor s = Doctor.doctor.findById(getParaToInt());
		System.out.println("edit-s: " + s);
		
		String title = s.getTitle();
		System.out.println(title);
		title = title.replace("<br>","\n");
		System.out.println(title);
		s.setTitle(title);
		s.update();
		
//		更新 titlec
		String tit = s.getTitle();
		String titlec;
		if(tit.length() > 50){
			titlec = tit.substring(0, 50) + "...";
		}else{
			titlec = tit.substring(0, tit.length()) + "...";
		}
		s.setTitlec(titlec);
		s.update();
		
		setAttr("doctor", Doctor.doctor.findById(getParaToInt()));
		render("/src/doctorEdit.html");
	}
	
	@Before(AuthInterceptor.class)
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
		
		Doctor s = getModel(Doctor.class);
		System.out.println("s: " + s);
		String  picpath  = getPara("doctor.picpath");
		s.setPicPath(picpath);
		s.update();

//		截取内容设置
		String tit = s.getTitle();
		String cont;
		if(tit.length() > 50){
			cont = tit.substring(0, 50) + "...";
		}else{
			cont = tit.substring(0, tit.length()) + "...";
		}
		s.setTitlec(cont);
		
//		日期设置
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.update();
		
		redirect("/services/manage");
	}
	
	public void detail(){
		setAttr("source", Doctor.doctor.findById(getParaToInt()));
		render("/detailSingleDoctor.html");
	}
	
	public void detailEdit(){
		setAttr("source", Doctor.doctor.findById(getParaToInt()));
		render("/singleDoctor.html");
	}
	
	

}
