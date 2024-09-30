#!/bin/sh

curl http://ml3.tibell.io:33434/v1/models | jq . | grep "id"
