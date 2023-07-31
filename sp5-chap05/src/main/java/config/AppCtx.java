package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import spring.MemberDao;
import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"spring"}
//,excludeFilters = @Filter(type = FilterType.REGEX, pattern="spring\\..*Dao")
//,excludeFilters = @Filter(type = FilterType.ASPECTJ, pattern="spring.*Dao")
//,excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = {NoProduct.class,ManualBean.class})
//,excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,classes = MemberDao.class)
//,excludeFilters = {@Filter(type = FilterType.ANNOTATION,classes = ManualBean.class),@Filter(type = FilterType.REGEX,pattern = "spring2\\..*")}
)
public class AppCtx {

//	@Bean
//	public MemberDao memberDao() {
//		return new MemberDao();
//	}
	
//	@Bean
//	public MemberRegisterService memberRegSvc() {
//		return new MemberRegisterService();
//	}
	
//	@Bean
//	public ChangePasswordService changePwdSvc() {
//		ChangePasswordService pwdSvc = new ChangePasswordService();
//		//pwdSvc.setMemberDao(memberDao());
//		return pwdSvc;
//	}
	
	@Bean
	@Qualifier("printer")
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
	

//	@Bean
//	public MemberListPrinter listPrinter() {
//		return new MemberListPrinter();
//	}
	
//	@Bean
//	public MemberInfoPrinter infoPrinter() {
//		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//		return infoPrinter;
//	}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
