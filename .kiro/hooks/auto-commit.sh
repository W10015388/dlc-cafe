#!/bin/bash
cd "$(git rev-parse --show-toplevel 2>/dev/null)" || exit 0

# Skip if no git repo
git rev-parse --git-dir >/dev/null 2>&1 || exit 0

# Skip if nothing to commit
git diff --quiet && git diff --cached --quiet && exit 0

# Stage all changes and commit with timestamp
git add -A
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
CHANGED=$(git diff --cached --name-only | head -5 | tr '\n' ', ' | sed 's/,$//')
git commit -m "auto: ${TIMESTAMP} - ${CHANGED}" >/dev/null 2>&1

# Push only if remote exists
if git remote | grep -q .; then
  git push >/dev/null 2>&1
fi

exit 0
