package Suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import Domain.UsuarioTest;
import Service.UsuarioServiceTest;

@Suite
@SuiteDisplayName("Suite de Testes")
//@SelectClasses(value = {UsuarioTest.class,UsuarioServiceTest.class})
@SelectPackages("Service")
public class SuiteTest {

}
