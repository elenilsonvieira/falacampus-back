package br.edu.ifpb.dac.falacampus;
import br.edu.ifpb.dac.falacampus.seleniumTest.SuiteSystemTest;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
@Suite
@Testable
@SelectClasses({
        SuiteSystemTest.class
})
class SuiteSystemTests {
}
