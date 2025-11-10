#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -u

service="monolith"

environment="kairo-sample"

expected="Deploy ${service} to ${environment}"
echo "${expected} <-- Type this exactly to confirm."
read -r answer

if [[ "${answer}" != "${expected}" ]]; then
  echo "Aborted."
  exit 1
fi

./gradlew assemble

docker build --platform=linux/amd64 \
  -t "us-central1-docker.pkg.dev/kairo-sample/kairo-sample/${service}:$(git rev-parse --short=8 HEAD)" \
  "."

docker push "us-central1-docker.pkg.dev/kairo-sample/kairo-sample/${service}:$(git rev-parse --short=8 HEAD)"

gcloud run services update \
  --project "${environment}" \
  --region "us-central1" \
  "${service}" \
  --image "us-central1-docker.pkg.dev/kairo-sample/kairo-sample/${service}:$(git rev-parse --short=8 HEAD)"
