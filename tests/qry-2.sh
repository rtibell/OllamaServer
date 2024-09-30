#!/bin/sh

BASE_URL=http://ml3.tibell.io:33434/api

curl "${BASE_URL}/chat" -d '{
  "model": "llama3.1:8b",
  "messages": [
    { "role": "user", "content": "why is the sky blue?" }
  ]
}'
