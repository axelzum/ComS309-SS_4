# Information on Team Workflow Processes

## Story Point Guidelines



1 point:
* Should take less than a day to implement.
* Very minimal testing changes, similar examples for code already exist

2 points:
* 1-3 Days to implement.
* A little exploration required, some parts of the story have existing code/examples.
* Not very much testing required.
* Simple frontend or backend changes.

3 points:
* About 1 week of work.
* Simple fullstack changes, or lengthy frontend/backend change.
* Small test changes, maybe a new test file.

4 points:
* 1.5 weeks
* Complex fullstack change requiring a little bit of exploration. Not many existing examples.
* Several new files in code/new tests

5 points:
* Will take the entire sprint.
* Don’t know how to implement will take a lot of exploration to complete.
* Should try to split into smaller stories


## Git Branching Guide

When working on a new story always create a new branch on git. Create a new branch off master:
`git checkout -b [story number]/dev`
(eg. `git checkout -b ss4-123/axel`)

Commit and push all changes for the story on its specific branch using the comment format:
`git commit -m “[SS4-123] commit message here”`

When all changes are implemented create a merge request on GitLab, for team members to review. Once the review is complete the branch will be merged to master.

To delete a local branch on your computer use the command:
`git branch -d [branch name]`

You can list all local and remote branches using the command:
`git branch -a`

When a new branch is merged to master, update your branches in progress by executing
`git pull origin master`
This will merge master with your current branch. You may need to resolve conflicts to complete the merge. Make sure this is done before a branch is merged to master after completion.

If multiple people are working on the same story they will need to branch off each other and it gets complicated, communicate with each other or Axel to figure out how to do that.
