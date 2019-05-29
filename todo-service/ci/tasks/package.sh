#!/bin/bash

set -e -u -x

artifact=source-code/todo-service

export ROOT_FOLDER=$( pwd )
M2_HOME=${HOME}/.m2
mkdir -p ${M2_HOME}
 
M2_LOCAL_REPO="${ROOT_FOLDER}/.m2"
 
mkdir -p "${M2_LOCAL_REPO}/repository"
 
cat > ${M2_HOME}/settings.xml <<EOF
 
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository>${M2_LOCAL_REPO}/repository</localRepository>
</settings>
 
EOF

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
