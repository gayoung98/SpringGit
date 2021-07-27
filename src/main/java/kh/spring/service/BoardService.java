package kh.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.spring.config.BoardConfig;
import kh.spring.dao.BoardDAO;
import kh.spring.dto.BoardDTO;


@Service
public class BoardService {
	
	@Autowired
	private BoardDAO dao;
	
	
	public int getSeq() throws Exception{
		return dao.getSeq();
	}
	public int insert(BoardDTO dto) throws Exception{
		return dao.insert(dto);
	}

	public int update(BoardDTO dto) throws Exception {
		return dao.update(dto);
	}
	public int delete(int seq) throws Exception {
		return dao.delete(seq);
	}
	public BoardDTO select(int seq) throws Exception{
		return dao.select(seq);
	}
	public int addViewCount(int seq, int viewCount) throws Exception{
		return dao.addViewCount(seq, viewCount);
	}
	private int getRecordCount() throws Exception {
		return dao.getRecordCount();
	}
	public int getRecordCount(String category, String keyword) throws Exception {
		return dao.getRecordCount(category, keyword);
	}
	public List<String> getPageNavi(int currentPage, String category, String searchWord) throws Exception {
		int recordTotalCount ;

		if(searchWord==null||searchWord.contentEquals("")) {
			recordTotalCount=this.getRecordCount();
		}else {
			recordTotalCount=this.getRecordCount(category,searchWord);
		}

		int recordCountPerPage = BoardConfig.RECORD_COUNT_PER_PAGE; // 한 페이지 당 보여줄 게시글의 개수
		int naviCountPerPage = BoardConfig.NAVI_COUNT_PER_PAGE; // 내 위치 페이지를 기준으로 시작부터 끝까지의 페이지가 총 몇개인지

		int pageTotalCount = 0;   
		// 전체 레코드를 페이지당 보여줄 게시글 수 로 나눠서, 나머지가 0보다 크다면 1페이지를 더 추가해줘라!
		if (recordTotalCount % recordCountPerPage > 0) {
			pageTotalCount = (recordTotalCount / recordCountPerPage) + 1;
		} else {
			// 전체 레코드를 페이지당 보여줄 게시글 수 로 나눠서, 나머지가 0이면
			// 페이지의 게시글 수와 레코드 개수가 딱 맞아 떨어지니까, 총 만들어야 할 전체 페이지 개수도 딱 맞아 떨어진다!
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}


		if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		} else if (currentPage < 1) {
			currentPage = 1;
		}

		// 페이지 네비게이터의 첫번째 시작 숫자를 알 수 있는 코드
		int startNavi = (currentPage - 1) / naviCountPerPage * naviCountPerPage + 1;
		// 페이지 네비게이터의 마지막 숫자를 알 수 있는 코드
		int endNavi = startNavi + (naviCountPerPage - 1);
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		// 페이지 < 1 2 3 4 5> 처럼 이전, 이후 표시 만드는 코드
		boolean needPrev = true;
		boolean needNext = true;

		if (startNavi == 1) {
			needPrev = false;
		}
		if (endNavi == pageTotalCount) {
			needNext = false;
		}

		List<String> pageNavi = new ArrayList<>();

		if (needPrev) {
			pageNavi.add("<");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			pageNavi.add(String.valueOf(i)); // 숫자 i를 string으로 변환해서 add 해주기!
		}
		if (needNext) {
			pageNavi.add(">");
		}

		return pageNavi;
	}
	public List<BoardDTO> getPageList(int startNum, int endNum) throws Exception {
		return dao.getPageList(startNum, endNum);
	}
	public List<BoardDTO> getPageList(int startNum, int endNum, String category, String keyword) throws Exception {
		return dao.getPageList(startNum, endNum, category, keyword);
	}
	public List<BoardDTO> search(String category, String searchWord) throws Exception {
		return dao.search(category, searchWord);
	}
	public List<BoardDTO> list(int cpage, String category, String searchWord) throws Exception {
		int endNum =cpage*BoardConfig.RECORD_COUNT_PER_PAGE;
		int startNum =endNum -(BoardConfig.RECORD_COUNT_PER_PAGE-1);

		List<BoardDTO> list ;
		if(searchWord==null||searchWord.contentEquals("")) {
			list = dao.getPageList(startNum,endNum);
		}else {
			list = dao.getPageList(startNum,endNum,category,searchWord);
		}
		return list;
	}
	public List<BoardDTO> searchList(String category, String searchWord) throws Exception {
		List<BoardDTO> searchList = null;
		if(searchWord==null||searchWord.contentEquals("")) {
		}else {
			searchList = dao.search(category, searchWord);
		}
		return searchList;
	}
	
}
