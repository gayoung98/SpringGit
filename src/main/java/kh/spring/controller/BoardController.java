package kh.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.dao.MemberDAO;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BoardDAO daoB;
	
	@Autowired
	private MemberDAO daoM;
	
	@RequestMapping("writeForm")
	public String write() {
		
	}
	@RequestMapping("writeProc")
	public String writeProc() {
		
	}
	
	@RequestMapping("list")
	public String list() {
		
	}
	@RequestMapping("view")
	public String view() {
		
	}
	@RequestMapping("modify")
	public String modify() {
		
	}
	@RequestMapping("delete")
	public String delete() {
		
	}


	
	
	
	
}
