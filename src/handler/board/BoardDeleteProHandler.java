package handler.board;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.BoardDao;
import handler.CommandHandler;
import handler.HandlerException;
import member.MemberDao;

@Controller
public class BoardDeleteProHandler implements CommandHandler {
	@Resource
	private MemberDao memberDao;
	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardDeletePro")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! BoardDeletePro";
		int num = Integer.parseInt(request.getParameter("num"));
		String passwd = request.getParameter("passwd");
		String pageNum = request.getParameter("pageNum");	
		
		String id = (String) request.getSession().getAttribute("memid");
		System.out.println(id);
		int idcheck = boardDao.idCheck(id, passwd, num);
		int passwdcheck = 0;
		int result = 0;
		if( idcheck == 1 ) {
			passwdcheck = memberDao.check(id, passwd);
			if( passwdcheck == 1 ) {
				result = boardDao.deleteArticle( num );
				request.setAttribute("result", result);
			} else if ( passwdcheck == -1 ) {
				result = 0;
				request.setAttribute("result", result);
			}
		} else if ( idcheck == -1 ){
			result = 2;
			request.setAttribute("result", result);
		}
		
		request.setAttribute("title", title);
		request.setAttribute("pageNum", pageNum);
		return new ModelAndView("/board/deletePro");
	}

}
