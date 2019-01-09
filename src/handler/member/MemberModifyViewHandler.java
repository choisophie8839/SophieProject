package handler.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import handler.CommandHandler;
import handler.HandlerException;
import member.LogonDataBean;
import member.MemberDao;

@Controller
public class MemberModifyViewHandler implements CommandHandler {
	@Resource
	private MemberDao memberDao;
	
	@RequestMapping("/ModifyView")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! ModifyView";
		String id = (String)request.getSession().getAttribute("memid");
		String passwd = request.getParameter("passwd");
		int result = memberDao.check(id, passwd);
		LogonDataBean memberDto = new LogonDataBean();
		if ( result > 0) {
			memberDto = memberDao.getMember(id);
		}
		request.setAttribute("title", title);
		request.setAttribute("result", result);
		request.setAttribute("memberDto", memberDto);
		return new ModelAndView("/member/modifyView");
	}

}
