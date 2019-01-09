package film;

import org.apache.ibatis.session.SqlSession;

import bean.SqlMapClient;

public class FilmDBBean implements FilmDao{
	private SqlSession session = SqlMapClient.getSession();

} //class
