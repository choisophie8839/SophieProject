package handler.film;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class MovieInfoHandler implements CommandHandler {
	@RequestMapping("/MovieInfo")
	@Override
	public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		String title = "Hi! MovieInfo";
		String targetDt = request.getParameter("targetDt")==null?"20181206":request.getParameter("targetDt");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("targetDt", targetDt);
		String key = "67fceb2ea9049bd461ce3590966fe259";
		KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
		String dailyResponse;

		try {
			dailyResponse = service.getDailyBoxOffice(true, paramMap);
			JsonObject object = new JsonParser().parse(dailyResponse).getAsJsonObject();
			JsonArray dailyBoxOfficeList = object.get("boxOfficeResult").getAsJsonObject().get("dailyBoxOfficeList").getAsJsonArray();
			File file = new File("Y:\\filmdetail\\filmdetail.txt");
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			// box office 내의 영화 정보 출력
			for( int i=0; i<dailyBoxOfficeList.size(); i++ ) {
				String movieCd = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieCd").getAsString();
				MovieInfoResult movieInfoResult = new MovieAPIServiceImplService().getMovieAPIServiceImplPort().searchMovieInfo( key, movieCd );
				String movieNm = movieInfoResult.getMovieInfo().getMovieNm();
				String movieNmOg = movieInfoResult.getMovieInfo().getMovieNmOg();
				String prdtYear = movieInfoResult.getMovieInfo().getPrdtYear();
				String showTm = movieInfoResult.getMovieInfo().getShowTm();
				String openDt = movieInfoResult.getMovieInfo().getOpenDt();
				out.write(movieCd + "," + movieNm  + "," + movieNmOg + "," + prdtYear + "," + showTm + "," + openDt + "," );
				List<NationData> nationlist = movieInfoResult.getMovieInfo().getNations().getNation();
				for(int j=0; j<nationlist.size(); j++ ) {
					out.write(nationlist.get(j).getNationNm()+ " ");
				}
				out.write( "," );
				List<GenreData> genrelist = movieInfoResult.getMovieInfo().getGenres().getGenre();
				for(int k=0; k<genrelist.size(); k++ ) {
					out.write(genrelist.get(k).getGenreNm() + " ");
				}
				
				out.println();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("title", title);
		return new ModelAndView("/film/movieinfo");
	}

}
