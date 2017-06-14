#!/bin/bash
if [ "$DEPLOY_SERVER" != "TRUE" ]; then
	echo "$DEPLOY_SERVER is not TRUE, no push into amazon aws"
	echo "This happens, when the server is pinned to a specific version, for demo purposes."
	echo "No auto deploy of "$TRAVIS_COMMIT_MESSAGE
	exit 0;
fi
echo "Uploading new version to amazon aws"
./gradlew server:uploadArchives
cd app
cd build
eval "$(ssh-agent -s)"
chmod 400 /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh
ssh-add /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh
scp -rp -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh /home/travis/build/BankingBoys/amos-ss17-proj7/server/compiled/server-1.0-SNAPSHOT.war ubuntu@13.58.27.176:/home/ubuntu/
ssh -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh ubuntu@13.58.27.176 "/home/ubuntu/pay/payara41/bin/asadmin start-domain"
ssh -i /home/travis/build/BankingBoys/amos-ss17-proj7/keyssh ubuntu@13.58.27.176 "/home/ubuntu/pay/payara41/bin/asadmin --user admin --passwordfile ~/payPW deploy --force --name server-1.0-SNAPSHOT server-1.0-SNAPSHOT.war"

