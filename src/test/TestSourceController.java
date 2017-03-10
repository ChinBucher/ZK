package test;

import static org.junit.Assert.*;
import model.Source;

import org.junit.Test;

import controller.SourceController;

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
	
}
