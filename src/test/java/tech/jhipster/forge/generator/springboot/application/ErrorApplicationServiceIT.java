package tech.jhipster.forge.generator.springboot.application;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.forge.TestUtils.*;
import static tech.jhipster.forge.common.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.forge.common.utils.FileUtils.getPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.forge.IntegrationTest;
import tech.jhipster.forge.common.domain.Project;

@IntegrationTest
class ErrorApplicationServiceIT {

  @Autowired
  ErrorApplicationService errorApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();

    errorApplicationService.init(project);

    String pathMain = "src/main/java/com/mycompany/myapp/error/domain";
    assertFileExist(project, getPath(pathMain, "Assert.java"));
    assertFileExist(project, getPath(pathMain, "MissingMandatoryValueException.java"));
    assertFileExist(project, getPath(pathMain, "UnauthorizedValueException.java"));

    String pathTest = "src/test/java/com/mycompany/myapp/error/domain";
    assertFileExist(project, getPath(pathTest, "AssertTest.java"));
    assertFileExist(project, getPath(pathTest, "MissingMandatoryValueExceptionTest.java"));
    assertFileExist(project, getPath(pathTest, "UnauthorizedValueExceptionTest.java"));
  }

  @Test
  void shouldInitWithSpecificPackage() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    errorApplicationService.init(project);

    String packageResult = "package tech.jhipster.chips.error.domain;";

    String pathMain = "src/main/java/tech/jhipster/chips/error/domain";
    assertFileExist(project, getPath(pathMain, "Assert.java"));
    assertFileContent(project, getPath(pathMain, "Assert.java"), packageResult);

    assertFileExist(project, getPath(pathMain, "MissingMandatoryValueException.java"));
    assertFileContent(project, getPath(pathMain, "MissingMandatoryValueException.java"), packageResult);

    assertFileExist(project, getPath(pathMain, "UnauthorizedValueException.java"));
    assertFileContent(project, getPath(pathMain, "UnauthorizedValueException.java"), packageResult);

    String pathTest = "src/test/java/tech/jhipster/chips/error/domain";
    assertFileExist(project, getPath(pathTest, "AssertTest.java"));
    assertFileContent(project, getPath(pathTest, "AssertTest.java"), packageResult);

    assertFileExist(project, getPath(pathTest, "MissingMandatoryValueExceptionTest.java"));
    assertFileContent(project, getPath(pathTest, "MissingMandatoryValueExceptionTest.java"), packageResult);

    assertFileExist(project, getPath(pathTest, "UnauthorizedValueExceptionTest.java"));
    assertFileContent(project, getPath(pathTest, "UnauthorizedValueExceptionTest.java"), packageResult);
  }
}
