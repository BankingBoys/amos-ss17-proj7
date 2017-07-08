#!/bin/bash
if [ "$DEPLOY_SERVER" != "TRUE" ]; then
	echo "$DEPLOY_SERVER is not TRUE, no push into amazon aws"
	echo "This happens, when the server is pinned to a specific version, for demo purposes."
	echo "No auto deploy of "$TRAVIS_COMMIT_MESSAGE
	exit 0;
fi

if [[ $TRAVIS_COMMIT_MESSAGE != *"#serverdeploy"* ]]; then
	echo "Commit message does not contain #serverdeploy"
	echo "Commit message was:"$TRAVIS_COMMIT_MESSAGE
	echo "No server deploy happens"
	exit 0;
fi


echo "Uploading new version to amazon aws"
./gradlew server:assemble
cd app
cd build
eval "$(ssh-agent -s)"
chmod 400 /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh
ssh-add /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh
ssh -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh ubuntu@$AWS_SERVER "killall java"
scp -rp -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh /home/travis/build/BankingBoys/amos-ss17-proj7/server/build/libs/server-1.0-SNAPSHOT.jar ubuntu@$AWS_SERVER:/home/ubuntu/
ssh -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh ubuntu@$AWS_SERVER "java -jar /home/ubuntu/server-1.0-SNAPSHOT.jar > /home/ubuntu/log &"
