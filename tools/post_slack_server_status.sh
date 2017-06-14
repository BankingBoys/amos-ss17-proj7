#!/bin/bash

# Usage: slackpost <token> <channel> <message>

# Enter the name of your slack host here - the thing that appears in your URL:
# https://slackhost.slack.com/
server="13.58.27.176"
text=""

# HockeyAppDeploy
if [ "$TRAVIS_BRANCH" != "master" ]; then
	text=$text"HockeyAppDeploy{FALSE}"
else 
	text=$text"HockeyAppDeploy{TRUE}"
fi

# Amazon AWS Deploy
if [ "$DEPLOY_SERVER" != "TRUE" ]; then
	text=$text", ServerDeploy:{FALSE}"
else
	text=$text", ServerDeploy:{TRUE}"
fi


# Amazon Server Status
if nc -w 5 -z "$server" 8080 ; then
    echo "Port 22 on $server is open"
    text=$text", ServerStatus:{UP}"
else
    text="!!!ATTENTION!!!::: "$text", ServerStatus:{DOWN}"
fi
text=$text+", Commit{"$TRAVIS_COMMIT_MESSAGE"}"

token="WF2UyrAWwmuoSgjjtqPo8O1N"
channel="#server"
escapedText=$(echo $text | sed 's/"/\"/g' | sed "s/'/\'/g" )
json="{\"text\": \"$escapedText\"}"

curl -s -d "payload=$json" $slackwebhook
