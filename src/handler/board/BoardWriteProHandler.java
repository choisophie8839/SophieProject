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
public class BoardWriteProHandler implements CommandHandler {
	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardWritePro")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! WritePro!";
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		
		BoardDataBean boardDto = new BoardDataBean();
		boardDto.setNum(Integer.parseInt(multi.getParameter("num")));
		boardDto.setId(multi.getParameter("id"));
		boardDto.setSubject(multi.getParameter("subject"));
		boardDto.setRe_f(Integer.parseInt(multi.getParameter("re_f")));
		boardDto.setRe_step(Integer.parseInt(multi.getParameter("re_step")));
		boardDto.setRe_level(Integer.parseInt(multi.getParameter("re_level")));
		boardDto.setRe_type(Integer.parseInt(multi.getParameter("re_type")));
		boardDto.setContent(multi.getParameter("content"));
		boardDto.setScore(Integer.parseInt(multi.getParameter("score")));
		boardDto.setMovienm(multi.getParameter("movienm"));
		boardDto.setReg_date( new Timestamp( System.currentTimeMillis()));
		
		int result = 0;
		
		List<MultipartFile> files = ( multi.getFiles("files").get(0).getSize() > 0 ) ? multi.getFiles("files") : null;
		if(files==null) {
			result = boardDao.insertArticle(boardDto);
			request.setAttribute("result", result);
		} else {
			result = boardDao.insertArticle(boardDto);
			int returnNum = boardDto.getNum();
			
			List<FileDataBean> fileDtos = new ArrayList<FileDataBean>();
			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap.put("num", returnNum);
			fileMap.put("filereg_date", new Timestamp( System.currentTimeMillis()));
			
			String path = request.getServletContext().getRealPath("/boardupload/");		
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
		}
		
		request.setAttribute("title", title);
		return new ModelAndView("/board/writePro");
	}

}
