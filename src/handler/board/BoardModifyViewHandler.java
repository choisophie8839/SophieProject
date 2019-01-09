package handler.board;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.BoardDao;
import board.BoardDataBean;
import board.FileDataBean;
import handler.CommandHandler;
import handler.HandlerException;
import member.MemberDao;

@Controller
public class BoardModifyViewHandler implements CommandHandler {
	@Resource
	private MemberDao memberDao;
	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardModifyView")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title="Hi! BoardModifyView!";
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String passwd = request.getParameter("passwd");
		String id = (String) request.getSession().getAttribute("memid");
		
		int idcheck = boardDao.idCheck(id, passwd, num);
		int passwdcheck = 0;
		int result = 0;
		if( idcheck == 1 ) {
			passwdcheck = memberDao.check(id, passwd);
			if( passwdcheck == 1 ) {
				result = 1;
				BoardDataBean boardDto = boardDao.getArticle( num );
				List<FileDataBean> fileDto = boardDao.checkFiles(num);
				if( ! fileDto.isEmpty()) {
					request.setAttribute("fileDto", fileDto);
				}
				request.setAttribute("result", result);
				request.setAttribute("boardDto", boardDto);
			} else if ( passwdcheck == -1 ) {
				result = 0;
				request.setAttribute("result", result);
			}
		} else if ( idcheck == -1 ){
			result = 2;
			request.setAttribute("result", result);
		}
		request.setAttribute("title", title);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		
		return new ModelAndView("/board/modifyView");
	}

}
