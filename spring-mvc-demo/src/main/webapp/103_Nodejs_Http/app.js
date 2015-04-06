// sample of http server

var http = require("http");

function process_request(req,resp){
    var body = "<H1> thank you for calling me !</H1>";
    var content_length = body.length;
    resp.writeHeader(200,{
       'Content-Length':content_length,
        'Content-Type':'text/plain'
    });

    resp.end(body);
};

var s = http.createServer(process_request);
s.listen(10000);
