#!/bin/sh

rm -f tmp.2


BASE_URL=http://ml3.tibell.io:33434/api
MODEL="llama3.1:8b"

cat << EOF > tmp.4
{
  "model": "${MODEL}",
  "prompt": "Här är ett JSON-objekt med tomma \"namn\" och \"kategorier\"-arrayer. Kan du fylla i dessa arrayer med namnen på personer som nämns i texten och lämpliga nyhetskategori som matchar texten? Kan du också fylla i strängen \"description\" med en sammanfattning som beskriver innehållet i texten med en mening?  Svara endast med det ifylda JSON-objectet. { \"names\": [], \"categorys\": [], \"description\": \"\" }. Svara genom att använda JSON.  Kritik mot att Kinberg Batras agerande inte prövats rättsligt: \"Skulle kunna leda till avsked\" - I fredags meddelade regeringen Anna Kinberg Batras avgång som landshövding efter JO:s kritik i torsdags. Men nu riktas kritik mot att Kinberg Batras agerande inte prövats rättsligt. – Jag tycker det är slappt, säger statsvetaren Göran Sundström.",
  "format": "json",
  "stream": false
}
EOF

curl "${BASE_URL}/generate" -d @tmp.4
