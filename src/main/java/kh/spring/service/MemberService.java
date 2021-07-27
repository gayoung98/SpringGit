package kh.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.spring.dao.MemberDAO;
import kh.spring.dto.MemberDTO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO dao;
	
	public int idCheck(String id) throws Exception{
		return dao.idCheck(id);
	}
	public int join(MemberDTO dto) throws Exception{
		return dao.join(dto);
	}
	public int login(String id, String pw) throws Exception{
		return dao.login(id, pw);
	}
	public List<MemberDTO> selectAll() throws Exception{
		return dao.selectAll();
	}
	public int signout(String id) throws Exception{
		return dao.signout(id);
	}
	public  List<MemberDTO> mypage(String id) throws Exception{
		return dao.mypage(id);
	}
}
