package kh.spring.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kh.spring.config.BoardConfig;
import kh.spring.dto.BoardDTO;


import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {

	@Autowired
	private JdbcTemplate jdbc;

	public int getSeq() throws SQLException, Exception {
		String sql= "select board_seq.nextval from dual";
		return jdbc.queryForObject(sql, Integer.class);
	}

	public int insert(BoardDTO dto) throws Exception{

		String sql ="insert into board values(?, ?, ?, ?, sysdate, 0)";
		return jdbc.update(sql, dto.getSeq(), dto.getTitle(), dto.getContent(), dto.getWriter());

	}

	public int delete(int seq) throws Exception{
		String sql = "delete from board where seq = ?";
		return jdbc.update(sql, seq);				
	}

	public int update(BoardDTO dto) throws Exception{
		String sql = "update board set title=?, contents=?  where seq=?";
		return jdbc.update(sql, dto.getTitle(), dto.getContent(), dto.getSeq());				
	}

	// view, modify에 사용할 select문.. 근데 문법이 맞는지 확신 없서용..
	//	public BoardDTO select(int seq) throws Exception{
	//		String sql = "select * from board where seq=?";
	//		return jdbc.queryForObject(sql, new RowMapper<BoardDTO>() {
	//			@Override
	//			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	//
	//				BoardDTO dto = new BoardDTO();
	//				dto.setSeq(seq);
	//				dto.setTitle(rs.getString("title"));
	//				dto.setContent(rs.getString("content"));
	//				dto.setWriter(rs.getString("writer"));
	//				dto.setWrite_date(rs.getDate("write_date"));
	//				dto.setView_count(rs.getInt("view_count"));
	//				return dto;
	//			}
	//		}, seq);
	//	}


	public int addViewCount(int seq, int viewCount) throws Exception{
		String sql = "update ass set viewCount=? where seq=?";
		return jdbc.update(sql, ++viewCount, seq);
	}


	private int getRecordCount() throws Exception {
		String sql = "select count(*) from board";
		return jdbc.queryForObject(sql, Integer.class);
	}
	// 오버 로딩해서 다시 하나 더 만들기
	private int getRecordCount(String category, String keyword) throws Exception {
		String sql = "select count(*) from board where " + category + " like ?";
		return jdbc.queryForObject(sql, Integer.class);
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
		String sql = "select * from " + "(select " + "row_number() over(order by seq desc) rnum," + "seq,"+"writer," + "title,"
				+ "content," + "write_date," + "view_count " + "from board) " + "where " + "rnum between ? and ?";
		return jdbc.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setWrite_date(rs.getDate("write_date"));
				dto.setView_count(rs.getInt("view_count"));
				return dto;
			}
		}, startNum,endNum );
	}
	// 검색 후, 페이지 리스트를 가져오는 메서드를 오버로딩해서 한번 더 만들기!
	public List<BoardDTO> getPageList(int startNum, int endNum, String category, String keyword) throws Exception {
		String sql = "select * from " + "(select " + "row_number() over(order by seq desc) rnum," + "seq,"+"writer," + "title,"
				+ "content," + "write_date," + "view_count " + "from board where "+category+" like ?) " + "where " + "rnum between ? and ?";
		return jdbc.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setWrite_date(rs.getDate("write_date"));
				dto.setView_count(rs.getInt("view_count"));
				return dto;
			}
		}, "%"+keyword+"%", startNum,endNum );

	}

	//========= 게시판  페이징 처리 끝! ======================================================================

	public List<BoardDTO> search(String category, String searchWord) throws Exception {
		String sql ="select * from board where "+category +" like ?";
		return jdbc.query(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardDTO dto = new BoardDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setWrite_date(rs.getDate("write_date"));
				dto.setView_count(rs.getInt("view_count"));
				return dto;
			}
		}, "%"+searchWord+"%");

	}




}
