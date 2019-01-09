package member;

public interface MemberDao {
	public int insertMember( LogonDataBean memberDto );
	public int check( String id, String passwd );
	public LogonDataBean getMember( String id );
	public int modifyMember( LogonDataBean memberDto );
	public int deleteMember( String id );
}
