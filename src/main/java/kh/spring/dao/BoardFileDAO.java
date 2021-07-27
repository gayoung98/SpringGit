package kh.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.spring.dto.BoardFileDTO;

@Repository
public class BoardFileDAO {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public int insert(BoardFileDTO dto) throws Exception{

		String sql = "insert into boardFile values(boardFile_seq.nextval, ?, ?, sysdate, ?)";
		return jdbc.update(sql, dto.getOriName(), dto.getSysName(), dto.getParent());
		
	}
	
	public int delete(int seq) throws Exception{

		String sql = "delete from boardFile where seq=?";
		return jdbc.update(sql, seq);
		
	}
	public int deleteAll(int parent) throws Exception{
		String sql = "delete from boardFile where parent=?";
		return jdbc.update(sql, parent);
	}
	
	public BoardFileDTO select(int seq) throws Exception {

		String sql = "select * from boardFile where seq=?";

		return jdbc.queryForObject(sql, new RowMapper<BoardFileDTO>() {
			

			@Override
			public BoardFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				BoardFileDTO dto = new BoardFileDTO();
				dto.setSeq(seq);
				dto.setOriName(rs.getString("oriName"));
				dto.setSysName(rs.getString("sysName"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setParent(rs.getInt("parent"));
				return dto;
			}
		}, seq);

	}


	public List<BoardFileDTO> selectAll(int parent) throws Exception {

		String sql = "select * from boardFile where parent=?";

		return jdbc.query(sql, new RowMapper<BoardFileDTO>() {
			

			@Override
			public BoardFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				BoardFileDTO dto = new BoardFileDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setOriName(rs.getString("oriName"));
				dto.setSysName(rs.getString("sysName"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setParent(parent);
				return dto;
			}
		}, parent);

	}
	
}
