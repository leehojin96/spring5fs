package spring;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class MemberService {

	@Autowired
	private MemberDao memberDao;

	@Transactional
	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
	
	
	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}
	
	@Transactional
	public void removeMember(long id,String email,String password) {
		Member member = memberDao.selectByEmail(email);

		if(!member.getId().equals(id)||!member.getEmail().equals(email)||!member.getPassword().equals(password)) {
			throw new WrongIdPwdNameException();
		}
		
		memberDao.removeMember(member);
	}
	
	


}
