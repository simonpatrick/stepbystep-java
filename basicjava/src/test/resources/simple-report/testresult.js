new Vue({
    "data": {
        "testsuites": [{
            "cases": 1,
            "failed": 0,
            "isPassedSuite": true,
            "passed": 1,
            "passedSuite": true,
            "suiteName": "test1",
            "testCases": [{
                "completed": false,
                "endMillis": 0,
                "logs": ["this is testlogs"],
                "pass": true,
                "screenShots": ["test_1.jpg"],
                "skipped": false,
                "startedMillis": 0,
                "status": 1,
                "stepScreenshotPath": [],
                "testClassName": "test1",
                "testDescription": "test test",
                "testMethodName": "testing"
            }],
            "testSuiteFailed": false
        }, {
            "cases": 1,
            "failed": 0,
            "isPassedSuite": true,
            "passed": 1,
            "passedSuite": true,
            "suiteName": "test2",
            "testCases": [{"$ref": "$.data.testsuites[0].testCases[0]"}],
            "testSuiteFailed": false
        }, {
            "cases": 1,
            "failed": 0,
            "isPassedSuite": true,
            "passed": 1,
            "passedSuite": true,
            "suiteName": "test3",
            "testCases": [{"$ref": "$.data.testsuites[0].testCases[0]"}],
            "testSuiteFailed": false
        }]
    }, "el": "#testresult"
});
