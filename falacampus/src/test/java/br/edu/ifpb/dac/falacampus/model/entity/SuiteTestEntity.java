package br.edu.ifpb.dac.falacampus.model.entity;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
	UserTest.class,
	DepartamentTest.class,
	SystemRoleTest.class,
	AnswerTest.class
})
public class SuiteTestEntity {

}
