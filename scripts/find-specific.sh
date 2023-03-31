#!/bin/sh


siege -d1 -t130s -c100 http://127.0.0.1:8081/users/find-specific
