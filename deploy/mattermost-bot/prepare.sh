#!/usr/bin/env bash

set -eo pipefail

###### Set script options ######
OPTSPEC=":hp:c:n:i:d:a:-:"

function show_help {
cat << EOF
Usage: $0 [-p PROJECT_NAME] [-c CLUSTER] [-n NAMESPACE] [-i INSTANCE_SUFFIX] [-d DEVPLATFORM_ENV] [-a EXTRA_ARGS] [--force] [--debug]

* Functionality.
  * Generate helm values-CLUSTER-INSTANCE_SUFFIX.yaml using values.yaml.
  * Run helm template.
* Dependencies.
  * Required: yq, curl.
  * Optional: helm, jq, access to Ingress PROD2.

    -p      Required. The name for the PROJECT being used.
    -c      Required. The name for the CLUSTER being used.
    -n      Required. The name for the NAMESPACE being used.
    -i      Required. The name for the INSTANCE_SUFFIX being used (dev, test, preprod and etc.).
    -d      Required. The name for the DEVPLATFORM_ENV being used (environ: dev or prod).
    -a      Optional. Extra argumenents for yq (on values-CLUSTER-INSTANCE_SUFFIX.yaml file creation stage).
    --force Optional. Overwrite values-CLUSTER-INSTANCE_SUFFIX.yaml.
    --debug Optional. Generate templates from values-CLUSTER-INSTANCE_SUFFIX.yaml to output-.* dir for checking.

    -h      Display this help and exit.
EOF
}

###### Check script invocation options ######
while getopts "$OPTSPEC" optchar; do
  case "${optchar}" in
    h)
      show_help
      exit
      ;;
    -)
      case "${OPTARG}" in
        force)
          FORCE="true";
          ;;
        debug)
          DEBUG="true";
          ;;
      esac;;
    p)
      PROJECT_NAME="$OPTARG";;
    c)
      CLUSTER="$OPTARG";;
    n)
      NAMESPACE="$OPTARG";;
    i)
      INSTANCE_SUFFIX="$OPTARG";;
    d)
      DEVPLATFORM_ENV="$OPTARG"
      if [ "$DEVPLATFORM_ENV" != "prod" ] && [ "$DEVPLATFORM_ENV" != "dev" ]; then
        echo -e "\033[91mInvalid value for DEVPLATROFM_ENV is provided!\033[0m\nExpected one of: dev, prod"
        exit 1
      fi
      ;;
    a)
      EXTRA_ARGS="$OPTARG";;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

if [ -z "$PROJECT_NAME" ] || [ -z "$CLUSTER" ] || [ -z "$NAMESPACE" ] || [ -z "$INSTANCE_SUFFIX" ] || [ -z "$DEVPLATFORM_ENV" ]; then
  show_help
  exit 1
fi

###### Check and create values file ######
function create_values {
  if [ -f values.yaml ]; then
      if [ -f "values-$CLUSTER-$INSTANCE_SUFFIX.yaml" ]; then
        if [ -z "$FORCE" ]; then
          echo -e "\n\033[91mFile \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\" exists\033[0m"
        else
          cp values.yaml "values-$CLUSTER-$INSTANCE_SUFFIX.yaml"
          echo -e "\n\033[91mFile \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\" will be overwritten\033[0m"
        fi
      else
        cp values.yaml "values-$CLUSTER-$INSTANCE_SUFFIX.yaml"
        echo -e "\n\033[92mFile \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\" will be created\033[0m"
      fi
  else
    echo -e "\n\033[91m\n\"values.yaml\" file is not found, create it from \"values-example.yaml\" file\033[0m"
    help
    exit 1
  fi
}

###### Check actual version for values file ######
function check_version {
  if [ -n "$DEBUG" ]; then
    actual_version=$(curl -s https://chartmuseum.paym-infra-services.internal.ix-m2-prod2.prod.k8s.tcsbank.ru/api/charts | jq '.[] | .[].version' | head -1 | sed 's/"//g')
    current_version=$(grep version values-$CLUSTER-$INSTANCE_SUFFIX.yaml | sed 's/# [a-z]*: ~//')

    if [[ $(echo $actual_version | awk -F"." '{print $2}' ) -gt $(echo $current_version | awk -F"." '{print $2}' ) ]]; then
      echo -e "\n\033[91mFile \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\" is outdated, update it.\
              \033[0m\nActual version values file is $actual_version"
      echo -e "\n\033[91mFile \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\" IS NOT updated!\033[0m"
      help
      exit 1
    fi
  fi
}

###### Verification values file ######
function patch_values {
  yq ".cluster=\"$CLUSTER\" | .namespace=\"$NAMESPACE\" | .instanceSuffix=\"$INSTANCE_SUFFIX\" | .devplatformEnv=\"$DEVPLATFORM_ENV\" $EXTRA_ARGS" \
    -i "values-$CLUSTER-$INSTANCE_SUFFIX.yaml"
}

function generate_templates {
  if [ -n "$DEBUG" ]; then
    echo -e "\n\033[93mTesting helm chart for \"values-$CLUSTER-$INSTANCE_SUFFIX.yaml\"...\033[0m"
    
    helm dependency update
    helm template -f "values-$CLUSTER-$INSTANCE_SUFFIX.yaml" "$PROJECT_NAME-$INSTANCE_SUFFIX" . \
      -n "$NAMESPACE" --output-dir "output-$CLUSTER-$INSTANCE_SUFFIX" \
      --debug
    
    echo -e "\n\033[91mCheck templates in \"output-$CLUSTER-$INSTANCE_SUFFIX\" folder\033[0m"
  else
    echo -e "\n\033[93mDebug mode is off, no helm template call\033[0m"
  fi
}

###### Check installed dependencies ######
function check_deps {
  if ! yq --version > /dev/null; then
    echo "yq must be in PATH"
    exit 1
  fi

  if [ -n "$DEBUG" ]; then
    if ! helm version > /dev/null; then
      echo "helm must be in PATH"
      exit 1
    fi

    if ! jq --version > /dev/null; then
      echo "jq должен быть в PATH"
      exit 1
    fi

    if ! curl -m 5 -s https://chartmuseum.paym-infra-services.internal.ix-m2-prod2.prod.k8s.tcsbank.ru > /dev/null; then
      echo "Need get access to Ingress PROD2 https://k8s.pages.devplatform.tcsbank.ru/runtime-userdocs/services/runtime/prod2/concepts/access/"
      exit 1
    fi
  fi
}

###### Show help after error or complete execute script ######
function help {
  echo -e "\nMore information is here: \033[96m\nhttps://paym-infra.pages.devplatform.tcsbank.ru/pi-docs/user-guides/kubernetes/deploy"
}

################################################################

###### Run script logic ######
#check_deps
#create_values
#patch_values
#check_version
generate_templates

################################################################
