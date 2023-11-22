# CSCB07Project
This is the GitHub repository for the CSCB07 project.

## Notes on Version Control (Updated)
Git Feature Branch Workflow.

The core idea behind the Feature Branch Workflow is that all feature development should take place in a dedicated branch instead of the main branch.

Follow these steps when working (based on https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow):
1. Use these commands:
  ```
  git checkout main // Switch current branch to main
  git fetch origin // Fetch all changes from the remote repo but no merge
  git reset --hard origin/main // Reset the local main branch to match the main branch from the remote repo
  ```
2. Create a new branch based on the latest version of the main branch dedicated to **one feature**. Give the branch a descriptive name. Use this command:
  ```
  git checkout -b new-feature // Create a new branch named "new-feature"; -b flag tells Git to create branch if it does not exist
  ```
3. On this branch, edit and commit changes. When ready, push commits to remote. Use this command:
  ```
  git push -u origin new-feature // Push branch "new-feature" to the remote repo; -u flag makes the branch a remote tracking branch
  ```
  After setting up the tracking branch, ```git push``` can be invoked without any parameters to automatically push the new-feature branch to the remote repo

4. Once work is finished, rebase your branch based on main ('''git rebase main''' while in your branch; make sure your local main is sync'd with the remote main), then create a pull request for your branch. Now teammates comment and approve the pushed commits. Your updates appear in the pull request

## Tools Used
- Git
- Android Studio
- Firebase
- Mockito
- Jira
