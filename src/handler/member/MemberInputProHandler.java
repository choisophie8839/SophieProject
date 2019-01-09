package handler.member;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;

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
public class MemberInputProHandler implements CommandHandler {
	@Resource
	private MemberDao memberDao;
	
	@RequestMapping("/InputPro")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! InputPro!";
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogonDataBean memberDto = new LogonDataBean();
		memberDto.setId(request.getParameter("id"));
		memberDto.setPasswd(request.getParameter("passwd"));
		memberDto.setEmail(request.getParameter("email"));
		memberDto.setTel(request.getParameter("tel"));
		Date birth = Date.valueOf(request.getParameter("birth"));
		memberDto.setBirth(birth);
		memberDto.setGender(request.getParameter("gender").charAt(0));
		memberDto.setAddress1(request.getParameter("address1"));
		memberDto.setAddress2(request.getParameter("address2"));
		memberDto.setReg_date(new Timestamp( System.currentTimeMillis() ));
		
		int result = memberDao.insertMember( memberDto );
		
		request.setAttribute("title", title );
		request.setAttribute("result", result);
		return new ModelAndView("/member/inputPro");
	}

}
