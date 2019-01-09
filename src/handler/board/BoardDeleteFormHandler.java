package handler.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import handler.CommandHandler;
import handler.HandlerException;

@Controller
public class BoardDeleteFormHandler implements CommandHandler {
	@RequestMapping("/BoardDeleteForm")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! BoardDeleteForm!";
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");

		request.setAttribute("title", title);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
		return new ModelAndView("/board/deleteForm");
	}

}
