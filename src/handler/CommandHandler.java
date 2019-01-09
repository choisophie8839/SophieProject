//CommandHandler는 모든 Handler클래스의 조상 클래스!
//HandlerException에서 Exception클래스 상속 받음!

package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

//Return값 ModelAndView
public interface CommandHandler {
	public ModelAndView process( HttpServletRequest request, HttpServletResponse response ) 
			throws HandlerException;
}
