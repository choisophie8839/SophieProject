package handler.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import handler.CommandHandler;
import handler.HandlerException;
import member.MemberDao;

@Controller
public class MemberLoginProHandler implements CommandHandler {
	@Resource
	private MemberDao memberDao;
	
	@RequestMapping("/LoginPro")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! LoginPro!";
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		int result = memberDao.check(id, passwd);

		request.setAttribute("title", title);
		request.setAttribute("result", result);
		request.setAttribute("id", id);

		return new ModelAndView("/member/loginPro");
	}

}
