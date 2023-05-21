package br.edu.ifpb.dac.falacampus.presentation.control.Inte;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        UserControllTest.class,
        AnswerControllTeste.class,
        AuthenticationControllTest.class
})
public class SuiteControllTest {
}
