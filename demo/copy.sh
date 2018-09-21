

cp ../native-src/android/app/release/AlarmPlugin.jar ../platforms/android/libs/

rm -rf node_modules/nativescript-alarm
mkdir node_modules/nativescript-alarm
cp ../*.js node_modules/nativescript-alarm/
cp ../*.json node_modules/nativescript-alarm/
cp -Rf ../platforms node_modules/nativescript-alarm/