  #!/bin/bash
if [ "$TRAVIS_BRANCH" != "master" ]; then
	echo "$TRAVIS_BRANCH is not master branch, no push into hockey app"
	echo "No auto deploy of "$TRAVIS_COMMIT_MESSAGE
	exit 0;
fi
echo "auto deploy of "$TRAVIS_COMMIT_MESSAGE
cd /home/travis/build/BankingBoys/amos-ss17-proj7/
./gradlew app:assemble
cd app/build/outputs/apk
curl \
      -F "status=2" \
      -F "notify=1" \
      -F "notes=auto deploy of "$TRAVIS_COMMIT_MESSAGE \
      -F "notes_type=0" \
      -F "ipa=@app-debug.apk" \
      -H "X-HockeyAppToken: $hockeyapptoken" \
      https://rink.hockeyapp.net/api/2/apps/f9b11667dbd643fe995b1261326a91ce//app_versions/upload



