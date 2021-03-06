package tech.jhipster.forge.generator.springboot.domain.usecase;

import tech.jhipster.forge.common.domain.Project;

public interface LiquibaseService {
  void init(Project project);

  void addLiquibase(Project project);
  void addChangelogMasterXml(Project project);
  void addConfigurationJava(Project project);
}
