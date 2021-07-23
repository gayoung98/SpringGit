package kh.spring.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;

import kh.spring.config.BoardConfig;
import kh.spring.dao.BoardDAO;
import kh.spring.dao.BoardFileDAO;
import kh.spring.dao.MemberDAO;
import kh.spring.dto.BoardDTO;
import kh.spring.dto.BoardFileDTO;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BoardDAO daoB;
	
	@Autowired
	private BoardFileDAO daoF;
	
	@Autowired
	private MemberDAO daoM;
	

	@RequestMapping("writeForm")
	public String writeForm() {
		return "/board/boardWrite";
	}
	
	@RequestMapping("writeProc")
	public String writeProc(BoardDTO dto) throws Exception {
		System.out.println("write.ass");
		
		String id = (String) session.getAttribute("loginId");

		int seq = daoB.getSeq();
		String title = dto.getTitle();
		String contents = dto.getContent();
		String writer = id;
		int view_count = 0;

		BoardDTO dtoInsert = new BoardDTO(seq, title, contents, writer, null, view_count);

		int insert = daoB.insert(dtoInsert);
		if(insert>0) {
			System.out.println("글쓰기 입력 완료");
		}

		return "/board/list";
	}

	
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
	}

	@RequestMapping("view")
	public String view() {
		
	}
	
	@RequestMapping("modiForm")
	public String modiForm() {
		return "/board/boardModify";
	}
	@RequestMapping("modiProc")
	public String modiProc(BoardDTO dto) throws Exception {
		System.out.println("과제 수정 요청");

		String id = (String) session.getAttribute("loginId");

		
		int seq = daoB.getSeq();
		String title = dto.getTitle();
		String contents = dto.getContent();
		String writer = id;
		int view_count = 0;

		BoardDTO dtoModify = new BoardDTO(seq, title, contents, writer, null, view_count);
		

		int result = daoB.update(dtoModify);
		if(result>0) {
			System.out.println("글 수정 완료");
		}

		return "/board/list";
	}
	@RequestMapping("delete")
	public String delete() {
		
	}
	
	@ExceptionHandler
	public String exceptionHandlerB(Exception e) {
		e.printStackTrace();
		return "error";
	}
	



	
	
	
	
}
