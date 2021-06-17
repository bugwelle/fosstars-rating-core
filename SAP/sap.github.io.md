**Status**: **Failed**

**Confidence**: Low (8.95, max confidence value is 10.0)

## Violated rules

1.  **[rl-assigned_teams-1]** Does it have enough teams on GitHub? **No**
1.  **[rl-assigned_teams-2]** Does it have an admin team on GitHub? **No**
1.  **[rl-assigned_teams-3]** Does it have enough admins on GitHub? **No**
1.  **[rl-assigned_teams-4]** Does it have a team with push privileges on GitHub? **No**
1.  **[rl-assigned_teams-5]** Does teams have enough members on GitHub? **No**
1.  **[rl-license_file-1]** Does it have a license file? **No**
1.  **[rl-readme_file-1]** Does it have a README file? **No**
1.  **[rl-reuse_tool-1]** Does README mention REUSE? **No**
1.  **[rl-reuse_tool-2]** Does it have LICENSES directory with licenses? **No**
1.  **[rl-reuse_tool-3]** Is it registered in REUSE? **No**
1.  **[rl-reuse_tool-4]** Is it compliant with REUSE rules? **No**


## Warnings

1.  **[rl-contributor_file-1]** Does it have a contributing guideline? **No**
1.  **[rl-contributor_file-2]** Does the contributing guideline have required text? **No**


## Unclear rules

1.  **[rl-license_file-2]** Does it use an allowed license? **unknown**
1.  **[rl-license_file-3]** Does the license have disallowed content? **unknown**


## Passed rules

1.  **[rl-contributor_file-1]** Does it have a contributing guideline? **No**
1.  **[rl-contributor_file-2]** Does the contributing guideline have required text? **No**
1.  **[rl-readme_file-2]** If a project's README doesn't contain required info **No**
1.  **[rl-security_policy-1]** Does it have a security policy? **Yes**
1.  **[rl-vulnerability_alerts-1]** Are vulnerability alerts enabled? **Yes**
1.  **[rl-vulnerability_alerts-2]** Does it have unresolved vulnerability alerts? **No**


## How to fix it

1.  No readme file could be found in your repository. Be sure to add a readme that can be detected by GitHub.
    More info:
    1.  [About READMEs](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/about-readmes)
2.  No team with push privileges for the given repository could be found. Please contact the OSPO so that such a team can be created for your repository or ask your admins to perform the task for you.3.  A members team could be found, but it only contains one person or is even empty. If there is a maintainer in the team, please ask this person to add additional members or contact the OSPO to add the colleagues who are entitled to be members.4.  You should add information about the Developer Certificate of Origin (DCO) to the contributions file. Moreover, be sure that there are no longer any references to the outdated contributor license agreement (CLA) in the file.5.  No license file could be found in your repository. Be sure to add a license that can be detected by GitHub.
    More info:
    1.  [Licensing a repository](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/licensing-a-repository#determining-the-location-of-your-license)
6.  REUSE tool compliance requires that all used licenses are provided in a LICENSES folder on root level.7.  No team with administrative privileges for the given repository could be found. Please contact the OSPO so that such a team can be created for your repository.8.  An admins team could be found, but it only contains one person or is even empty. If there is a maintainer in the team, please ask this person to add another administrator or contact the OSPO to add the colleagues who are entitled to be admins.9.  Only one or no team could be found which is assigned. Please make sure to have at least one member and one admin team assigned to the repository. If you need assistance, please contact the OSPO.10.  No contributing guideline file could be found in your repository. Though it's only a recommendation, we'd like to encourage you to add some information that can be detected by GitHub.
    More info:
    1.  [Setting guidelines for repository contributors](https://docs.github.com/en/communities/setting-up-your-project-for-healthy-contributions/setting-guidelines-for-repository-contributors#adding-a-contributing-file)
11.  The README of your projects needs to include a link to the results of the REUSE tool scan as well as a licensing section.12.  A registration for the repository could not be found. Please verify that the repository is registered properly.
    More info:
    1.  [Register a project in REUSE](https://api.reuse.software/register)
13.  The latest REUSE tool compliance check resulted in errors. Please perform the check again on your repository and fix the issues.