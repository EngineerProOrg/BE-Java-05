Loadtest command:

```loadtest -n 1 -c 1  http://localhost:8080/transactions -m POST -P '{"userId": 1,"amount": 2,"idempotentKey": "abcd-1234-jhasgd"}' -T application/json```

```loadtest -c 10 --rps 200 http://localhost:8080/transactions/1```