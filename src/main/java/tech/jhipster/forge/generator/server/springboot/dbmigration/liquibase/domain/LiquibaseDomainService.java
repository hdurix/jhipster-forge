package tech.jhipster.forge.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.forge.generator.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.generator.project.domain.Constants.*;

import tech.jhipster.forge.generator.project.domain.BuildToolRepository;
import tech.jhipster.forge.generator.project.domain.Dependency;
import tech.jhipster.forge.generator.project.domain.Project;
import tech.jhipster.forge.generator.project.domain.ProjectRepository;
import tech.jhipster.forge.generator.server.springboot.core.domain.SpringBootService;

public class LiquibaseDomainService implements LiquibaseService {

  public static final String SOURCE = "liquibase";
  public static final String LIQUIBASE_PATH = "technical/secondary/liquibase";

  public final ProjectRepository projectRepository;
  public final BuildToolRepository buildToolRepository;
  public final SpringBootService springBootService;

  public LiquibaseDomainService(
    ProjectRepository projectRepository,
    BuildToolRepository buildToolRepository,
    SpringBootService springBootService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolRepository = buildToolRepository;
    this.springBootService = springBootService;
  }

  @Override
  public void init(Project project) {
    addLiquibase(project);
    addChangelogMasterXml(project);
    addConfigurationJava(project);
  }

  @Override
  public void addLiquibase(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.liquibase").artifactId("liquibase-core").build();
    buildToolRepository.addDependency(project, dependency);
  }

  @Override
  public void addChangelogMasterXml(Project project) {
    projectRepository.add(project, getPath(SOURCE, "resources"), "master.xml", getPath(MAIN_RESOURCES, "config/liquibase"));
  }

  @Override
  public void addConfigurationJava(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToLiquibase(project, packageNamePath, "src", "AsyncSpringLiquibase.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "LiquibaseConfiguration.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "SpringLiquibaseUtil.java", MAIN_JAVA);

    templateToLiquibase(project, packageNamePath, "test", "AsyncSpringLiquibaseTest.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "LiquibaseConfigurationIT.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "LogbackRecorder.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "SpringLiquibaseUtilTest.java", TEST_JAVA);
  }

  private void templateToLiquibase(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, LIQUIBASE_PATH));
  }
}