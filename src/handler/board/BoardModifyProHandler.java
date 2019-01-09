package handler.board;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.BoardDao;
import board.BoardDataBean;
import board.FileDataBean;
import handler.CommandHandler;
import handler.HandlerException;

@Controller
public class BoardModifyProHandler implements CommandHandler {
	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardModifyPro")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! BoardModifyPro";
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		
		BoardDataBean boardDto = new BoardDataBean();
		boardDto.setNum(Integer.parseInt(multi.getParameter("num")));
		boardDto.setSubject(multi.getParameter("subject"));
		boardDto.setContent(multi.getParameter("content"));
		boardDto.setScore(Integer.parseInt(multi.getParameter("score")));
		boardDto.setMovienm(multi.getParameter("movienm"));
	
		int result = 0;
		
		List<MultipartFile> files = ( multi.getFiles("files").get(0).getSize() > 0 ) ? multi.getFiles("files") : null;
		if(files==null) {
			result = boardDao.modifyArticle(boardDto);
			request.setAttribute("result", result);
		} else {
			//modifyArticle을 하되 원래 있던 사진 목록 삭제하고 다시 insert
			result = boardDao.modifyArticle(boardDto);
			int returnNum = boardDto.getNum();
			List<FileDataBean> oldfileDto = boardDao.checkFiles(returnNum);
			if( ! oldfileDto.isEmpty()) {
				boardDao.deleteFiles(returnNum);
				
				List<FileDataBean> fileDtos = new ArrayList<FileDataBean>();
				Map<String, Object> fileMap = new HashMap<String, Object>();
				fileMap.put("num", returnNum);
				fileMap.put("filereg_date", new Timestamp( System.currentTimeMillis()));
				
				String path = request.getServletContext().getRealPath("/boardupload/");
				System.out.println("path : " + path);			
				UUID randomuuid = UUID.randomUUID();
				Calendar cal = Calendar.getInstance();
				File newfolder = new File( path+(cal.get(Calendar.MONTH)+1)+File.separator );
				if( !newfolder.exists()) {
					newfolder.mkdirs();
				}

				for(int i=0; i<files.size(); i++) {
					FileDataBean fileDto = new FileDataBean();
					fileDto.setOrg_filenm(files.get(i).getOriginalFilename());
					try {
						String std_filenm = path+(cal.get(Calendar.MONTH)+1)+File.separator+randomuuid.toString()+files.get(i).getOriginalFilename();
						System.out.println(std_filenm);
						fileDto.setStd_filenm((cal.get(Calendar.MONTH)+1)+File.separator+randomuuid.toString()+files.get(i).getOriginalFilename());
						File newfile = new File(std_filenm);
						files.get(i).transferTo(newfile);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileDtos.add(i, fileDto);
				}
				
				fileMap.put("fileDtos", fileDtos);
				int fileresult = boardDao.insertFiles(fileMap);
				request.setAttribute("fileresult", fileresult);
				System.out.println(fileresult);
			} else {
				List<FileDataBean> fileDtos = new ArrayList<FileDataBean>();
				Map<String, Object> fileMap = new HashMap<String, Object>();
				fileMap.put("num", returnNum);
				fileMap.put("filereg_date", new Timestamp( System.currentTimeMillis()));
				
				String path = request.getServletContext().getRealPath("/boardupload/");
				System.out.println("path : " + path);			
				UUID randomuuid = UUID.randomUUID();
				Calendar cal = Calendar.getInstance();
				File newfolder = new File( path+(cal.get(Calendar.MONTH)+1)+File.separator );
				if( !newfolder.exists()) {
					newfolder.mkdirs();
				}

				for(int i=0; i<files.size(); i++) {
					FileDataBean fileDto = new FileDataBean();
					fileDto.setOrg_filenm(files.get(i).getOriginalFilename());
					try {
						String std_filenm = path+(cal.get(Calendar.MONTH)+1)+File.separator+randomuuid.toString()+files.get(i).getOriginalFilename();
						System.out.println(std_filenm);
						fileDto.setStd_filenm((cal.get(Calendar.MONTH)+1)+File.separator+randomuuid.toString()+files.get(i).getOriginalFilename());
						File newfile = new File(std_filenm);
						files.get(i).transferTo(newfile);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileDtos.add(i, fileDto);
				}
				
				fileMap.put("fileDtos", fileDtos);
				int fileresult = boardDao.insertFiles(fileMap);
				request.setAttribute("fileresult", fileresult);
				System.out.println(fileresult);
			}
		}
		
		String pageNum = request.getParameter("pageNum");
		
		request.setAttribute("title", title);
		return new ModelAndView("/board/modifyPro");
	}
	public void makeNewFiles() {
		
	}
}
