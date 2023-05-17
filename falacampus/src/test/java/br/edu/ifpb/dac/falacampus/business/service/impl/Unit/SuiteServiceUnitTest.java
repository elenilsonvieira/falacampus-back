package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
	AnswerServiceUnitTest.class,
	PasswordEnconderServiceUnitTest.class,
	AnswerConverterUnitTest.class,
	CommentConverterUnitTest.class,
	DetailsCommentUnitTest.class
})
public class SuiteServiceUnitTest {

}
