#!/bin/bash

GW="$(which generate_workspace)"

if [[ ! -f ${GW} ]]; then
    echo "you need to build the generate_workspace script form bazel workspace"
    echo "and make it available via the 'generate_workspace' command."
    echo
    echo "# cd \${BAZEL_WORKSPACE}"
    echo "# bazel build //src/tools/generate_workspace:generate_workspace_deploy.jar"
    exit 1
fi
set -e

TMP_file=$(mktemp -t 'backend.XXXXXXXXXX')

POMS=( $(find | grep '/pom.xml' | sed -e 's:/pom.xml$::' -e 's:^:-m :') )

#echo "${POMS[*]}"
#exit 0
echo "-- log file ${TMP_file}"
echo "## generate_workspace ${POMS[*]}"
generate_workspace "${POMS[@]}" 1> "${TMP_file}" 2>&1 || exit 1
echo "-- done"

WS="$(tail -n 2 "${TMP_file}" | head -n 1)"
BUILD="$(tail -n 1 "${TMP_file}")"

echo "-- writing WORKSPACE"
cat "${WS}" > WORKSPACE
echo "-- writing third-party/BUILD"
mkdir -p third-party/
#cp third-party/common.BUILD third-party/BUILD
cat "${BUILD}" > third-party/BUILD
#sed 's:name = \"[^_]*_\(.*\)\":name = \"\1\":' \
#    "${BUILD}" >> third-party/BUILD
