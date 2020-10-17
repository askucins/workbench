Proxy

```
simpleproxy -vu -L 8080 -R localhost:5050 -t proxy.log
```

it works:
```
http -F --all --proxy=http:http://localhost:8080 -phHbB 'http://localhost:5050/' 'Accept-Encoding: identity'
```

it does not:
```
http -F --all --proxy=http:http://localhost:8080 -phHbB -f POST 'http://localhost:5050/' 'Accept-Encoding: identity' 'checked=true' 'name=Joe'
```
