#!/bin/bash

set -e -u -x

artifact=source-code/SpringBootVueApplication

pushd ${artifact}
  echo "Packaging JAR"
  ./mvnw clean package
popd

jar_count=`find ${artifact}/target -type f -name *.jar | wc -l`

if [ $jar_count -gt 1 ]; then
  echo "More than one jar found, don't know which one to deploy. Exiting"
  exit 1
fi

find ${artifact}/target -type f -name *.jar -exec cp "{}" package-output/app.jar \;

echo "Done packaging"
exit 0
