package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
@Suite
@Testable
@SelectClasses({
	AuthenticationServiceIntegrationTestIntegrate.class,
	AnswerServiceTest.class,
	DepartamentServiceTest.class,
	CommentServiceTest.class,
	SystemRoleServiceImplTest.class
})
public class SuiteTestIntegrationService {
}
