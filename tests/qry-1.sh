#!/bin/sh

BASE_URL=http://ml3.tibell.io:33434/api

curl "${BASE_URL}/generate" -d '{
  "model": "llama3.1:8b",
  "prompt":"Why is the sky blue?"
}'

