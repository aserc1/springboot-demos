# 工程简介

提供两个接口：

1. 根据日期计算是一周的第几天；
2. 根据四则运算表达式计算结果。

# 接口示例

## 根据日期计算是一周的第几天

```http
POST http://localhost:8080/rest/v1/date/day_of_week
Content-Type: application/json

{
  "date": "2021-10-5"
}

### 成功：
{
  "retCode": 1000,
  "message": null,
  "data": {
    "dayOfWeek": "二"
  }
}

### 失败：
{
  "retCode": 1002,
  "message": "2021-10-55 is not a valid date",
  "data": null
}
```

## 根据四则运算表达式计算结果

```http
POST http://localhost:8080/rest/v1/calculate
Content-Type: application/json

{
  "expression": "2+3*-5"
}

### 成功：
{
  "retCode": 1000,
  "message": null,
  "data": {
    "result": 0.0
  }
}

### 失败：
{
  "retCode": 1002,
  "message": "Invalid expression: 2+3--5",
  "data": null
}
```



1. 



