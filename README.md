![Apache License, Version 2.0](https://badges.dotstart.tv/github/repository/dotStart/Badges/license)
![Last Activity](http://localhost:8080/github/repository/dotStart/Beacon/activity)
![Latest Release](http://localhost:8080/github/repository/dotStart/Beacon/release)

Project/User/Organization Badges
--------------------------------

A set of (optionally) self hosted badges which display the current state of your profile, repository
or project*.

** More integrations may be added in the future

Requirements
------------

* Linux, Mac OS or Windows 7 (or newer)
* Java 10
* Redis (latest is preferable)

Building
--------

This application makes use of [Apache Maven](https://maven.apache.org/) and may thus be compiled
using a single simple command:

1. Clone the repository from `https://github.com/dotStart/Badges.git` or [download a tarball](https://https://github.com/dotStart/Badges/archive/master.zip)
1. Execute `mvn clean package` from the project root
1. Move the resulting jar archive (`target/Badge.jar`) to a dedicated directory (preferably on a server)
1. Execute Java as documented below

Running
-------

Assuming that you installed all prerequisites and acquired an executable jar file, you may simply
execute `java -jar Badge.jar`. The following configuration parameters may be passed in accordance
with the [Spring Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
as well if you wish to customize the instance further:

| Configuration Property                | Default Value        | Description                                                            |
| ------------------------------------- | -------------------- | ---------------------------------------------------------------------- |
| badge.integration.gitlab.baseUrl      | `https://gitlab.org` | Specifies the location of the target GitLab instance                   |
| badge.integration.gitlab.tokenType    | `PRIVATE_TOKEN`      | Changes the authentication type to use for GitLab queries              |
| badge.integration.gitlab.token        |                      | An arbitrary token with which requests to GitLab will be authenticated |
| badge.integration.github.clientId     |                      | OAuth application identifier for GitHub query authentication           |
| badge.integration.github.clientSecret |                      | OAuth application secret for GitHub query authentication               |

If you wish to display your badges in any larger capacity, it is recommended to at least set up a
GitHub clientId and secret to ensure that the application does not accidentally exceed the anonymous
rate limit.

Additionally, please note that the specified GitLab token may make some of your internal projects
accessible (assuming that the owning user has access to them). This may or may not be desired
behavior (GitLab does not seem to differentiate between authenticated and anonymous queries and thus
you may simply omit the token in most cases).

Browser Compatibility
---------------------

Please note that the application makes use of the SVG format which may not be available within
`<img>` tags in older browser versions. It does not currently support server-side rendering and is
thus not suited for cases where broad compatibility with outdated browsers is required.

License
-------

```
Copyright 2018 <first name> <surname> [<e-mail>]
and other copyright owners as documented in the project's IP log.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
