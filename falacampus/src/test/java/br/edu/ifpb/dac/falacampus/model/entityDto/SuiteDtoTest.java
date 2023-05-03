package br.edu.ifpb.dac.falacampus.model.entityDto;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
	AnswerDtoTest.class,
	DepartamentDtoTest.class
	})
public class SuiteDtoTest {

}
