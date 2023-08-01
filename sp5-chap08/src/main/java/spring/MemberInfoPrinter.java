package spring;

public class MemberInfoPrinter {

	private MemberDao memDao;
	private MemberPrinter printer;

	public boolean printMemberInfo(String email) {
		Member member = memDao.selectByEmail(email);
		if (member == null) {
			System.out.println("데이터 없음\n");
			return true;
		}
		printer.print(member);
		return false;
	}
	
	

	public void setMemberDao(MemberDao memberDao) {
		this.memDao = memberDao;
	}

	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}

}
