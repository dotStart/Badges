/*
 * Copyright 2018 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tv.dotstart.badge.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;
import tv.dotstart.badge.service.github.Github;

/**
 * Provides a controller which generates badges related to GitHub projects, users and
 * organizations.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/github")
public class GithubBadgeController {

  private final Github api;

  public GithubBadgeController(@NonNull Github api) {
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
    return () -> Badge.create(
        "location",
        this.api.getOrganization(ownerName),
        (org) -> {
          var location = org.getLocation();
          return location == null ? "none" : location;
        },
        "no such organization"
    );
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
    return () -> Badge.create(
        "repositories",
        this.api.getOrganization(ownerName),
        (org) -> {
          return Integer.toString(org.getPublicRepositories());
        },
        "no such organization"
    );
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
    return () -> Badge.create(
        "last activity",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> {
          var lastPush = repo.getPushedAt();

          if (lastPush == null) {
            return "never";
          }

          var duration = Duration.between(lastPush, Instant.now());

          if (duration.toDaysPart() > 0) { // TODO: Move to utility
            return duration.toDaysPart() + " days ago";
          } else if (duration.toHoursPart() > 0) {
            return duration.toHoursPart() + " hours ago";
          } else if (duration.toMinutesPart() > 0) {
            return duration.toMinutesPart() + " minutes ago";
          } else {
            return "just now";
          }
        },
        "no such repository"
    );
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
    return () -> Badge.create(
        "forks",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> Integer.toString(repo.getForks()),
        "no such repository"
    );
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
    return () -> Badge.create(
        "issues",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> Integer.toString(repo.getOpenIssues()),
        "no such repository"
    );
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
    return () -> Badge.create(
        "language",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> {
          var language = repo.getLanguage();

          if (language == null) {
            return "unknown";
          }

          return language.toLowerCase();
        },
        "no such repository"
    );
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
    return () -> Badge.create(
        "license",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> {
          var license = repo.getLicense();

          if (license == null) {
            return "unknown";
          }

          return license.getName().toLowerCase();
        },
        "no such repository"
    );
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
    return () -> Badge.create(
        "latest release",
        this.api.getLatestRelease(ownerName, repositoryName),
        (release) -> {
          if (release == null) {
            return "none";
          }

          return release.getTagName().toLowerCase();
        },
        "no such repository"
    );
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
    return () -> Badge.create(
        "stars",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> Integer.toString(repo.getStargazers()),
        "no such repository"
    );
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
    return () -> Badge.create(
        "subscribers",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> Integer.toString(repo.getSubscriberCount()),
        "no such repository"
    );
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
    return () -> Badge.create(
        "watchers",
        this.api.getRepository(ownerName, repositoryName),
        (repo) -> Integer.toString(repo.getWatchers()),
        "no such repository"
    );
  }
}
