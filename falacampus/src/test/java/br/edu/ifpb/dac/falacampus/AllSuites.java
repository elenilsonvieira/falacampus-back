package br.edu.ifpb.dac.falacampus;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        SuiteSystemTests.class,
        SuiteIntegrateTest.class,
        SuiteUnitTests.class
})
public class AllSuites {
}
