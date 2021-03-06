package tech.jhipster.forge.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProject;
import static tech.jhipster.forge.common.domain.DefaultConfig.*;
import static tech.jhipster.forge.common.utils.FileUtils.getPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.utils.FileUtils;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.error.domain.UnauthorizedValueException;

@UnitTest
class ProjectTest {

  @Nested
  class Build {

    @Test
    void shouldBuild() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).build();

      assertThat(project.getPath()).isEqualTo(path);
      assertThat(project.getConfig()).isEqualTo(Map.of());
    }

    @Test
    void shouldBuildWithConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).config(Map.of(PROJECT_NAME, "JHipster Forge")).build();

      assertThat(project.getPath()).isEqualTo(path);
      assertThat(project.getConfig()).isEqualTo(Map.of(PROJECT_NAME, "JHipster Forge"));
    }

    @Test
    void shouldBuildWithNullConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).config(null).build();

      assertThat(project.getPath()).isEqualTo(path);
      assertThat(project.getConfig()).isEqualTo(Map.of());
    }

    @Test
    void shouldNotBuildWithNullPath() {
      Project.ProjectBuilder builder = Project.builder().path(null);

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("path");
    }

    @Test
    void shouldNotBuildWithBlankPath() {
      Project.ProjectBuilder builder = Project.builder().path(" ");

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("path");
    }
  }

  @Nested
  class GetConfig {

    @Test
    void shouldGetConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).config(Map.of(PROJECT_NAME, "JHipster Forge")).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");

      assertThat(project.getConfig(BASE_NAME)).isEmpty();
      assertThat(project.getConfig("projectname")).isEmpty();
      assertThat(project.getConfig("test")).isEmpty();
    }
  }

  @Nested
  class AddConfig {

    @Test
    void shouldAddConfigInEmptyConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).build();

      assertThat(project.getConfig("apero")).isEmpty();

      project.addConfig("apero", "beer");
      assertThat(project.getConfig("apero")).contains("beer");

      project.addConfig("apero", "chips");
      assertThat(project.getConfig("apero")).contains("beer");
    }

    @Test
    void shouldAddConfigInExistingConfig() {
      // Given
      String path = FileUtils.tmpDirForTest();
      Map<String, Object> config = new HashMap<>(Map.of(PROJECT_NAME, "JHipster Forge"));
      Project project = Project.builder().path(path).config(config).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");
      assertThat(project.getConfig("apero")).isEmpty();

      // When + Then
      project.addConfig("apero", "beer");
      assertThat(project.getConfig("apero")).contains("beer");

      // When + Then
      project.addConfig(PROJECT_NAME, "chips");
      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");
    }

    @Test
    void shouldNotAddConfigWithBadBaseName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> project.addConfig(BASE_NAME, "jhipster with space"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining(BASE_NAME);
    }

    @Test
    void shouldNotAddConfigWithBadPackageName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> project.addConfig(PACKAGE_NAME, "tech jhipster forge"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining(PACKAGE_NAME);
    }

    @Test
    void shouldAddDefaultConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).build();

      project.addDefaultConfig(BASE_NAME);

      assertThat(project.getConfig(BASE_NAME)).contains("jhipster");
    }

    @Test
    void shouldNotAddDefaultConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().path(path).build();

      project.addDefaultConfig("apero");

      assertThat(project.getConfig("apero")).isEmpty();
    }
  }

  @Nested
  class BaseName {

    @Test
    void shouldGetBaseName() {
      Project project = tmpProject();

      project.addConfig(BASE_NAME, "JHipster");

      assertThat(project.getBaseName()).contains("JHipster");
    }

    @Test
    void shouldNotGetBaseNameWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getBaseName()).isEmpty();
    }
  }

  @Nested
  class PackageName {

    @Test
    void shouldGetPackageName() {
      Project project = tmpProject();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.forge");

      assertThat(project.getPackageName()).contains("tech.jhipster.forge");
    }

    @Test
    void shouldNotGetPackageNameWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getPackageName()).isEmpty();
    }

    @Test
    void shouldGetPackageNamePath() {
      Project project = tmpProject();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.forge");

      assertThat(project.getPackageNamePath()).contains(getPath("tech/jhipster/forge"));
    }

    @Test
    void shouldNotGetPackageNamePathForDefault() {
      Project project = tmpProject();

      assertThat(project.getPackageNamePath()).isEmpty();
    }
  }

  @Nested
  class GetStringConfig {

    @Test
    void shouldGetStringConfig() {
      Project project = tmpProject();

      project.addConfig("apero", "beer");

      assertThat(project.getStringConfig("apero")).contains("beer");
    }

    @Test
    void shouldGetStringConfigWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getStringConfig("apero")).isEmpty();
    }

    @Test
    void shouldNotGetStringConfig() {
      Project project = tmpProject();

      project.addConfig("apero", List.of("beer"));

      assertThatThrownBy(() -> project.getStringConfig("apero"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("apero");
    }
  }

  @Nested
  class GetIntegerConfig {

    @Test
    void shouldGetIntegerConfig() {
      Project project = tmpProject();

      project.addConfig("serverPort", 1337);

      assertThat(project.getIntegerConfig("serverPort")).contains(1337);
    }

    @Test
    void shouldGetIntegerConfigWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getIntegerConfig("serverPort")).isEmpty();
    }

    @Test
    void shouldNotGetIntegerConfig() {
      Project project = tmpProject();

      project.addConfig("serverPort", List.of(1337));

      assertThatThrownBy(() -> project.getIntegerConfig("serverPort"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("serverPort");
    }
  }
}
