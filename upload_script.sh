  #!/bin/bash

   cd /home/travis/build/BankingBoys/amos-ss17-proj7/android
   gradle assembleRelease
   cd app
   cd build
   cd outputs
   cd apk
   curl \
      -F "status=2" \
      -F "notify=1" \
      -F "notes=auto deploy." \
      -F "notes_type=0" \
      -F "ipa=@app-release-unsigned.apk" \
      -H "X-HockeyAppToken: 788d6d4924ae4144a20601938fb9354e" \
      https://rink.hockeyapp.net/api/2/apps/fbff9b3e55e142f89d51718ea3b4e6e4//app_versions/upload


