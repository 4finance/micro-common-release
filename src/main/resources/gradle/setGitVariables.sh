#!/bin/sh

echo Configuring Git for pushing to GH...

git config user.email "4financebot@gmail.com"
git config user.name "4Finance Bot"
git config push.default simple
git remote set-url origin "https://${GH_TOKEN}@github.com/4finance/micro-common-release.git"

echo Git configured
