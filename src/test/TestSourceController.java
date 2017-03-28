/**
 * Junit 测试 代码
 */
package test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Source;

import org.junit.Test;

import controller.SourceController;
import controller.StatuteController;

public class TestSourceController {
	
	@Test
	public void testSrcCtr(){
		SourceController  sc = new SourceController();
		sc.downLoad();
	}
	
	@Test
	public void testSrcPaginate(){
		Source src = new Source();
		src.fName();
	}
	
	@Test
	public void testDate(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh：mm");
		String s = df.format(d);
		System.out.println(s);
		
		Date currentDate = new Date(System.currentTimeMillis());
		System.out.println("currentDate: " + currentDate);
		String str = currentDate.toString();
		String[] dateStr = str.split(" ");
		String day = dateStr[2];
		String month = dateStr[1];
		System.out.println("day-month: " + day + "-" + month );
		
		Date dd = new Date();  
//        System.out.println(dd);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(dd);  
//        System.out.println("格式化后的日期：" + dateNowStr); 
        String[] ss = dateNowStr.toString().split(" ");
        String sdate = ss[0];
        System.out.println("date: " + sdate);
	}
	
	@Test
	public void testSearch(){
		StatuteController sc = new StatuteController();
		sc.search();
	}
	
	@Test
	public void test(){
		abstract class Book {
		    String title;
		    String author;
		    
		    Book(String title, String author) {
		        this.title = title;
		        this.author = author;
		    }
		    
		    abstract void display();
		}
		
		class MyBook extends Book{
			private int price;
			MyBook(String title, String author, int price) {
				super(title, author);
				// TODO Auto-generated constructor stub
				this.price = price;
			}

			@Override
			void display() {
				// TODO Auto-generated method stub
				System.out.println("Title: " + this.title +
												"Author: " + this.author +
												"Price: " + this.price);
				
			}
			
		}
	}
	
	
}
