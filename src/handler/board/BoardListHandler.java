package handler.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.BoardDao;
import board.BoardDataBean;
import handler.CommandHandler;
import handler.HandlerException;

@Controller
public class BoardListHandler implements CommandHandler {
	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardList")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! list!";
		
		int pageSize = 5;
		int pageBlock = 3;
		
		int count = 0;	//총 글 수
		int start = 0;	
		int end = 0;
		String pageNum = null;
		int currentPage = 0;
		int number = 0;
		
		int startPage = 0;
		int endPage = 0;
		int pageCount = 0;
		
		int re_type = 0;
		
		count = boardDao.getCount( re_type );
		// pageNum get방식으로 넘겨받음. 없으면 pageNum=1
		pageNum = request.getParameter("pageNum");
		if(pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		
		currentPage = Integer.parseInt( pageNum );
		// 1) 총 page 수 계산!
		pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
		// 글을 삭제하는 경우 현재 페이지가 pageCount보다 클 수 있음. 그런 경우 대비해서 같게 해줌.
		if( currentPage > pageCount ) currentPage = pageCount;
		
		// 2) start는 그 페이지에서 보이는 글번호 시작, end는 글번호 끝.
		start = (currentPage - 1)*pageSize + 1; 	// (1 - 1)*5 + 1 = 1
		end = start + pageSize - 1;					// (1 + 5) - 1 = 5;		
		//end는 5인데 실제 글 수는 5보다 작을 수 있음!
		if( end > count ) end = count;
		
		// 3) 현재 페이지에서 보이는 가장 위의 글번호가 number
		number = count - ( currentPage - 1 )*pageSize;	// 4 - ( 1 - 1 )*5 = 4
														// 35 - ( 1 - 1 )*5 = 35
														// 35 - ( 3 - 1 )*5 = 25
														// 6 - ( 2 - 1 ) *5 = 1
		// 4) startPage는 현재 아래에 보이는 페이지 
		startPage = ( currentPage / pageBlock )*pageBlock + 1;	// ( 1 / 3 )*3 + 1 = 1
																// ( 4 / 3 )*3 + 1 = 4
		if( currentPage % pageBlock == 0 ) startPage -= pageBlock;	// ( 3 / 3 )*3 + 1 = 4이면 안됨! 1이어야 함!
																// 그래서 pageBlock만큼 빼 줌
		endPage = startPage + pageBlock -1;
		
		if( endPage > pageCount ) endPage = pageCount;
		
		request.setAttribute("title", title);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("number", number);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageBlock", pageBlock);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		map.put("re_type", re_type);
		
		if ( count > 0 ) {
			List<BoardDataBean> articles = boardDao.getArticles( map );
			request.setAttribute( "articles", articles );
		}
		
		return new ModelAndView("/board/list");
	}

}
