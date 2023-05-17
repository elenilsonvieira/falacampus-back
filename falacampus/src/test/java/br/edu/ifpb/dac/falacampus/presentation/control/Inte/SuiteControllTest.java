package br.edu.ifpb.dac.falacampus.presentation.control.Inte;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        UserControllerTest.class,
})
public class SuiteControllTest {
}
