package tv.dotstart.badge.controller;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import org.kohsuke.github.GitHub;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;
import tv.dotstart.badge.badge.Badge.Color;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/github")
public class GithubBadgeController {

  private final GitHub api;

  public GithubBadgeController(@NonNull GitHub api) {
    this.api = api;
  }

  /**
   * Creates a badge which displays the location for a given organization.
   *
   * @param ownerName an owner name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/organization/{ownerName}/location")
  public Callable<Badge> organizationLocation(
      @NonNull @PathVariable("ownerName") String ownerName) {
    return () -> {
      try {
        var organization = this.api.getOrganization(ownerName);
        var location = organization.getLocation();

        return new Badge(
            "location",
            location == null ? "none" : location,
            location == null ? Color.FALLBACK : Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "location",
            "no such organization",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the public repository count for a given organization.
   *
   * @param ownerName an owner name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/organization/{ownerName}/repositories")
  public Callable<Badge> organizationRepositories(
      @NonNull @PathVariable("ownerName") String ownerName) {
    return () -> {
      try {
        var organization = this.api.getOrganization(ownerName);

        return new Badge(
            "repositories",
            Integer.toString(organization.getPublicRepoCount()),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "repositories",
            "no such organization",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the total amount of forks for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/activity")
  public Callable<Badge> projectActivity(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);
        var lastPush = repository.getPushedAt();

        if (lastPush == null) {
          return new Badge(
              "activity",
              "none",
              Color.DEFAULT
          );
        }

        var duration = Duration.between(lastPush.toInstant(), Instant.now());
        String value;

        if (duration.toDaysPart() > 0) { // TODO: Move to utility
          value = duration.toDaysPart() + " days ago";
        } else if (duration.toHoursPart() > 0) {
          value = duration.toHoursPart() + " hours ago";
        } else if (duration.toMinutesPart() > 0) {
          value = duration.toMinutesPart() + " minutes ago";
        } else {
          value = "just now";
        }

        return new Badge(
            "activity",
            value,
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
   * Creates a badge which displays the total amount of forks for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/forks")
  public Callable<Badge> projectForks(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);

        return new Badge(
            "forks",
            Integer.toString(repository.getForks()),
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
   * Creates a badge which displays the total amount of open issues for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/issues")
  public Callable<Badge> projectIssues(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);

        return new Badge(
            "issues",
            Integer.toString(repository.getOpenIssueCount()),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "issues",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the main language for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/language")
  public Callable<Badge> projectLanguage(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);
        var language = repository.getLanguage();

        return new Badge(
            "language",
            language == null ? "none" : language.toLowerCase(),
            language == null ? Color.FALLBACK : Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "issues",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the license for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/license")
  public Callable<Badge> projectLicense(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);
        @SuppressWarnings("deprecation") // preview API
            var license = repository.getLicense();

        return new Badge(
            "license",
            license == null ? "none" : license.getName().toLowerCase(),
            license == null ? Color.FALLBACK : Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "license",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the latest release for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/release")
  public Callable<Badge> projectRelease(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);
        var release = repository.getLatestRelease();

        return new Badge(
            "release",
            release == null ? "none" : release.getTagName().toLowerCase(),
            release == null ? Color.FALLBACK : Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "license",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the total amount of stars for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/stars")
  public Callable<Badge> projectStars(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);

        return new Badge(
            "stars",
            Integer.toString(repository.getStargazersCount()),
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
   * Creates a badge which displays the total amount of subscribers for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/subscribers")
  public Callable<Badge> projectSubscribers(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);

        return new Badge(
            "subscribers",
            Integer.toString(repository.getSubscribersCount()),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "subscribers",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }

  /**
   * Creates a badge which displays the total amount of watchers for a given project.
   *
   * @param ownerName an owner name.
   * @param repositoryName a repository name.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{ownerName}/{repositoryName}/watchers")
  public Callable<Badge> projectWatchers(@NonNull @PathVariable("ownerName") String ownerName,
      @NonNull @PathVariable("repositoryName") String repositoryName) {
    return () -> {
      try {
        var repository = this.api.getRepository(ownerName + "/" + repositoryName);

        return new Badge(
            "watchers",
            Integer.toString(repository.getWatchers()),
            Color.DEFAULT
        );
      } catch (FileNotFoundException ex) {
        return new Badge(
            "watchers",
            "no such project",
            Color.FALLBACK
        );
      }
    };
  }
}
