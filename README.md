# ip_test

一个检验请求方外网ip地址的服务。

只有一个接口url：
@GetMapping(value = {"", "/", "index", "index.html"})
@PostMapping(value = {"", "/", "index", "index.html"})


get访问接口会返回一个html页面提示ip地址。eg:ip.sunyc.cn
post访问只会返回纯文本格式的ip地址。
