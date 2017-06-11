#!/bin/bash

# Usage: slackpost <token> <channel> <message>

# Enter the name of your slack host here - the thing that appears in your URL:
# https://slackhost.slack.com/
server="13.58.27.176"
text=""
if nc -w 5 -z "$server" 8080 ; then
    echo "Port 22 on $server is open"
    text="serverUp"
else
    text="Serverdeploy doenst work!!!! Commit{"$TRAVIS_COMMIT_MESSAGE"}"
fi

token="WF2UyrAWwmuoSgjjtqPo8O1N"
channel="#server"
escapedText=$(echo $text | sed 's/"/\"/g' | sed "s/'/\'/g" )
json="{\"text\": \"$escapedText\"}"

curl -s -d "payload=$json" $slackwebhook
