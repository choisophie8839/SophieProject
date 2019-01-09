package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import bean.SqlMapClient;

public class BoardDBBean implements BoardDao{
	private SqlSession session = SqlMapClient.getSession();
	
	public int getCount( int re_type ) {
		return session.selectOne( "Board.getCount", re_type );
	}
	
	public int insertArticle( BoardDataBean boardDto ) {
		int num = boardDto.getNum();
		int re_f = boardDto.getRe_f();
		int re_step = boardDto.getRe_step();
		int re_level = boardDto.getRe_level();
		int re_type = boardDto.getRe_type();

		//제목글인 경우 get으로 넘어 온 num이 없음.
		//re_f를 만들어야 하기 때문에 가장 큰 num +1을해서 re_f를 만들어주고
		//만약 글이 하나도 없는 경우 re_f는 1로 해줌.
		//제목글이기 때문에 re_step, re_level, re_type은 모두 0으로!
		if( num == 0 ) {
			if( getCount( re_type ) > 0 ) {
				re_f = (int) session.selectOne("Board.maxNum") + 1;
			} else {
				re_f = 1;
			}
			re_step = 0;
			re_level = 0;
			re_type = 0;
		//답글인 경우 re_f는 그대로 가져와야 하기 때문에 re_step, re_level만 ++해줌.
		//insertReply의 경우 re_f가 같은 것 중에 답글이 이미 존재하는 경우 이전 글의 re_step을 +1해주고 
		//내가 그 자리에 들어감.
		//(아직 작성 안함! 댓글은 항상 답글 안에 있기때문에 re_type이 0이 아닌 경우를 이 안에 작성해줘야 함.
		//어떤 글의 댓글인지 알아야 하기 때문에 re_f는 그대로 받아오고 댓글인지 대댓글인지 알기 위해 re_step과 re_level을
		//새로 정해줘야 할 듯)!
		} else {
			session.update( "Board.insertReply", boardDto );
			re_step ++;
			re_level ++;
		}
		boardDto.setRe_f(re_f);
		boardDto.setRe_step(re_step);
		boardDto.setRe_level(re_level);
		boardDto.setRe_type(re_type);
				
		return session.insert( "Board.insertArticle", boardDto );
	}
	
	public int insertFiles( Map<String, Object> fileMap ) {
		return session.insert("Board.insertFiles", fileMap );
	}
	public List<BoardDataBean> getArticles ( Map<String, Integer> map ) {
		return session.selectList("Board.getArticles", map );
	}
	public BoardDataBean getArticle( int num ) {
		return session.selectOne("Board.getArticle", num );
	}
	public int addCount( int num ) {
		return session.update("Board.addCount", num );
	}
	public List<FileDataBean> checkFiles ( int num ){
		return session.selectList("Board.checkFiles", num );
	}
	public int deleteArticle( int num ) {
		int result = 0;
		
		BoardDataBean boardDto = getArticle( num );
		Map<String, Integer> map= new HashMap<>();
		map.put("re_f", boardDto.getRe_f());
		map.put("re_step", boardDto.getRe_step());
		map.put("re_type", 1);
		
		// re_f가 같고 re_step이 본인보다 1개 크고 re_level이 본인보다 큰 경우 답글 있음.
		int count = session.selectOne("Board.checkReply", boardDto);
		if( count > 0 ) {
			// 답글 있으면 삭제 불가
			result = -1;
		} else {
			// 답글 없으면 삭제 가능
			List<FileDataBean> fileDto = checkFiles(num);
			if( ! fileDto.isEmpty() ) {
				int resultCheck = session.delete("Board.deleteFiles", num);
				if( resultCheck > 0 ) {
					session.update("Board.deleteReply", boardDto );
					result = session.delete("Board.deleteArticle", num );
					session.delete("Board.deleteAllComments", map);
				}				
			} else {
				session.update("Board.deleteReply", boardDto );
				result = session.delete("Board.deleteArticle", num );
				session.delete("Board.deleteAllComments", map);
			}
		}
		
		return result;
	}
	public int idCheck ( String id, String passwd, int num ) {
		int idcheck = 0;
		BoardDataBean boardDto = getArticle( num );
		if(boardDto.getId().equals(id)) {
			idcheck = 1;
		} else {
			idcheck = -1;
		}
		return idcheck;
	}
	public int modifyArticle ( BoardDataBean boardDto ) {
		return session.update("Board.modifyArticle", boardDto);
	}
	public int deleteFiles( int num ) {
		return session.delete("Board.deleteFiles", num );
	}
	public int insertComment( BoardDataBean boardDto ) {
		int re_f = boardDto.getRe_f();
		int re_step = boardDto.getRe_step();
		int re_level = boardDto.getRe_level();
		int re_type = boardDto.getRe_type();
		//System.out.println(re_f + " : " + re_step + " : " + re_level + " : " + re_type);
		Map<String, Integer> map= new HashMap<>();
		map.put("re_f", re_f);
		map.put("re_step", re_step);
		map.put("re_type", re_type);
		int count = countComment(map);
		
		if( count > 0 ) {
			re_level = (int)session.selectOne("Board.maxLevel", map ) + 1;
		} else {
			re_level = 1;
		}
		boardDto.setRe_level(re_level);
		return session.insert( "Board.insertComment", boardDto);
	}
	public int countComment( Map<String, Integer> map ) {
		return session.selectOne( "Board.countComment", map );
	}
	public List<BoardDataBean> showComments ( Map<String, Integer> map ) {
		return session.selectList("Board.showComments", map);
	}
	public int deleteComment( int num ) {
		int result = 0;
		BoardDataBean boardDto = getArticle( num );
		int re_f = boardDto.getRe_f();
		int re_step = boardDto.getRe_step();
		int re_type = boardDto.getRe_type();
		int re_level = boardDto.getRe_level();
		Map<String, Integer> map= new HashMap<>();
		map.put("re_f", re_f);
		map.put("re_step", re_step);
		map.put("re_type", re_type);
		map.put("re_level", re_level);
		
		session.update("Board.arrangeComments", map);
		result = session.delete("Board.deleteComments", num );
		return result;
	}
	public int modifyComment ( BoardDataBean boardDto) {
		return session.update("Board.modifyComment", boardDto);
	}
} //class
