package br.edu.ifpb.dac.falacampus;

import br.edu.ifpb.dac.falacampus.business.service.impl.Inte.SuiteTestIntegrationService;
import br.edu.ifpb.dac.falacampus.business.service.impl.Unit.SuiteServiceUnitTest;
import br.edu.ifpb.dac.falacampus.model.entity.SuiteTestEntity;
import br.edu.ifpb.dac.falacampus.presentation.control.Inte.SuiteControllTest;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        SuiteTestIntegrationService.class,
        SuiteControllTest.class
})
public class SuiteIntegrateTest {
}
