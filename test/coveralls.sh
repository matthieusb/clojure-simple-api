COVERALLS_URL='https://coveralls.io/api/v1/jobs'
CLOVERAGE_VERSION='1.0.4-SNAPSHOT' lein cloverage -o test/cov --coveralls
curl -F 'json_file=@test/cov/coveralls.json' "$COVERALLS_URL"
