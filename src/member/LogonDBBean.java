package member;

import org.apache.ibatis.session.SqlSession;

import bean.SqlMapClient;


public class LogonDBBean implements MemberDao {
	private SqlSession session = SqlMapClient.getSession();
	
	public int insertMember( LogonDataBean memberDto ) {
		return session.insert( "Member.insertMember", memberDto );
	}
	
	public int checkId( String id ) {
		return session.selectOne( "Member.checkId", id );
	}
	
	public int check( String id, String passwd ) {
		int result = 0;
		if( checkId(id) > 0 ) {
			LogonDataBean memberDto = getMember(id);
			if( passwd.equals( memberDto.getPasswd() )) {
				result = 1;
			} else {
				result = -1;
			}
		} else {
			result = 0;
		}
		return result;
	}
	
	public LogonDataBean getMember( String id ) {
		return session.selectOne( "Member.getMember", id ); 
	} 
	public int modifyMember( LogonDataBean memberDto ) {
		return session.update( "Member.modifyMember", memberDto );
	}
	public int deleteMember( String id ) {
		return session.delete( "Member.deleteMember", id );
	}

} // class
