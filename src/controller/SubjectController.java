/**
 * 分中心
 */
package controller;

import interceptor.AuthInterceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Subcenter;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class SubjectController extends Controller{
	public void index(){
		setAttr("sbcPage", Subcenter.sbc.paginate(getParaToInt(0, 1), 9));
		render("/subject.html");
	}
	
	public void detail(){
		setAttr("source",  Subcenter.sbc.findById(getParaToInt()));
		render("/detailSingleSubcenter.html");
	}
	
	@Before(AuthInterceptor.class)
	public void manage(){
		setAttr("sbcPage", Subcenter.sbc.paginate(getParaToInt(0, 1), 9));
		render("/src/subcenterManage.html");
	}
	
	public void detailEdit(){
		setAttr("source", Subcenter.sbc.findById(getParaToInt()));
		render("/singleSubcenter.html");
	}
	
	public void delete(){
		Subcenter.sbc.deleteById(getParaToInt());
		redirect("/subject/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void add(){
		render("/src/subcenterAdd.html");
	}
	
	public void upload(){
		System.out.println("upload");
		List<UploadFile> files = this.getFiles("./subject");
		
		Subcenter s = getModel(Subcenter.class);
		String basePath = PathKit.getWebRootPath(),
				  folder = "\\upload\\statute",
				  fname =  s.getPicPath();
		String path = basePath + folder + fname;
		String[] filename = new String[2];
		System.out.println("路径："+path);
		
		for(int i=0;i<files.size();i++){
			filename[i] = files.get(i).getFileName();
		}
		if(null != filename[1]){
			s.setPicPath(filename[1]);
		}
		s.save();
//		日期相关
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.update();
		
		System.out.println("s: " + s);
		redirect("/subject/manage");
	}
	
	@Before(AuthInterceptor.class)
	public void edit() {
		Subcenter s = Subcenter.sbc.findById(getParaToInt());
		System.out.println("edit-s: " + s);
		
		String title = s.getTitle();
		System.out.println(title);
		title = title.replace("<br>","\n");
		System.out.println(title);
		s.setTitle(title);
		s.update();
		
		setAttr("subcenter", Subcenter.sbc.findById(getParaToInt()));
		render("/src/subcenterEdit.html");
	}
	
	@Before(AuthInterceptor.class)
	public void update() {
		System.out.println("upload");
		List<UploadFile> files = this.getFiles("./subject");
		
		Subcenter s = getModel(Subcenter.class);
		String basePath = PathKit.getWebRootPath(),
				  folder = "\\upload\\statute",
				  fname =  s.getPicPath();
		String path = basePath + folder + fname;
		String[] filename = new String[2];
		System.out.println("路径："+path);
		
		for(int i=0;i<files.size();i++){
			filename[i] = files.get(i).getFileName();
		}
		if(null != filename[1]){
			s.setPicPath(filename[1]);
		}
		s.update();
		
//		日期设置
		Date currentDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dateNowStr = sdf.format(currentDate);  
		String[] sdate = dateNowStr.toString().split(" ");
		s.setDate(sdate[0]);
		s.update();
		
		redirect("/subject/manage");
	}
	
	
	
	

}
