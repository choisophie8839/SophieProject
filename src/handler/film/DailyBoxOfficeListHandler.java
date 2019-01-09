package handler.film;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import handler.CommandHandler;
import handler.HandlerException;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.GenreData;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.MovieAPIServiceImplService;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.MovieInfoResult;
import kr.or.kobis.kobisopenapi.consumer.soap.movie.NationData;

@Controller
public class DailyBoxOfficeListHandler implements CommandHandler {
	@RequestMapping("/DailyBoxOfficeList")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {

		String title = "Hi! DailyBoxOfficeList";
		String targetDt = "20181213";
		try {
		while (! targetDt.equals("20190101")) {
		//String targetDt = request.getParameter("targetDt")==null?"20180101":request.getParameter("targetDt");			//조회일자
		//String itemPerPage = request.getParameter("itemPerPage")==null?"10":request.getParameter("itemPerPage");		//결과row수
		//String multiMovieYn = request.getParameter("multiMovieYn")==null?"":request.getParameter("multiMovieYn");		//“Y” : 다양성 영화 “N” : 상업영화 (default : 전체)
		//String repNationCd = request.getParameter("repNationCd")==null?"":request.getParameter("repNationCd");			//“K: : 한국영화 “F” : 외국영화 (default : 전체)
		//String wideAreaCd = request.getParameter("wideAreaCd")==null?"":request.getParameter("wideAreaCd");				//“0105000000” 로서 조회된 지역코드
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("targetDt", targetDt);
		// 발급키
		String key = "567c6d00904f4335c83b999bbff19a15";
		// KOBIS 오픈 API Rest Client를 통해 호출
	    KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
	    
	 // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
	    String dailyResponse;
		
			//dailyResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);

			dailyResponse = service.getDailyBoxOffice(true, paramMap);
			//System.out.println("dailyResponse : "+dailyResponse);
			JsonObject object = new JsonParser().parse(dailyResponse).getAsJsonObject();
			JsonArray dailyBoxOfficeList = object.get("boxOfficeResult").getAsJsonObject().get("dailyBoxOfficeList").getAsJsonArray();
			File file = new File("Y:\\film\\"+ targetDt + ".txt");
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
			// 일일 박스오피스 출력
			for( int i=0; i<dailyBoxOfficeList.size(); i++ ) {
				String rank = dailyBoxOfficeList.get(i).getAsJsonObject().get("rank").getAsString();
				String rankOldAndNew  = dailyBoxOfficeList.get(i).getAsJsonObject().get("rankOldAndNew").getAsString(); 
				String movieCd = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").getAsString();
				String openDt = dailyBoxOfficeList.get(i).getAsJsonObject().get("openDt").getAsString();
				String salesAmt = dailyBoxOfficeList.get(i).getAsJsonObject().get("salesAmt").getAsString();
				String audiCnt = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiCnt").getAsString();
				String salesAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("salesAcc").getAsString();
				String audiAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiAcc").getAsString();
				String scrnCnt = dailyBoxOfficeList.get(i).getAsJsonObject().get("scrnCnt").getAsString();
				String showCnt = dailyBoxOfficeList.get(i).getAsJsonObject().get("showCnt").getAsString();
				MovieInfoResult movieInfoResult = new MovieAPIServiceImplService().getMovieAPIServiceImplPort().searchMovieInfo( key, movieCd );
				String movieNm = movieInfoResult.getMovieInfo().getMovieNm();
				List<GenreData> genrelist = movieInfoResult.getMovieInfo().getGenres().getGenre();
				out.write( targetDt + "," + rank + "," + rankOldAndNew + "," + movieCd + "," + movieNm + "," + openDt +"," + salesAmt + "," + salesAcc + "," + audiAcc + "," + audiCnt + "," + scrnCnt + "," + showCnt + ",");
				for(int j=0; j<genrelist.size(); j++ ) {
					out.write(genrelist.get(j).getGenreNm() + " ");
				}
				out.println();			
			}
			out.flush();
			out.close();

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = format.parse(targetDt);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			targetDt = format.format(cal.getTime());

		}
/*			 // Json 라이브러리를 통해 Handling
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String,Object> dailyResult = mapper.readValue(dailyResponse, HashMap.class);
			//System.out.println(dailyResult.get("boxOfficeResult"));
						
			request.setAttribute("dailyResult",dailyResult);
*/			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("title", title);
		
		return new ModelAndView("/film/dailyboxofficelist");
	}

}
