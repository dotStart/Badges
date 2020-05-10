![Apache License, Version 2.0](https://badges.dotstart.tv/v1/github/repository/dotStart/Badges/license)
![Last Activity](https://badges.dotstart.tv/v1/github/repository/dotStart/Badges/activity)
![Latest Release](https://badges.dotstart.tv/v1/github/repository/dotStart/Badges/release)

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
| badge.connector.discord.enabled       | `true`               | En- or disables Discord widgets                                        |
| badge.connector.github.enabled        | `true`               | En- or disables GitHub widgets                                         |
| badge.connector.github.clientId       |                      | OAuth application identifier for GitHub query authentication           |
| badge.connector.github.clientSecret   |                      | OAuth application secret for GitHub query authentication               |
| badge.connector.gitlab.enabled        | `true`               | En- or disables GitLab widgets                                         |
| badge.connector.gitlab.baseUrl        | `https://gitlab.org` | Specifies the location of the target GitLab instance                   |
| badge.legacy.enabled                  | `true`               | En- or disables legacy endpoints                                       |

If you wish to display your badges in any larger capacity, it is recommended to at least set up a
GitHub clientId and secret to ensure that the application does not accidentally exceed the anonymous
rate limit.

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
