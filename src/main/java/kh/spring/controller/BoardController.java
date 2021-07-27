package kh.spring.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kh.spring.config.BoardConfig;
import kh.spring.dao.MemberDAO;
import kh.spring.dto.BoardDTO;
import kh.spring.dto.BoardFileDTO;
import kh.spring.service.BoardFileService;
import kh.spring.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {


	@Autowired
	private HttpSession session;

	@Autowired
	private BoardService serviceB;

	@Autowired
	private BoardFileService serviceBF;

	@Autowired
	private MemberDAO daoM;


	@RequestMapping("writeForm")
	public String writeForm() {
		return "/board/boardWrite";
	}

	@RequestMapping("writeProc")
	public String writeProc(BoardDTO dto, MultipartFile[] file) throws Exception {
		System.out.println("write.ass");

		String id = (String) session.getAttribute("loginId");

		int seq = serviceB.getSeq();
		String title = dto.getTitle();
		String contents = dto.getContent();
		String writer = id;
		int view_count = 0;

		BoardDTO dtoInsert = new BoardDTO(seq, title, contents, writer, null, view_count);

		int insert = serviceB.insert(dtoInsert);
		if(insert>0) {
			System.out.println("글쓰기 입력 완료");
		}


		String realPath = session.getServletContext().getRealPath("files");
		File filesPath = serviceBF.mkdir(realPath);


		serviceBF.uploadFiles(seq, filesPath, file);	



		return "redirect:/board/list?cpage=1";
	}


	@RequestMapping("list")
	public String list(int cpage, String category, String searchWord, Model m) throws Exception {
		int endNum =cpage*BoardConfig.RECORD_COUNT_PER_PAGE;
		int startNum =endNum -(BoardConfig.RECORD_COUNT_PER_PAGE-1);

		List<BoardDTO> list ;
		if(searchWord==null||searchWord.contentEquals("")) {
			list = serviceB.getPageList(startNum,endNum);
		}else {
			list = serviceB.getPageList(startNum,endNum,category,searchWord);

		}

		List<String> pageNavi = serviceB.getPageNavi(cpage,category,searchWord);

		List<BoardDTO> searchList = null;
		if(searchWord==null||searchWord.contentEquals("")) {
		}else {
			searchList = serviceB.search(category, searchWord);
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
	public String view(int seq, Model m) throws Exception{
		BoardDTO dto = serviceB.select(seq);
		List<BoardFileDTO> fileList = serviceBF.selectAll(seq);

		m.addAttribute("list", dto);
		m.addAttribute("fileList", fileList);

		return "/board/boardView";
	}

	@RequestMapping("modiForm")
	public String modiForm(int seq, Model m) throws Exception{
		System.out.println("modiForm 호출");
		BoardDTO dto = serviceB.select(seq);
		List<BoardFileDTO> fileList = serviceBF.selectAll(seq);

		m.addAttribute("dto", dto);
		m.addAttribute("fileList", fileList);
		return "/board/boardModify";
	}
	@RequestMapping("modiProc")
	public String modiProc(BoardDTO dto, String[] delTarget, MultipartFile[] file) throws Exception {
		System.out.println("과제 수정 요청");


		int seq = dto.getSeq();
		String title = dto.getTitle();
		String contents = dto.getContent();

		BoardDTO dtoModify = new BoardDTO(seq, title, contents, null, null, 0);

		int result = serviceB.update(dtoModify);
		if(result>0) {
			System.out.println("글 수정 완료");
		}

		String realPath = session.getServletContext().getRealPath("files");
		File filesPath = serviceBF.mkdir(realPath);
		
		if(delTarget!= null) {  
			serviceBF.deleteFiles(filesPath, delTarget);
		}


		serviceBF.uploadFiles(seq, filesPath, file);



		return "redirect:/board/view?seq="+String.valueOf(seq);
	}


	@RequestMapping("delete")
	public String delete(int seq) throws Exception {

		String filesPath = session.getServletContext().getRealPath("files");
		List<BoardFileDTO> delFileList = serviceBF.selectAll(seq);

		if(delFileList!=null) {
			for(BoardFileDTO dtoF : delFileList) {
				String sysName = dtoF.getSysName();
				File targetFile = new File(filesPath +"/" + sysName); 

				boolean result = targetFile.delete();
				System.out.println("파일 삭제 여부: " + result);
			}
			serviceBF.deleteAll(seq);
		}

		serviceB.delete(seq);
		return "redirect:/board/list?cpage=1";
	}

	@RequestMapping("download")
	public void download(String oriName, String sysName, HttpServletResponse response) throws Exception{

		String realPath = session.getServletContext().getRealPath("files");
		File targetFile = new File(realPath + "/" + sysName); //저장된 경로

		oriName = new String(oriName.getBytes(), "ISO_8859-1");

		response.setContentType("application/octet-stream; charset=utf8"); 
		//application/octet-stream: binery(파일 내용), charset: 인코딩 정보
		response.setHeader("content-Disposition", "attachment;filename=\"" + oriName + "\""); 
		//attachment(첨부 파일)을 다운 받을 시에 사용할 이름. (사용자가 보는 이름)

		try(
				ServletOutputStream sos = response.getOutputStream();
				){
			FileUtils.copyFile(targetFile, sos); 
			//apache.io 안에 들어있는 파일 copy 기능
			//targetFile을 sos로 내보냄.
			sos.flush();
		}
	}

	@ExceptionHandler
	public String exceptionHandlerB(Exception e) {
		e.printStackTrace();
		return "error";
	}











}
