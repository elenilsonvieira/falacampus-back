package br.edu.ifpb.dac.falacampus.seleniumTest;

import br.edu.ifpb.dac.falacampus.presentation.control.Inte.AnswerControllTest;
import br.edu.ifpb.dac.falacampus.presentation.control.Inte.AuthenticationControllTest;
import br.edu.ifpb.dac.falacampus.presentation.control.Inte.CommentControllTest;
import br.edu.ifpb.dac.falacampus.presentation.control.Inte.UserControllTest;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        HomeTest.class,
        LoginTest.class
})
public class SuiteSystemTest {
}
