package handler.board;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import board.BoardDao;
import board.BoardDataBean;
import board.FileDataBean;
import handler.CommandHandler;
import handler.HandlerException;

@Controller
public class BoardContentHandler implements CommandHandler {

	@Resource
	private BoardDao boardDao;
	
	@RequestMapping("/BoardContent")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! BoardContent!";
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String number = request.getParameter("number");
		
		BoardDataBean boardDto = boardDao.getArticle( num );
		List<FileDataBean> fileDto = boardDao.checkFiles(num);
		if( ! fileDto.isEmpty()) {
			request.setAttribute("fileDto", fileDto);
		}
		if( ! boardDto.getId().equals((String)request.getSession().getAttribute("memid")) ) {
			boardDao.addCount( num );
		}
		request.setAttribute("title", title);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("number", number);
		request.setAttribute("boardDto",boardDto);
		return new ModelAndView("/board/content");
	}
	
	@RequestMapping(value="/addPost.eh", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public int addPost(@RequestBody String commentform) {
		try {
			commentform = URLDecoder.decode(commentform,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(commentform);
		Map<String, String> commentmap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			commentmap = mapper.readValue(commentform, new TypeReference<Map<String, String>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int re_level = 0;	// 글레벨
		
		BoardDataBean boardDto = new BoardDataBean();
		boardDto.setNum(Integer.parseInt(commentmap.get("num")));
		boardDto.setId(commentmap.get("id"));
		boardDto.setRe_f(Integer.parseInt(commentmap.get("re_f")));
		boardDto.setRe_step(Integer.parseInt(commentmap.get("re_step")));
		boardDto.setRe_level(re_level);
		boardDto.setRe_type(Integer.parseInt(commentmap.get("re_type")));
		boardDto.setContent(commentmap.get("content"));
		boardDto.setReg_date(new Timestamp( System.currentTimeMillis()));
		boardDto.setMovienm(commentmap.get("movienm"));
		
		int result = boardDao.insertComment(boardDto);
		
		return result;
	}
	
	@RequestMapping(value="/showComment.eh", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public String showComment(@RequestBody String commentdata) {
		try {
			commentdata = URLDecoder.decode(commentdata,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Integer> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(commentdata, new TypeReference<Map<String, Integer>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int result = boardDao.countComment(map);
		String commentresult = "";
		if( result==0 ) {
			commentresult = "no comment";
		} else {
			List<BoardDataBean> comments = boardDao.showComments(map);
			Gson gsonBuilder = new GsonBuilder().create();
			commentresult = gsonBuilder.toJson(comments);
		}
		return commentresult;
	}
	@RequestMapping(value="/commentDelete.eh", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public int commentDelete(@RequestBody String deletenum) {
		try {
			deletenum = URLDecoder.decode(deletenum,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String getnum = "";
		for(int i = 0 ; i < deletenum.length(); i++) {    
            // 48 ~ 57은 아스키 코드로 0~9이다.
            if(48 <= deletenum.charAt(i) && deletenum.charAt(i) <= 57) 
            	getnum += deletenum.charAt(i);
        	}
		int num = Integer.parseInt(getnum);
		int commentdelresult = boardDao.deleteComment(num);
		return commentdelresult;
	}
	@RequestMapping(value="/commentModifyForm.eh", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public String commentModifyForm(@RequestBody String modifynum) {
		try {
			modifynum = URLDecoder.decode(modifynum,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String getnum = "";
		for(int i = 0 ; i < modifynum.length(); i++) {    
            // 48 ~ 57은 아스키 코드로 0~9이다.
            if(48 <= modifynum.charAt(i) && modifynum.charAt(i) <= 57) 
            	getnum += modifynum.charAt(i);
        	}
		int num = Integer.parseInt(getnum);
		BoardDataBean boardDto = boardDao.getArticle( num );
		Gson gsonBuilder = new GsonBuilder().create();
		String commentresult = gsonBuilder.toJson(boardDto);
		//System.out.println(commentresult);
		return commentresult;
	}
	
	@RequestMapping(value="/commentModify.eh", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public int commentModify(@RequestBody String modifyform) {
		try {
			modifyform = URLDecoder.decode(modifyform,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(modifyform);
		Map<String, String> commentmap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			commentmap = mapper.readValue(modifyform, new TypeReference<Map<String, String>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int re_type = 1;	// 글 type
		
		BoardDataBean boardDto = new BoardDataBean();
		boardDto.setNum(Integer.parseInt(commentmap.get("num")));
		boardDto.setRe_type(re_type);
		boardDto.setContent(commentmap.get("content"));
		boardDto.setReg_date(new Timestamp( System.currentTimeMillis()));
		
		int result = boardDao.modifyComment(boardDto);
		
		return result;
	}
}
