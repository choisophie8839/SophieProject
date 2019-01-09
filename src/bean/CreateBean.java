package bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import board.BoardDBBean;
import board.BoardDao;
import film.FilmDBBean;
import film.FilmDao;
import member.LogonDBBean;
import member.MemberDao;

@Configuration
public class CreateBean {
//여기에 Dao와 DBBean을 설정해줌.
	@Bean
	public BoardDao boardDao() {
		return new BoardDBBean();
	}
	
	@Bean
	public MemberDao memberDao() {
		return new LogonDBBean();
	}
	
	@Bean
	public FilmDao filmDao() {
		return new FilmDBBean();
	}	
	
	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		//setViewClass의 매개변수가 class임
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
