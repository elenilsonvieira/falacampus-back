package br.edu.ifpb.dac.falacampus.seleniumTest;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@Testable
@SelectClasses({
        HomeTest.class,
        //EditUserTest.class,
        CommentTest.class,
        AnswerTest.class,
        //LoginTest.class,

})
public class SuiteSystemTest {

}
