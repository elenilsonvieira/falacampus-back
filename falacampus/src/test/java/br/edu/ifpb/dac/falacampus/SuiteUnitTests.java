package br.edu.ifpb.dac.falacampus;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.edu.ifpb.dac.falacampus.model.entity.SuiteTestEntity;

@Suite
@Testable
@SelectClasses({SuiteTestEntity.class})
public class SuiteUnitTests {

}
