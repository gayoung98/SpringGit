package kh.spring.dao;



import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


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
		String sql = "update board set title=?, content=?  where seq=?";
		return jdbc.update(sql, dto.getTitle(), dto.getContent(), dto.getSeq());				
	}


	public BoardDTO select(int seq) throws Exception{
		String sql = "select * from board where seq=?";
		return jdbc.queryForObject(sql, new RowMapper<BoardDTO>() {
			@Override
			public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				BoardDTO dto = new BoardDTO();
				dto.setSeq(seq);
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setWrite_date(rs.getDate("write_date"));
				dto.setView_count(rs.getInt("view_count"));
				return dto;
			}
		}, seq);
	}


	public int addViewCount(int seq, int viewCount) throws Exception{
		String sql = "update ass set viewCount=? where seq=?";
		return jdbc.update(sql, ++viewCount, seq);
	}


	public int getRecordCount() throws Exception {
		String sql = "select count(*) from board";
		return jdbc.queryForObject(sql, Integer.class);
	}
	// 오버 로딩해서 다시 하나 더 만들기
	public int getRecordCount(String category, String keyword) throws Exception {
		String sql = "select count(*) from board where " + category + " like ?";
		return jdbc.queryForObject(sql, Integer.class);
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
