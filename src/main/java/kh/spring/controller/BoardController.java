package kh.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.config.BoardConfig;
import kh.spring.dao.BoardDAO;
import kh.spring.dao.MemberDAO;
import kh.spring.dto.BoardDTO;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BoardDAO daoB;
	
	@Autowired
	private MemberDAO daoM;
	
	
	/*
	@RequestMapping("writeForm")
	public String write() {
		
	}
	@RequestMapping("writeProc")
	public String writeProc() {
		
	}
	*/
	@RequestMapping("list")
	public String list(int cpage, String category, String searchWord, Model m) throws Exception {
		int endNum =cpage*BoardConfig.RECORD_COUNT_PER_PAGE;
		int startNum =endNum -(BoardConfig.RECORD_COUNT_PER_PAGE-1);
		
		List<BoardDTO> list ;
		if(searchWord==null||searchWord.contentEquals("")) {
			list = daoB.getPageList(startNum,endNum);
		}else {
			list = daoB.getPageList(startNum,endNum,category,searchWord);
			
		}
		
		List<String> pageNavi = daoB.getPageNavi(cpage,category,searchWord);
		
		List<BoardDTO> searchList = null;
	    if(searchWord==null||searchWord.contentEquals("")) {
	    }else {
	     searchList = daoB.search(category, searchWord);
	    }
	    
	    m.addAttribute("cpage", cpage);
	    m.addAttribute("searchList",searchList);
	    m.addAttribute("list", list);
	    m.addAttribute("navi", pageNavi);
	    m.addAttribute("category", category);
	    m.addAttribute("searchWord", searchWord);
		
	    return "/board/boardMain";
	}/*
	@RequestMapping("view")
	public String view() {
		
	}
	@RequestMapping("modify")
	public String modify() {
		
	}
	@RequestMapping("delete")
	public String delete() {
		
	}
*/

	
	
	
	
}
