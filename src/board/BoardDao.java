package board;

import java.util.List;
import java.util.Map;

public interface BoardDao {
	public int getCount( int re_type );
	public int insertArticle( BoardDataBean boardDto );
	public int insertFiles( Map<String, Object> fileMap );
	public List<BoardDataBean> getArticles ( Map<String, Integer> map );
	public BoardDataBean getArticle( int num );
	public int addCount( int num );
	public List<FileDataBean> checkFiles ( int num );
	public int deleteArticle( int num );
	public int idCheck( String id, String passwd, int num );
	public int modifyArticle( BoardDataBean boardDto );
	public int deleteFiles ( int num );
	public int insertComment( BoardDataBean boardDto );
	public int countComment( Map<String, Integer> map );
	public List<BoardDataBean> showComments ( Map<String, Integer> map );
	public int deleteComment ( int num );
	public int modifyComment( BoardDataBean boardDto );
}
