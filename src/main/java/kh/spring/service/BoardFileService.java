package kh.spring.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.spring.dao.BoardFileDAO;
import kh.spring.dto.BoardFileDTO;


@Service
public class BoardFileService {

	
	@Autowired
	private BoardFileDAO dao;
	
	public int insert(BoardFileDTO dto) throws Exception{
		return dao.insert(dto);
	}
	public int delete(int seq) throws Exception{
		return dao.delete(seq);
	}
	public int deleteAll(int parent) throws Exception{
		return dao.deleteAll(parent);
	}
	public BoardFileDTO select(int seq) throws Exception {
		return dao.select(seq);
	}
	public List<BoardFileDTO> selectAll(int parent) throws Exception {
		return dao.selectAll(parent);
	}

	public void uploadFiles(int seq, String realPath, MultipartFile[] file) throws Exception{
		File filesPath = new File(realPath);
		if(!filesPath.exists()) {
			filesPath.mkdir();
		}
		for(MultipartFile tmp : file) {
			if(tmp.getSize()>0) {
				String oriName = tmp.getOriginalFilename();
				String sysName = UUID.randomUUID().toString().replaceAll("-", "")+"_"+oriName;
				System.out.println("parent: "+seq);
				if(dao.insert(new BoardFileDTO(0, oriName, sysName, null, seq))>0) {
					System.out.println("parent: "+seq);
					System.out.println("DB에 파일 저장 완료");
					tmp.transferTo(new File(filesPath.getAbsolutePath()+"/"+sysName));
					System.out.println(filesPath.getAbsolutePath());
				}
			}
		}	
	}
	public void deleteFiles(String realPath, String[] delTarget) throws Exception{
		File filesPath = new File(realPath);
		if(!filesPath.exists()) {
			filesPath.mkdir();
		}
		for(String del : delTarget) {
			System.out.println("지울 파일 번호 "+ del);

			String sysName = dao.select(Integer.parseInt(del)).getSysName();
			File targetFile = new File(filesPath +"/" + sysName); 
			boolean resultF = targetFile.delete();
			System.out.println("파일 삭제 여부: " + resultF);
			if(resultF) {
				int resultDBF = dao.delete(Integer.parseInt(del));
				if(resultDBF>0) {
					System.out.println("DB에서 파일 삭제 성공");
				}else {
					System.out.println("DB에서 파일 삭제 실패");
				}
			}else {
				System.out.println("파일 삭제 실패");
			}
		}
	}
	
	
}
