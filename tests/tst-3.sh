#!/bin/sh

PROMPT="What color is the sky at different times of the day? Respond using JSON"

BASE_URL=http://ml3.tibell.io:33434/api
MODEL="llama3.1:8b"
#MODEL="glm4:latest"

cat << EOF > tmp.3
{
  "model": "${MODEL}",
  "prompt":"${PROMPT}",
  "format": "json",
  "stream": false
}
EOF

curl "${BASE_URL}/generate" -d @tmp.3
