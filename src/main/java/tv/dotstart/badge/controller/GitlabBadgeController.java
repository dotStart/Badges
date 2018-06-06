package tv.dotstart.badge.controller;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import org.gitlab.api.GitlabAPI;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;
import tv.dotstart.badge.badge.Badge.Color;

/**
 * Provides badges which provide basic information about the status of a GitLab project.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/gitlab")
public class GitlabBadgeController {

  private final GitlabAPI api;

  public GitlabBadgeController(@NonNull GitlabAPI api) {
    this.api = api;
  }

  /**
   * Creates a badge which displays the time since the last logged activity of a given Gitlab
   * project.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/activity")
  public Callable<Badge> activity(@NonNull @PathVariable("projectId") String projectId) {
    return () -> {
      try {
        var project = this.api.getProject(projectId);

        var duration = Duration.between(project.getLastActivityAt().toInstant(), Instant.now());
        String value;

        if (duration.toDaysPart() > 0) {
          value = duration.toDaysPart() + " days ago";
        } else if (duration.toHoursPart() > 0) {
          value = duration.toHoursPart() + " hours ago";
        } else if (duration.toMinutesPart() > 0) {
          value = duration.toMinutesPart() + " minutes ago";
        } else {
          value = "just now";
        }

        return new Badge(
            "last activity",
            value,
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "stars",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the total amount of forks of a given Gitlab project.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/forks")
  public Callable<Badge> forks(@NonNull @PathVariable("projectId") String projectId) {
    return () -> {
      try {
        var project = this.api.getProject(projectId);

        return new Badge(
            "forks",
            project.getForksCount().toString(),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "forks",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the total amount of stars a given Gitlab project has received.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/stars")
  public Callable<Badge> stars(@NonNull @PathVariable("projectId") String projectId) {
    return () -> {
      try {
        var project = this.api.getProject(projectId);

        return new Badge(
            "stars",
            project.getStarCount().toString(),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "stars",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }
}
