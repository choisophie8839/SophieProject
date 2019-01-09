package handler.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import handler.CommandHandler;
import handler.HandlerException;

@Controller
public class BoardWriteFormHandler implements CommandHandler {
	@RequestMapping("/BoardWriteForm")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! writeForm!";
		
		int num = 0;		// 제목글 0 / 답변글 !0
		int re_f = 0;		// 그룹화아이디
		int re_step = 0;	// 글순서
		int re_level = 0;	// 글레벨
		int re_type = 0;	// 출력위치
		
		//답글의 경우
		if( request.getParameter("num") != null ) {
			num = Integer.parseInt(request.getParameter("num"));
			re_f = Integer.parseInt(request.getParameter("re_f"));
			re_step = Integer.parseInt(request.getParameter("re_step"));
			re_level = Integer.parseInt(request.getParameter("re_level"));
			re_type = Integer.parseInt(request.getParameter("re_type"));
		} 
		
		request.setAttribute("title", title);
		
		request.setAttribute("num", num);
		request.setAttribute("re_f", re_f);
		request.setAttribute("re_step", re_step);
		request.setAttribute("re_level", re_level);
		request.setAttribute("re_type", re_type);
			
		return new ModelAndView("/board/writeForm");
	}

}
