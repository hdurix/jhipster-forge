package tech.jhipster.forge.generator.springboot.primary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.forge.common.domain.Project;
import tech.jhipster.forge.generator.springboot.application.ErrorApplicationService;

@RestController
@RequestMapping("/api/error")
@Api(tags = "Error")
public class ErrorResource {

  private final ErrorApplicationService errorApplicationService;

  public ErrorResource(ErrorApplicationService errorApplicationService) {
    this.errorApplicationService = errorApplicationService;
  }

  @ApiOperation("Add Error domain to project")
  @ApiResponses({ @ApiResponse(code = 500, message = "An error occurred while initializing project") })
  @PostMapping
  public void init(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    errorApplicationService.init(project);
  }
}
