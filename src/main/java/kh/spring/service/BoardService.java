package kh.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return dao.getPageNavi(currentPage, category, searchWord);
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
	
	
}
