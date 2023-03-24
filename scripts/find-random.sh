#!/bin/sh


echo "========== RUNNING CONCURRENCY = 5 =========="
siege -d1 -t60s -c5 http://127.0.0.1:8081/users/find-random
