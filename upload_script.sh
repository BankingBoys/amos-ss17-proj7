  #!/bin/bash

   cd /home/travis/build/BankingBoys/amos-ss17-proj7/android
   gradle assemble
   cd app
   cd build
   cd outputs
   cd apk
   curl \
      -F "status=2" \
      -F "notify=1" \
      -F "notes=auto deploy." \
      -F "notes_type=0" \
      -F "ipa=@app-debug.apk" \
      -H "X-HockeyAppToken: $hockeyapptoken" \
      https://rink.hockeyapp.net/api/2/apps/4adac746746644d8ad6c94939495c7ff//app_versions/upload


