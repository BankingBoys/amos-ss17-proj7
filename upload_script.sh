  #!/bin/bash
if [ "$TRAVIS_BRANCH" != "master" ]; then
	echo "$TRAVIS_BRANCH is not master branch, no push into hockey app"
	exit 0;
fi

cd /home/travis/build/BankingBoys/amos-ss17-proj7/
./gradlew android:app:assemble
cd android/app/build/outputs/apk
curl \
      -F "status=2" \
      -F "notify=1" \
      -F "notes=auto deploy of "$TRAVIS_BRANCH \
      -F "notes_type=0" \
      -F "ipa=@app-debug.apk" \
      -H "X-HockeyAppToken: $hockeyapptoken" \
      https://rink.hockeyapp.net/api/2/apps/upload



