<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge"/>
    <meta name="viewport" content="with=device-with,initial-scale = 1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>测试结果报告</title>
    <link rel="stylesheet" href="asserts/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="asserts/css/dashboard.css"/>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Test Result</a>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <!--side-bar-->
        <div class="col-sm-3 col-md-2 sidebar">
            <ul id="nav nav-sidebar">
                <li class="active">
                    <a href="#">Overview<span class="sr-only">(current)</span></a>
                </li>
                <li><a href="#">${SuiteName}</a></li>
                <li><a href="#">测试套件2</a></li>
            </ul>
        </div>
        <!--概要-->
        <div class="col-sm-9 col-sm-offset-3 col-mf-20 col-md-offset-2 main">
            <h1 class="page-header">测试结果</h1>

            <div class="row placeholder">
                <div class="col-xs-3 col-sm-3 placeholder">
                    <h4>${SuiteName}测试通过case总数：45</h4>
                </div>
                <div class="col-xs-3 col-sm-3 placeholder">
                    <h4>${SuiteName}测试失败case总数：34</h4>
                </div>
                <div class="col-xs-3 col-sm-3 placeholder">
                    <h4>测试忽略case总数：33</h4>
                </div>
            </div>
            <h2 class="sub-header">测试详细结果</h2>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thread>
                        <tr>
                            <th>#</th>
                            <th>TestSuite</th>
                            <th>TestMethod</th>
                            <th>TestResult</th>
                            <th>Screenshot</th>
                        </tr>
                    </thread>
                    <tbody>
                    <tr>
                        <td>TestSuite</td>
                        <td>TestClass</td>
                        <td>TestMethod</td>
                        <td>
                            <span class="label label-success">PASS</span>
                        </td>
                        <td>Screenshot_link</td>
                    </tr>
                    <tr>
                        <td>TestSuite</td>
                        <td>TestClass</td>
                        <td>TestMethod</td>
                        <td>TestResult</td>
                        <td>Screenshot_link</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<!-- Placed at the end of the document so the pages load faster -->
<script src="asserts/js/jquery.min.map"></script>
<script src="asserts/js/bootstrap.min.js"></script>
</body>
</html>